/**
 * FileName: ConfigHelper
 * Author:   jason
 * Date:     2021/5/27 14:29
 * Description:
 */
package ml.zhangxujie.konfig.common;

import cn.hutool.log.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public class ConfigHelper {
    private static String filename = "konfig";

    public static String getConf(String key, String defaultValue) {
        String result = defaultValue;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(filename);
            result = bundle.getString(key).trim();
        } catch (MissingResourceException e) {
            log.error("没有当前配置项：" + key);
        } catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return result;
    }

    public static String getConf(String key) {
        String result = "";
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(filename);
            result = bundle.getString(key).trim();
        } catch (MissingResourceException e) {
            log.error("没有当前配置项：" + key);
        } catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return result;
    }


}
