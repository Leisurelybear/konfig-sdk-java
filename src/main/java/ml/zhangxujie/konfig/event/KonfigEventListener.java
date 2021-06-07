package ml.zhangxujie.konfig.event;

import java.util.EventListener;

public interface KonfigEventListener extends EventListener {

    /**
     * @Author: Jason
     * @Description: 用户自定义实现方法，当配置变更时候要做的事情
     * @Param konfigEventObject: 配置变化更新的对象
     **/
    void OnChanged(KonfigEventObject konfigEventObject);

}
