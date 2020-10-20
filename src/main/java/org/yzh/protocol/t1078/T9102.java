package org.yzh.protocol.t1078;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.mvc.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.音视频实时传输控制)
public class T9102 extends AbstractMessage<Header> {

    private int channelNo;
    private int command;
    private int closeType;
    private int streamType;

    @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "控制指令（0:关闭音视频传输指令;\n1:切换码流(增加暂停和继续);\n2:暂停该通道所有流的发送;\n3:恢复暂停前流的发送,与暂停前的流类型一致;\n4:关闭双向对讲）")
    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "关闭音视频类型（0:关闭该通道有关的音视频数据;\n1:只关闭该通道有关的音频,保留该通道有关的视频;\n2:只关闭该通道有关的视频,保留该通道有关的音频）")
    public int getCloseType() {
        return closeType;
    }

    public void setCloseType(int closeType) {
        this.closeType = closeType;
    }

    @Field(index = 3, type = DataType.BYTE, desc = "切换码流类型（0:主码流;1:子码流）")
    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }
}