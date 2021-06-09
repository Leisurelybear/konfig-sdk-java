/**
 * FileName: KonfigUtil
 * Author:   jason
 * Date:     2021/6/9 18:53
 * Description:
 */
package ml.zhangxujie.konfig.common;

import ml.zhangxujie.konfig.dto.konfig.Konfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KonfigUtil {

    public static Map<String, Konfig> parseKonfigMap(List<Konfig> configList){

        Map<String, Konfig> map = new HashMap<>();

        if (configList == null || configList.size() == 0){
            return map;
        }

        for (Konfig konfig : configList) {
            map.put(konfig.getKey(), konfig);
        }
        return map;
    }
}
