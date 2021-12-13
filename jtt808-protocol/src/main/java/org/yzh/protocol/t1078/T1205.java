package org.yzh.protocol.t1078;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

import java.time.LocalDateTime;
import java.util.List;

@Message(JT1078.终端上传音视频资源列表)
public class T1205 extends JTMessage implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(lengthSize = 4, desc = "音视频资源列表")
    private List<Item> items;

    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {

        @Field(length = 1, desc = "逻辑通道号")
        private int channelNo;
        @Field(length = 6, charset = "BCD", desc = "开始时间")
        private LocalDateTime startTime;
        @Field(length = 6, charset = "BCD", desc = "结束时间")
        private LocalDateTime endTime;
        @Field(length = 4, desc = "报警标志0~31(参考808协议文档报警标志位定义)")
        private int warnBit1;
        @Field(length = 4, desc = "报警标志32~63")
        private int warnBit2;
        @Field(length = 1, desc = "音视频资源类型")
        private int mediaType;
        @Field(length = 1, desc = "码流类型")
        private int streamType = 1;
        @Field(length = 1, desc = "存储器类型")
        private int storageType;
        @Field(length = 4, desc = "文件大小")
        private long size;

        public Item() {
        }

        public Item(int channelNo, LocalDateTime startTime, LocalDateTime endTime, int warnBit1, int warnBit2, int mediaType, int streamType, int storageType, long size) {
            this.channelNo = channelNo;
            this.startTime = startTime;
            this.endTime = endTime;
            this.warnBit1 = warnBit1;
            this.warnBit2 = warnBit2;
            this.mediaType = mediaType;
            this.streamType = streamType;
            this.storageType = storageType;
            this.size = size;
        }

        public int getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(int channelNo) {
            this.channelNo = channelNo;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public int getWarnBit1() {
            return warnBit1;
        }

        public void setWarnBit1(int warnBit1) {
            this.warnBit1 = warnBit1;
        }

        public int getWarnBit2() {
            return warnBit2;
        }

        public void setWarnBit2(int warnBit2) {
            this.warnBit2 = warnBit2;
        }

        public int getMediaType() {
            return mediaType;
        }

        public void setMediaType(int mediaType) {
            this.mediaType = mediaType;
        }

        public int getStreamType() {
            return streamType;
        }

        public void setStreamType(int streamType) {
            this.streamType = streamType;
        }

        public int getStorageType() {
            return storageType;
        }

        public void setStorageType(int storageType) {
            this.storageType = storageType;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(128);
            sb.append('{');
            sb.append("channelNo=").append(channelNo);
            sb.append(",startTime=").append(startTime);
            sb.append(",endTime=").append(endTime);
            sb.append(",warnBit1=").append(Integer.toBinaryString(warnBit1));
            sb.append(",warnBit2=").append(Integer.toBinaryString(warnBit2));
            sb.append(",mediaType=").append(mediaType);
            sb.append(",streamType=").append(streamType);
            sb.append(",storageType=").append(storageType);
            sb.append(",size=").append(size);
            sb.append('}');
            return sb.toString();
        }
    }
}
