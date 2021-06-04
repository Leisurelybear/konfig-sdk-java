/**
 * FileName: ConfigCollection
 * Author:   jason
 * Date:     2021/5/27 11:22
 * Description: 配置集合
 */
package ml.zhangxujie.konfig.dto.konfig;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class KonfigCollection {

    //配置集合ID
    private Integer id;

    //配置集合名
    private String collectionName;

    //配置列表
    private List<Konfig> configList;

}
