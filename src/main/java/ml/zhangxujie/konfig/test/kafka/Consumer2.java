/**
 * FileName: Consumer
 * Author:   jason
 * Date:     2021/5/31 15:24
 * Description:
 */
package ml.zhangxujie.konfig.test.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class Consumer2{

    private static final String GROUPID = "groupB";

    public static void main(String args[]) {

        //1.参数配置:不是每一非得配置
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.255.254.152:9092");
        props.put("auto.commit.interval.ms", "1000");
        //因为每一个消费者必须属于某一个消费者组，所以必须还设置group.id
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", "true");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //2.创建消费者对象，并建立连接
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //3.设置从"my-topic"主题下拿取数据
        consumer.subscribe(Arrays.asList(Const.topic));

        //4.消费数据
        while (true) {
            //阻塞时间，从kafka中取出100毫秒的数据，有可能一次性去除0-n条
            ConsumerRecords<String, String> records = consumer.poll(100);
            //遍历
            for (ConsumerRecord<String, String> record : records)
                //打印结果
                //System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println("消费者消费的数据为：" + record.value());
        }
    }

//    private final KafkaConsumer<String, String> consumer;
//    private ConsumerRecords<String, String> msgList;
//    private final String topic;
//    private static final String GROUPID = "groupB";
//
//    public Consumer2(String topicName) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "10.255.254.152:9092");
//        props.put("group.id", GROUPID);
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
//        props.put("auto.offset.reset", "earliest");
//        props.put("key.deserializer", StringDeserializer.class.getName());
//        props.put("value.deserializer", StringDeserializer.class.getName());
//        this.consumer = new KafkaConsumer<String, String>(props);
//        this.topic = topicName;
//        this.consumer.subscribe(Arrays.asList(topic));
//    }
//
//    @Override
//    public void run() {
//        int messageNo = 1;
//        System.out.println("---------开始消费---------");
//        try {
//            for (;;) {
//                msgList = consumer.poll(1000);
//                if(null!=msgList&&msgList.count()>0){
//                    for (ConsumerRecord<String, String> record : msgList) {
//                        //消费100条就打印 ,但打印的数据不一定是这个规律的
//                        if(messageNo%100==0){
//                            System.out.println(messageNo+"=======receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
//                        }
//                        //当消费了1000条就退出
//                        if(messageNo%1000==0){
//                            break;
//                        }
//                        messageNo++;
//                    }
//                }else{
//                    Thread.sleep(1000);
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            consumer.close();
//        }
//    }
//    public static void main(String args[]) {
//        Consumer2 test1 = new Consumer2(Const.topic);
//        Thread thread1 = new Thread(test1);
//        thread1.start();
//    }
}
