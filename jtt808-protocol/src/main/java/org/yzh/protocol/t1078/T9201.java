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
@Message(JT1078.平台下发远程录像回放请求)
public class T9201 extends JTMessage {

    @Field(lengthUnit = 1, desc = "服务器IP地址")
    private String ip;
    @Field(length = 2, desc = "实时视频服务器TCP端口号")
    private int tcpPort;
    @Field(length = 2, desc = "实时视频服务器UDP端口号")
    private int udpPort;
    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "音视频资源类型：0.音视频 1.音频 2.视频 3.视频或音视频")
    private int mediaType;
    @Field(length = 1, desc = "码流类型：0.所有码流 1.主码流 2.子码流(如果此通道只传输音频,此字段置0)")
    private int streamType;
    @Field(length = 1, desc = "存储器类型：0.所有存储器 1.主存储器 2.灾备存储器")
    private int storageType;
    @Field(length = 1, desc = "回放方式：0.正常回放 1.快进回放 2.关键帧快退回放 3.关键帧播放 4.单帧上传")
    private int playbackMode;
    @Field(length = 1, desc = "快进或快退倍数：0.无效 1.1倍 2.2倍 3.4倍 4.8倍 5.16倍 (回放控制为1和2时,此字段内容有效,否则置0)")
    private int playbackSpeed;
    @Field(length = 6, charset = "BCD", desc = "开始时间(YYMMDDHHMMSS,回放方式为4时,该字段表示单帧上传时间)")
    private String startTime;
    @Field(length = 6, charset = "BCD", desc = "结束时间(YYMMDDHHMMSS,回放方式为4时,该字段无效,为0表示一直回放)")
    private String endTime;

}