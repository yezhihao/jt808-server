package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT1078.云台调整焦距控制, JT1078.云台调整光圈控制, JT1078.云台雨刷控制, JT1078.红外补光控制, JT1078.云台变倍控制})
public class T9302 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
    private int channelNo;
    @Field(index = 1, type = DataType.BYTE, desc = "参数(0.调大 1.调小)|(0.停止 1.启动)")
    private int param;

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }
}