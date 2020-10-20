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
@Message(JT1078.查询资源列表)
public class T9205 extends JTMessage {

    private int channelNo;
    private String startTime;
    private String endTime;
    private byte[] warningMark;
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

    @Field(index = 1, type = DataType.BCD8421, length = 6, desc = "开始时间（yyMMddHHmmss,全0表示无起始时间）")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "结束时间（yyMMddHHmmss,全0表示无终止时间）")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Field(index = 13, type = DataType.BYTES, length = 8, desc = "报警标志")
    public byte[] getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(byte[] warningMark) {
        this.warningMark = warningMark;
    }

    @Field(index = 21, type = DataType.BYTE, desc = "音视频资源类型（0:音视频,1:音频,2:视频,3:视频或音视频）")
    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @Field(index = 22, type = DataType.BYTE, desc = "码流类型（0:所有码流,1:主码流,2:子码流）")
    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    @Field(index = 23, type = DataType.BYTE, desc = "存储器类型（0:所有存储器,1:主存储器,2:灾备存储器）")
    public int getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(int memoryType) {
        this.memoryType = memoryType;
    }
}