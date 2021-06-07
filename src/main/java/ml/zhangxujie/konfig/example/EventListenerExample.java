/**
 * FileName: EventListenerExample
 * Author:   jason
 * Date:     2021/6/4 16:35
 * Description:
 */
package ml.zhangxujie.konfig.example;

import ml.zhangxujie.konfig.KonfigClient;
import ml.zhangxujie.konfig.event.KonfigEventListener;
import ml.zhangxujie.konfig.event.KonfigEventObject;
import ml.zhangxujie.konfig.event.KonfigEventObjectSource;

//使用监听器的Demo
public class EventListenerExample {

    public static void main(String[] args) {
        KonfigClient client = KonfigClient.getKonfigClient("test-e1");

        client.addEventListener(20, new KonfigEventListener() {
            @Override
            public void OnChanged(KonfigEventObject konfigEventObject) {

                System.out.println(konfigEventObject);
            }
        });


//        client.addEventListener(19, new KonfigEventListener() {
//            @Override
//            public void OnChanged(KonfigEventObject konfigEventObject) {
//                KonfigEventObjectSource source = konfigEventObject.getSource();
//                System.out.println("19:test: onChanged! - " + source);
//            }
//        });

//        client.addEventListener(20, new KonfigEventListener() {
//            @Override
//            public void OnChanged(KonfigEventObject konfigEventObject) {
//                KonfigEventObjectSource source = konfigEventObject.getSource();
//                System.out.println("20:test: onChanged! - " + source);
//            }
//        });

//        Thread t1 = new Thread(){
//            @Override
//            public void run() {
////                while (true){
//                    client.addEventListener(20, new KonfigEventListener() {
//                        @Override
//                        public void OnChanged(KonfigEventObject konfigEventObject) {
//                            KonfigEventObjectSource source = konfigEventObject.getSource();
//                            System.out.println("20:test: onChanged! - " + source);
//                        }
//                    });
////                }
//
//            }
//        };
//
//        Thread t2 = new Thread(){
//            @Override
//            public void run() {
////                while (true){
//                    System.out.println("------------------------------");
//                    System.out.println(Thread.currentThread().getName());
//
//                    client.addEventListener(19, new KonfigEventListener() {
//                        @Override
//                        public void OnChanged(KonfigEventObject konfigEventObject) {
//                            KonfigEventObjectSource source = konfigEventObject.getSource();
//                            System.out.println("19:test: onChanged! - " + source);
//                        }
//                    });
//                }
////            }
//        };
//
//        t1.start();
//        t2.start();
    }
}
