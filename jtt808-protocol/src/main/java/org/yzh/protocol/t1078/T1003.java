package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT1078.终端上传音视频属性)
public class T1003 extends JTMessage {

    @Field(length = 1, desc = "输入音频编码方式")
    private int audioFormat;
    @Field(length = 1, desc = "输入音频声道数")
    private int audioChannels;
    @Field(length = 1, desc = "输入音频采样率：0.8kHz 1.22.05kHz 2.44.1kHz 3.48kHz")
    private int audioSamplingRate;
    @Field(length = 1, desc = "输入音频采样位数：0.8位 1.16位 2.32位")
    private int audioBitDepth;
    @Field(length = 2, desc = "音频帧长度")
    private int audioFrameLength;
    @Field(length = 1, desc = "是否支持音频输出")
    private int audioSupport;
    @Field(length = 1, desc = "视频编码方式")
    private int videoFormat;
    @Field(length = 1, desc = "终端支持的最大音频物理通道")
    private int maxAudioChannels;
    @Field(length = 1, desc = "终端支持的最大视频物理通道")
    private int maxVideoChannels;

}