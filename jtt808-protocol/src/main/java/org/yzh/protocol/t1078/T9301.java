package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.云台旋转)
public class T9301 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
    private int channelNo;
    @Field(index = 1, type = DataType.BYTE, desc = "方向：0.停止 1.上 2.下 3.左 4.右")
    private int param1;
    @Field(index = 2, type = DataType.BYTE, desc = "速度(0~255)")
    private int param2;

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }
}
