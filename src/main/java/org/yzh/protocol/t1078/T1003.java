package org.yzh.protocol.t1078;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.终端上传音视频属性)
public class T1003 extends AbstractMessage<Header> {

    private int soundFormat;
    private int soundChannel;
    private int soundSamplingRate;
    private int soundSamplingBits;
    private int soundFrameLength;
    private int soundable;
    private int videoFormat;
    private int maxSoundChannels;
    private int maxVideoChannels;

    @Field(index = 0, type = DataType.BYTE, desc = "输人音频编码方式")
    public int getSoundFormat() {
        return soundFormat;
    }

    public void setSoundFormat(int soundFormat) {
        this.soundFormat = soundFormat;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "输人音频声道数")
    public int getSoundChannel() {
        return soundChannel;
    }

    public void setSoundChannel(int soundChannel) {
        this.soundChannel = soundChannel;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "输人音频采样率（0: 8kHz; 1: 22.05kHz; 2: 44.1kHz; 3: 48kHz）")
    public int getSoundSamplingRate() {
        return soundSamplingRate;
    }

    public void setSoundSamplingRate(int soundSamplingRate) {
        this.soundSamplingRate = soundSamplingRate;
    }

    @Field(index = 3, type = DataType.BYTE, desc = "输人音频采样位数（0: 8位; 1: 16位; 2: 32位）")
    public int getSoundSamplingBits() {
        return soundSamplingBits;
    }

    public void setSoundSamplingBits(int soundSamplingBits) {
        this.soundSamplingBits = soundSamplingBits;
    }

    @Field(index = 4, type = DataType.WORD, desc = "音频帧长度")
    public int getSoundFrameLength() {
        return soundFrameLength;
    }

    public void setSoundFrameLength(int soundFrameLength) {
        this.soundFrameLength = soundFrameLength;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "是否支持音频输出")
    public int getSoundable() {
        return soundable;
    }

    public void setSoundable(int soundable) {
        this.soundable = soundable;
    }

    @Field(index = 7, type = DataType.BYTE, desc = "视频编码方式")
    public int getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(int videoFormat) {
        this.videoFormat = videoFormat;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "终端支持的最大音频物理通道")
    public int getMaxSoundChannels() {
        return maxSoundChannels;
    }

    public void setMaxSoundChannels(int maxSoundChannels) {
        this.maxSoundChannels = maxSoundChannels;
    }

    @Field(index = 9, type = DataType.BYTE, desc = "终端支持的最大视频物理通道")
    public int getMaxVideoChannels() {
        return maxVideoChannels;
    }

    public void setMaxVideoChannels(int maxVideoChannels) {
        this.maxVideoChannels = maxVideoChannels;
    }
}