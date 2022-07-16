package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.车辆控制)
public class T8500 extends JTMessage {

    @Field(length = 1, desc = "控制标志：[0]0.车门解锁 1.车门加锁 [1~7]保留")
    @Field(length = 2, desc = "控制类型：1.车门加锁 2~8.为标准修订预留", version = 1)
    private int type;
    @Field(length = 1, desc = "控制标志：0.车门解锁 1.车门加锁", version = 1)
    private int param;

    public T8500() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }
}