package ml.zhangxujie.konfig.event;

import java.util.EventListener;

public interface KonfigEventListener extends EventListener {

    void OnChanged(KonfigEventObject konfigEventObject);

}
