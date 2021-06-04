/**
 * FileName: Producer
 * Author:   jason
 * Date:     2021/5/31 15:21
 * Description:
 */
package ml.zhangxujie.konfig.test.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MyProducer {


    public static void main(String args[]) {

        //1.参数配置：端口、缓冲内存、最大连接数、key序列化、value序列化等等（不是每一个非要配置）
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.255.254.152:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //2.创建生产者对象，并建立连接
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        try {
            //3.在my-topic主题下，发送消息
            for (int i = 0; i < 10000; i++) {
                System.out.println(Integer.toString(i));
                producer.send(new ProducerRecord<String, String>(Const.topic, Integer.toString(i), Integer.toString(i)));
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        //4.关闭
        producer.close();

    }

//    private final KafkaProducer<String, String> producer;
//    private final String topic;
//    public Producer(String topicName) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "10.255.254.152:9092");
//        props.put("acks", "all");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("key.serializer", StringSerializer.class.getName());
//        props.put("value.serializer", StringSerializer.class.getName());
//        this.producer = new KafkaProducer<String, String>(props);
//        this.topic = topicName;
//    }
//
//    @Override
//    public void run() {
//        int messageNo = 1;
//        try {
//            for(;;) {
//                String messageStr="你好，这是第"+messageNo+"条数据";
//                producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr));
//                //生产了100条就打印
//                if(messageNo%100==0){
//                    System.out.println("发送的信息:" + messageStr);
//                }
//                //生产1000条就退出
//                if(messageNo%1000==0){
//                    System.out.println("成功发送了"+messageNo+"条");
//                    break;
//                }
//                messageNo++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            producer.close();
//        }
//    }
//
//    public static void main(String args[]) {
//        Producer test = new Producer(Const.topic);
//        Thread thread = new Thread(test);
//        thread.start();
//    }

}
