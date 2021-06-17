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
@Message(JT1078.查询资源列表)
public class T9205 extends JTMessage {

    private int channelNo;
    private String startTime;
    private String endTime;
    private int warnBit1;
    private int warnBit2;
    private int mediaType;
    private int streamType;
    private int memoryType;

    @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 1, type = DataType.BCD8421, length = 6, desc = "开始时间(YYMMDDHHMMSS,全0表示无起始时间)")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS,全0表示无终止时间)")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Field(index = 13, type = DataType.DWORD, desc = "报警标志0-31(参考808协议文档报警标志位定义)")
    public int getWarnBit1() {
        return warnBit1;
    }

    public void setWarnBit1(int warnBit1) {
        this.warnBit1 = warnBit1;
    }

    @Field(index = 17, type = DataType.DWORD, desc = "报警标志32-63")
    public int getWarnBit2() {
        return warnBit2;
    }

    public void setWarnBit2(int warnBit2) {
        this.warnBit2 = warnBit2;
    }

    @Field(index = 21, type = DataType.BYTE, desc = "音视频资源类型: 0.音视频 1.音频 2.视频 3.视频或音视频")
    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @Field(index = 22, type = DataType.BYTE, desc = "码流类型: 0.所有码流 1.主码流 2.子码流")
    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    @Field(index = 23, type = DataType.BYTE, desc = "存储器类型: 0.所有存储器 1.主存储器 2.灾备存储器")
    public int getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(int memoryType) {
        this.memoryType = memoryType;
    }
}