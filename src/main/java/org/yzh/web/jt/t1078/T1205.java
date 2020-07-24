package org.yzh.web.jt.t1078;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT1078;

import java.io.Serializable;
import java.util.List;

/**
 * Created by
 */
@Message(JT1078.终端上传音视频资源列表)
public class T1205 extends AbstractMessage<Header> implements Serializable {

    @ApiModelProperty("序列号")
    private Integer serialNo;

    /** 音视频总数 */
    @ApiModelProperty("音视频总数")
    private Integer count;

    private List<VideoHis> videoHis;

    /** 对应的终端注册消息的流水号 */
    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.DWORD, desc = "音视频资源总数")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Field(index = 6, type = DataType.LIST, desc = "参数项列表")
    public List<VideoHis> getVideoHis() {
        return videoHis;
    }

    public void setVideoHis(List<VideoHis> videoHis) {
        this.videoHis = videoHis;
    }

    @ApiModel(description = "资源列表返回实体")
    public static class VideoHis {

        @ApiModelProperty("信道号")
        private Integer channelId;
        @ApiModelProperty("开始时间")
        private String startTime;
        @ApiModelProperty("结束时间")
        private String endTime;
        @ApiModelProperty("报警标志")
        private byte[] warningMark;
        @ApiModelProperty("音视频资源类型")
        private Integer avItemType;
        @ApiModelProperty("码流类型")
        private Integer streamType = 1;
        @ApiModelProperty("存储器类型")
        private Integer romType;

        @ApiModelProperty("文件大小")
        private Long size;

        @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
        public Integer getChannelId() {
            return channelId;
        }

        public void setChannelId(Integer channelId) {
            this.channelId = channelId;
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
        public Integer getAvItemType() {
            return avItemType;
        }

        public void setAvItemType(Integer avItemType) {
            this.avItemType = avItemType;
        }

        @Field(index = 22, type = DataType.BYTE, desc = "码流类型")
        public Integer getStreamType() {
            return streamType;
        }

        public void setStreamType(Integer streamType) {
            this.streamType = streamType;
        }

        @Field(index = 23, type = DataType.BYTE, desc = "存储器类型")
        public Integer getRomType() {
            return romType;
        }

        public void setRomType(Integer romType) {
            this.romType = romType;
        }

        @Field(index = 24, type = DataType.DWORD, desc = "文件大小")
        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

    }

    @Override
    public String toString() {
        return "Command1205{" +
                "serialNo=" + serialNo +
                ", count=" + count +
                ", videoHis=" + videoHis +
                '}';
    }
}
