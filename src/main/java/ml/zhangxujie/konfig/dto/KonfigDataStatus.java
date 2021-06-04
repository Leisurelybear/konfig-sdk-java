package ml.zhangxujie.konfig.dto;

public enum KonfigDataStatus {

    DRAFT, //下线的更改
    ONLINE; //上线的更改


    // 判断是否是在线状态
    public static boolean isOnline(KonfigDataStatus status) {
        return ONLINE.equals(status);
    }


}
