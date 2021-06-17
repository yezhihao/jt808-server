package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.车辆控制)
public class T8500 extends JTMessage {

    private int sign;

    public T8500() {
    }

    public T8500(int... sign) {
        this.sign = Bit.writeInt(sign);
    }

    @Field(index = 0, type = DataType.BYTE, desc = "控制标志: " +
            "[0  ] 0.车门解锁 1.车门加锁 " +
            "[1-7] 保留")
    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}