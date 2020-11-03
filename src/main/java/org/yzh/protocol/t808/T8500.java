package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bin;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.车辆控制)
public class T8500 extends JTMessage {

    /**
     * [ 0 ] 0.车门解锁；1.车门加锁
     * [1-7] 保留
     */
    private int sign;

    public T8500() {
    }

    public T8500(String mobileNo) {
        super(new Header(mobileNo, JT808.车辆控制));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "控制标志")
    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public void setSign(int... sign) {
        this.sign = Bin.writeInt(sign);
    }
}