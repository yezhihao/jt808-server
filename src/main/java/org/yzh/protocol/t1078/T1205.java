package org.yzh.protocol.t1078;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT1078;

import java.util.Arrays;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.终端上传音视频资源列表)
public class T1205 extends AbstractMessage<Header> {

    private int serialNo;
    private int count;
    private List<Item> items;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
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
        private String startTime;
        private String endTime;
        private byte[] warningMark;
        private int avItemType;
        private int streamType = 1;
        private int romType;
        private Long size;

        @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
        public int getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(int channelNo) {
            this.channelNo = channelNo;
        }

        @Field(index = 1, type = DataType.BCD8421, length = 6, desc = "开始时间")
        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "结束时间")
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
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
        public int getAvItemType() {
            return avItemType;
        }

        public void setAvItemType(int avItemType) {
            this.avItemType = avItemType;
        }

        @Field(index = 22, type = DataType.BYTE, desc = "码流类型")
        public int getStreamType() {
            return streamType;
        }

        public void setStreamType(int streamType) {
            this.streamType = streamType;
        }

        @Field(index = 23, type = DataType.BYTE, desc = "存储器类型")
        public int getRomType() {
            return romType;
        }

        public void setRomType(int romType) {
            this.romType = romType;
        }

        @Field(index = 24, type = DataType.DWORD, desc = "文件大小")
        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("channelNo=").append(channelNo);
            sb.append(", startTime='").append(startTime).append('\'');
            sb.append(", endTime='").append(endTime).append('\'');
            sb.append(", warningMark=").append(Arrays.toString(warningMark));
            sb.append(", avItemType=").append(avItemType);
            sb.append(", streamType=").append(streamType);
            sb.append(", romType=").append(romType);
            sb.append(", size=").append(size);
            sb.append('}');
            return sb.toString();
        }
    }
}
