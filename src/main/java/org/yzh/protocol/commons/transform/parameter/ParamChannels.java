package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

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

    private byte audioVideoChannels;
    private byte audioChannels;
    private byte videoChannels;
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
        private byte channelId;
        private byte channelNo;
        private byte channelType;
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamChannels> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
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