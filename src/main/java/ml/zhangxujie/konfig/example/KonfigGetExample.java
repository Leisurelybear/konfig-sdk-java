/**
 * FileName: KonfigGetExample
 * Author:   jason
 * Date:     2021/6/4 16:46
 * Description:
 */
package ml.zhangxujie.konfig.example;

import ml.zhangxujie.konfig.KonfigClient;
import ml.zhangxujie.konfig.dto.konfig.Konfig;
import ml.zhangxujie.konfig.dto.konfig.KonfigCollection;

import java.util.Map;

//获取配置的Demo
public class KonfigGetExample {

    public static void main(String[] args) {
        KonfigClient client = KonfigClient.getKonfigClient("test");
        KonfigCollection konfigCollection = client.getConfig(20);

        System.out.println(konfigCollection.toString());


        Map konfigMap = client.convertKonfigListToMap(konfigCollection.getConfigList());
        System.out.println(konfigMap.get("hello"));
        System.out.println(konfigMap.get("hello1"));
    }
}
