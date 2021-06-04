/**
 * FileName: KonfigClient
 * Author:   jason
 * Date:     2021/5/27 11:04
 * Description:
 */
package ml.zhangxujie.konfig;

import cn.hutool.core.lang.Singleton;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import ml.zhangxujie.konfig.common.ConfigHelper;
import ml.zhangxujie.konfig.common.Const;
import ml.zhangxujie.konfig.dto.KonfigDataStatus;
import ml.zhangxujie.konfig.dto.MqPollData;
import ml.zhangxujie.konfig.event.KonfigEventObject;
import ml.zhangxujie.konfig.event.KonfigEventListener;
import ml.zhangxujie.konfig.dto.konfig.KonfigCollection;
import ml.zhangxujie.konfig.event.KonfigEventObjectSource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//@NoArgsConstructor
@Slf4j
public class KonfigClient {

    private String url;

    private Properties props = new Properties();


    private volatile static KonfigClient konfigClient;

    private KonfigClient(String appId) {
        String host = ConfigHelper.getConf("server.host", "127.0.0.1");
        String port = ConfigHelper.getConf("server.port", "8301");

        //1.参数配置:不是每一非得配置
        props.put("bootstrap.servers", ConfigHelper.getConf("kafka.consumer.bootstrap.servers", ""));
        props.put("auto.commit.interval.ms", ConfigHelper.getConf("kafka.consumer.auto.commit.interval.ms", ""));
        //因为每一个消费者必须属于某一个消费者组，所以必须还设置group.id
        props.put("group.id", appId);
        props.put("enable.auto.commit", ConfigHelper.getConf("kafka.consumer.enable.auto.commit", ""));
        props.put("session.timeout.ms", ConfigHelper.getConf("kafka.consumer.session.timeout.ms", ""));
        props.put("key.deserializer", ConfigHelper.getConf("kafka.consumer.key.deserializer", ""));
        props.put("value.deserializer", ConfigHelper.getConf("kafka.consumer.value.deserializer", ""));

        this.url = host + ":" + port;
    }

    /**
     * @Description: 单例模式，获取Client实例
     * @Date: 2021/6/4 14:37
     * @Param appId: 每个应用唯一的appId，对于该Appid，每一条通知只能接受一次
     * @return: ml.zhangxujie.konfig.KonfigClient
     **/
    public static KonfigClient getKonfigClient(String appId) {
        if (konfigClient == null) {
            synchronized (Singleton.class) {
                if (konfigClient == null) {
                    konfigClient = new KonfigClient(appId);
                }
            }
        }
        return konfigClient;
    }

    /**
     * @Author: Jason
     * @Description: fallback 手动拉取配置
     * @Param collectionId: 拉取配置的 ID
     * @return: 返回配置集
     **/
    public KonfigCollection getConfig(Integer collectionId) {

        String api = url + Const.API_PATH_CONFIG + "/" + collectionId;

        String body = HttpRequest.get(api).execute().body();

        KonfigCollection konfigCollection = null;

        try {
            konfigCollection = JSONObject.parseObject(body, KonfigCollection.class);
        } catch (Exception e) {
            log.error("failed to parse json konfig");
        }

        if (konfigCollection == null) {
            return new KonfigCollection();
        }

        return konfigCollection;
    }


    /**
     * @Author: Jason
     * @Description: 设置监听事件
     * @Param eventListener: 监听事件
     * @Param collectionId: 监听的集合ID
     **/
    public void addEventListener(Integer collectionId, KonfigEventListener eventListener) {
        log.info("Add event listener: collectionId={}", collectionId);
        //开子线程，监听数据变化
        new Thread(() -> {
            //1.创建消费者对象，并建立连接
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

            //2.设置从"my-topic"主题下拿取数据
            consumer.subscribe(Arrays.asList(Const.MQ_TOPIC_CONFIG + collectionId));

            //3.循环监听消息队列数据
            while (true) {
                //阻塞时间，从kafka中取出100毫秒的数据，有可能一次性去除0-n条
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                //遍历
                for (ConsumerRecord<String, String> record : records) {
                    //打印日志，从消息队列中获取的数据
                    log.info("消费者消费的数据为：{}", record.value());


                    MqPollData pollData = new MqPollData();
                    try {
                        //通过fastjson转换成对象
                        pollData = JSONObject.parseObject(record.value(), MqPollData.class);
                    } catch (Exception e) {
                        log.error("failed to parse json from kafka");
                    }

                    //获得更新提示后，拉取最新配置
                    KonfigCollection konfigCollection = getConfig(collectionId);

                    //拼接监听事件的数据源
                    KonfigEventObjectSource source = new KonfigEventObjectSource();
                    if (pollData.getStatus().equals(Const.MQ_DATA_STATUS_ONLINE)) {
                        source.setKonfigDataStatus(KonfigDataStatus.ONLINE);
                    } else {
                        source.setKonfigDataStatus(KonfigDataStatus.DRAFT);
                    }
                    source.setTimestamp(pollData.getTimestamp());
                    source.setKonfigCollection(konfigCollection);

                    //创建事件对象
                    KonfigEventObject konfigEventObject = new KonfigEventObject(source);

                    //对事件对象执行用户实现的方法
                    eventListener.OnChanged(konfigEventObject);
                }

            }

        }).start();
    }
}
