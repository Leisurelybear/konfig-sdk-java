/**
 * FileName: Yell
 * Author:   jason
 * Date:     2021/5/27 16:29
 * Description:
 */
package ml.zhangxujie.konfig.test.longconnection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
public class Yell implements Serializable {
    private static final long serialVersionUID = 0L;
    protected Integer type;
    protected String msg;

    public String waitAndSee() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "空气非常安静,你看着我,我看着你.";
    }
}
