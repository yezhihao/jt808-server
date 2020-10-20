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
@Message(JT1078.终端上传音视频属性)
public class T1003 extends JTMessage {

    private int audioFormat;
    private int audioChannels;
    private int audioSamplingRate;
    private int audioBitDepth;
    private int audioFrameLength;
    private int audioSupport;
    private int videoFormat;
    private int maxAudioChannels;
    private int maxVideoChannels;

    @Field(index = 0, type = DataType.BYTE, desc = "输人音频编码方式")
    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "输人音频声道数")
    public int getAudioChannels() {
        return audioChannels;
    }

    public void setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "输人音频采样率（0: 8kHz; 1: 22.05kHz; 2: 44.1kHz; 3: 48kHz）")
    public int getAudioSamplingRate() {
        return audioSamplingRate;
    }

    public void setAudioSamplingRate(int audioSamplingRate) {
        this.audioSamplingRate = audioSamplingRate;
    }

    @Field(index = 3, type = DataType.BYTE, desc = "输人音频采样位数（0: 8位; 1: 16位; 2: 32位）")
    public int getAudioBitDepth() {
        return audioBitDepth;
    }

    public void setAudioBitDepth(int audioBitDepth) {
        this.audioBitDepth = audioBitDepth;
    }

    @Field(index = 4, type = DataType.WORD, desc = "音频帧长度")
    public int getAudioFrameLength() {
        return audioFrameLength;
    }

    public void setAudioFrameLength(int audioFrameLength) {
        this.audioFrameLength = audioFrameLength;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "是否支持音频输出")
    public int getAudioSupport() {
        return audioSupport;
    }

    public void setAudioSupport(int audioSupport) {
        this.audioSupport = audioSupport;
    }

    @Field(index = 7, type = DataType.BYTE, desc = "视频编码方式")
    public int getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(int videoFormat) {
        this.videoFormat = videoFormat;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "终端支持的最大音频物理通道")
    public int getMaxAudioChannels() {
        return maxAudioChannels;
    }

    public void setMaxAudioChannels(int maxAudioChannels) {
        this.maxAudioChannels = maxAudioChannels;
    }

    @Field(index = 9, type = DataType.BYTE, desc = "终端支持的最大视频物理通道")
    public int getMaxVideoChannels() {
        return maxVideoChannels;
    }

    public void setMaxVideoChannels(int maxVideoChannels) {
        this.maxVideoChannels = maxVideoChannels;
    }
}