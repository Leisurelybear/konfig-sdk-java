package ml.zhangxujie.konfig.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigHelperTest {

    @Test
    public void getConf() {
        System.out.println(ConfigHelper.getConf("username", ""));
        System.out.println(ConfigHelper.getConf("password", ""));
        System.out.println(ConfigHelper.getConf("host", ""));
        System.out.println(ConfigHelper.getConf("port", ""));
        System.out.println(ConfigHelper.getConf("null", "default"));
        System.out.println(ConfigHelper.getConf("kafka.topic", "default"));

    }
}