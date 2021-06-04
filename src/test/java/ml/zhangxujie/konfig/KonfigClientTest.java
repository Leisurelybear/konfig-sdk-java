package ml.zhangxujie.konfig;

import ml.zhangxujie.konfig.dto.konfig.KonfigCollection;
import ml.zhangxujie.konfig.event.KonfigEventListener;
import ml.zhangxujie.konfig.event.KonfigEventObject;
import ml.zhangxujie.konfig.event.KonfigEventObjectSource;
import org.junit.Test;

import java.awt.event.HierarchyBoundsAdapter;

public class KonfigClientTest {

    @Test
    public void getConfig() {

        KonfigClient client = KonfigClient.getKonfigClient("test");
        KonfigCollection konfigCollection = client.getConfig(20);

        System.out.println(konfigCollection.toString());
    }

    @Test
    public void addEventListener() {
        KonfigClient client = KonfigClient.getKonfigClient("test-2");

        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("------------------------------");
                    System.out.println(Thread.currentThread().getName());

                    client.addEventListener(20, new KonfigEventListener() {
                        @Override
                        public void OnChanged(KonfigEventObject konfigEventObject) {
                            KonfigEventObjectSource source = konfigEventObject.getSource();
                            System.out.println("20:test: onChanged! - " + source);
                        }
                    });
                }

            }
        };


        Thread t2 = new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("------------------------------");
                    System.out.println(Thread.currentThread().getName());

                    client.addEventListener(19, new KonfigEventListener() {
                        @Override
                        public void OnChanged(KonfigEventObject konfigEventObject) {
                            KonfigEventObjectSource source = konfigEventObject.getSource();
                            System.out.println("19:test: onChanged! - " + source);
                        }
                    });
                }
            }
        };


//        t1.setDaemon(true);
//        t2.setDaemon(true);

        t1.start();
        t2.start();


    }


}