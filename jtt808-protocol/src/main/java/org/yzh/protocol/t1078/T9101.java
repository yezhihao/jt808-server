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
@Message(JT1078.实时音视频传输请求)
public class T9101 extends JTMessage {

    @Field(lengthUnit = 1, desc = "服务器IP地址")
    private String ip;
    @Field(length = 2, desc = "实时视频服务器TCP端口号")
    private int tcpPort;
    @Field(length = 2, desc = "实时视频服务器UDP端口号")
    private int udpPort;
    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "数据类型：0.音视频 1.视频 2.双向对讲 3.监听 4.中心广播 5.透传")
    private int mediaType;
    @Field(length = 1, desc = "码流类型：0.主码流 1.子码流")
    private int streamType;

}