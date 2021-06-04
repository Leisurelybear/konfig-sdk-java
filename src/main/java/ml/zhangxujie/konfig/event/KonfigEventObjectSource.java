/**
 * FileName: KonfigEventObject
 * Author:   jason
 * Date:     2021/6/4 15:01
 * Description:
 */
package ml.zhangxujie.konfig.event;

import lombok.Data;
import lombok.ToString;
import ml.zhangxujie.konfig.dto.KonfigDataStatus;
import ml.zhangxujie.konfig.dto.konfig.KonfigCollection;

@Data
@ToString
public class KonfigEventObjectSource {

    // 推送时间戳
    private Long timestamp;

    //配置集合的状态，0=正常，1=草稿版本
    private KonfigDataStatus konfigDataStatus;

    //配置集合
    private KonfigCollection konfigCollection;


}
