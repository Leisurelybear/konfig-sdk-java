/**
 * FileName: Const
 * Author:   jason
 * Date:     2021/5/27 11:21
 * Description:
 */
package ml.zhangxujie.konfig.common;

public class Const {

    public static final String API_PATH_CONFIG = "/sdk/config";

    //Kafka主题前缀
    public final static String MQ_TOPIC_CONFIG = "CONFIG_COLLECTION_";


    //kafka拉取来的数据的状态，0=上线，1=下线
    public final static Integer MQ_DATA_STATUS_DRAFT = 1;
    public final static Integer MQ_DATA_STATUS_ONLINE = 0;



}
