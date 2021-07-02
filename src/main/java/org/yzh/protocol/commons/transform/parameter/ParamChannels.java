package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * 音视频通道列表设置
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamChannels {

    public static final int id = 0x0076;

    public static int id() {
        return id;
    }

    @Schema(description = "音视频通道总数")
    private byte audioVideoChannels;
    @Schema(description = "音频通道总数")
    private byte audioChannels;
    @Schema(description = "视频通道总数")
    private byte videoChannels;
    @Schema(description = "音视频通道对照表")
    private List<ChannelInfo> channels;

    public ParamChannels() {
    }

    public byte getAudioVideoChannels() {
        return audioVideoChannels;
    }

    public void setAudioVideoChannels(byte audioVideoChannels) {
        this.audioVideoChannels = audioVideoChannels;
    }

    public byte getAudioChannels() {
        return audioChannels;
    }

    public void setAudioChannels(byte audioChannels) {
        this.audioChannels = audioChannels;
    }

    public byte getVideoChannels() {
        return videoChannels;
    }

    public void setVideoChannels(byte videoChannels) {
        this.videoChannels = videoChannels;
    }

    public List<ChannelInfo> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelInfo> channels) {
        this.channels = channels;
    }

    public static class ChannelInfo {
        @Schema(description = "物理通道号(从1开始)")
        private byte channelId;
        @Schema(description = "逻辑通道号(按照JT/T 1076-2016 中的表2)")
        private byte channelNo;
        @Schema(description = "通道类型：0.音视频 1.音频 2.视频")
        private byte channelType;
        @Schema(description = "是否连接云台(类型为0和2时,此字段有效)：0.未连接 1.连接")
        private boolean hasPanTilt;

        public ChannelInfo() {
        }

        public ChannelInfo(byte channelId, byte channelNo, byte channelType, boolean hasPanTilt) {
            this.channelId = channelId;
            this.channelNo = channelNo;
            this.channelType = channelType;
            this.hasPanTilt = hasPanTilt;
        }

        public byte getChannelId() {
            return channelId;
        }

        public void setChannelId(byte channelId) {
            this.channelId = channelId;
        }

        public byte getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(byte channelNo) {
            this.channelNo = channelNo;
        }

        public byte getChannelType() {
            return channelType;
        }

        public void setChannelType(byte channelType) {
            this.channelType = channelType;
        }

        public boolean isHasPanTilt() {
            return hasPanTilt;
        }

        public void setHasPanTilt(boolean hasPanTilt) {
            this.hasPanTilt = hasPanTilt;
        }
    }

    public static class S implements io.github.yezhihao.protostar.Schema<ParamChannels> {

        public static final S INSTANCE = new S();

        private S() {
        }

        @Override
        public ParamChannels readFrom(ByteBuf input) {
            ParamChannels message = new ParamChannels();
            message.audioVideoChannels = input.readByte();
            message.audioChannels = input.readByte();
            message.videoChannels = input.readByte();

            List<ChannelInfo> channels = new ArrayList<>();
            while (input.isReadable()) {
                byte channelId = input.readByte();
                byte channelNo = input.readByte();
                byte channelType = input.readByte();
                boolean hasPanTilt = input.readBoolean();
                channels.add(new ChannelInfo(channelId, channelNo, channelType, hasPanTilt));
            }
            message.setChannels(channels);
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamChannels message) {
            output.writeByte(message.audioVideoChannels);
            output.writeByte(message.audioChannels);
            output.writeByte(message.videoChannels);

            List<ChannelInfo> channelInfos = message.getChannels();
            for (ChannelInfo channelInfo : channelInfos) {
                output.writeByte(channelInfo.channelId);
                output.writeByte(channelInfo.channelNo);
                output.writeByte(channelInfo.channelType);
                output.writeBoolean(channelInfo.hasPanTilt);
            }
        }
    }
}