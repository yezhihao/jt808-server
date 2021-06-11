package org.yzh.protocol.t1078;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Message(JT1078.终端上传音视频资源列表)
public class T1205 extends JTMessage implements Response {

    private int responseSerialNo;
    private int count;
    private List<Item> items;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    @Field(index = 2, type = DataType.DWORD, desc = "音视频资源总数")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Field(index = 6, type = DataType.LIST, desc = "参数项列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {

        private int channelNo;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private byte[] warningMark;
        private int mediaType;
        private int streamType = 1;
        private int memoryType;
        private long size;

        public Item() {
        }

        public Item(int channelNo, LocalDateTime startTime, LocalDateTime endTime, byte[] warningMark, int mediaType, int streamType, int memoryType, long size) {
            this.channelNo = channelNo;
            this.startTime = startTime;
            this.endTime = endTime;
            this.warningMark = warningMark;
            this.mediaType = mediaType;
            this.streamType = streamType;
            this.memoryType = memoryType;
            this.size = size;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
        public int getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(int channelNo) {
            this.channelNo = channelNo;
        }

        @Field(index = 1, type = DataType.BCD8421, length = 6, desc = "开始时间")
        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "结束时间")
        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        @Field(index = 13, length = 8, type = DataType.BYTES, desc = "报警标志")
        public byte[] getWarningMark() {
            return warningMark;
        }

        public void setWarningMark(byte[] warningMark) {
            this.warningMark = warningMark;
        }

        @Field(index = 21, type = DataType.BYTE, desc = "音视频资源类型")
        public int getMediaType() {
            return mediaType;
        }

        public void setMediaType(int mediaType) {
            this.mediaType = mediaType;
        }

        @Field(index = 22, type = DataType.BYTE, desc = "码流类型")
        public int getStreamType() {
            return streamType;
        }

        public void setStreamType(int streamType) {
            this.streamType = streamType;
        }

        @Field(index = 23, type = DataType.BYTE, desc = "存储器类型")
        public int getMemoryType() {
            return memoryType;
        }

        public void setMemoryType(int memoryType) {
            this.memoryType = memoryType;
        }

        @Field(index = 24, type = DataType.DWORD, desc = "文件大小")
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
            sb.append(", startTime='").append(startTime).append('\'');
            sb.append(", endTime='").append(endTime).append('\'');
            sb.append(", warningMark=").append(Arrays.toString(warningMark));
            sb.append(", mediaType=").append(mediaType);
            sb.append(", streamType=").append(streamType);
            sb.append(", memoryType=").append(memoryType);
            sb.append(", size=").append(size);
            sb.append('}');
            return sb.toString();
        }
    }
}
