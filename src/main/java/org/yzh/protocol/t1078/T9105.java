package org.yzh.protocol.t1078;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.实时音视频传输状态通知)
public class T9105 extends JTMessage {

    private int channelNo;
    private int packetLossRate;

    @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "丢包率")
    public int getPacketLossRate() {
        return packetLossRate;
    }

    public void setPacketLossRate(int packetLossRate) {
        this.packetLossRate = packetLossRate;
    }
}
