/**
 * FileName: KonfigEvent
 * Author:   jason
 * Date:     2021/6/1 9:48
 * Description:
 */
package ml.zhangxujie.konfig.event;

import ml.zhangxujie.konfig.dto.KonfigDataStatus;
import ml.zhangxujie.konfig.dto.konfig.Konfig;
import ml.zhangxujie.konfig.dto.konfig.KonfigCollection;

import java.util.EventObject;

public class KonfigEventObject extends EventObject {

    private KonfigEventObjectSource source;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public KonfigEventObject(KonfigEventObjectSource source) {
        super(source);
        this.source = source;
    }

    @Override
    public KonfigEventObjectSource getSource() {
        return this.source;
    }

    public Long getPollTimestamp(){
        return this.source.getTimestamp();
    }

    public KonfigCollection getKonfigCollection(){
        return this.source.getKonfigCollection();
    }

    public Konfig getKonfig(String key){
        return this.source.getConfigMap().get(key);
    }

    public boolean isOnline(){
        return KonfigDataStatus.isOnline(this.source.getKonfigDataStatus());
    }
}
