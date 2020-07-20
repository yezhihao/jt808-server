package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(MessageId.终端升级结果通知)
public class T0108 extends AbstractBody {

    /** 终端 */
    public static final int Terminal = 0;
    /** 道路运输证IC卡 读卡器 */
    public static final int CardReader = 12;
    /** 北斗卫星定位模块 */
    public static final int Beidou = 52;

    private Integer type;
    private Integer result;

    @Property(index = 0, type = DataType.BYTE, desc = "升级类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "升级结果")
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}