/**
 * FileName: ConfigPushData
 * Author:   jason
 * Date:     2021/6/3 15:47
 * Description:
 */
package ml.zhangxujie.konfig.dto;

import lombok.Data;

//从kafka拉取的数据
@Data
public class MqPollData {

    public MqPollData() {
        //初始化
        this.status = 1;
        this.timestamp = 0L;
        this.id = -1;
    }

    // 推送时间戳
    private Long timestamp;

    //推送的配置集ID
    private Integer id;

    //配置集合的状态，0=正常，1=草稿版本
    private Integer status;

}
