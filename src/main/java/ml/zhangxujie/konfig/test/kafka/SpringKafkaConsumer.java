package ml.zhangxujie.konfig.test.kafka;

import ml.zhangxujie.konfig.common.ConfigHelper;
import ml.zhangxujie.konfig.common.Const;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SpringKafkaConsumer {
    private static final String GROUPID = "groupA";
    private static final Integer collectionId = 20;



    public static void main(String args[]) {

        //1.参数配置:不是每一非得配置
        Properties props = new Properties();
        props.put("bootstrap.servers", ConfigHelper.getConf("kafka.consumer.bootstrap.servers", ""));
        props.put("auto.commit.interval.ms", ConfigHelper.getConf("kafka.consumer.auto.commit.interval.ms", ""));
        //因为每一个消费者必须属于某一个消费者组，所以必须还设置group.id
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", ConfigHelper.getConf("kafka.consumer.enable.auto.commit", ""));
        props.put("session.timeout.ms", ConfigHelper.getConf("kafka.consumer.session.timeout.ms", ""));
        props.put("key.deserializer", ConfigHelper.getConf("kafka.consumer.key.deserializer", ""));
        props.put("value.deserializer", ConfigHelper.getConf("kafka.consumer.value.deserializer", ""));


        //2.创建消费者对象，并建立连接
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //3.设置从"my-topic"主题下拿取数据
        consumer.subscribe(Arrays.asList(Const.MQ_TOPIC_CONFIG + collectionId));

        //4.消费数据
        while (true) {
            //阻塞时间，从kafka中取出100毫秒的数据，有可能一次性去除0-n条
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            //遍历
            for (ConsumerRecord<String, String> record : records)
                //打印结果
                //System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println("消费者消费的数据为：" + record.value());
        }
    }
}

