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
@Message(JT1078.音视频实时传输控制)
public class T9102 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "控制指令：" +
            " 0.关闭音视频传输指令" +
            " 1.切换码流(增加暂停和继续)" +
            " 2.暂停该通道所有流的发送" +
            " 3.恢复暂停前流的发送,与暂停前的流类型一致" +
            " 4.关闭双向对讲")
    private int command;
    @Field(length = 1, desc = "关闭音视频类型：" +
            " 0.关闭该通道有关的音视频数据" +
            " 1.只关闭该通道有关的音频,保留该通道有关的视频" +
            " 2.只关闭该通道有关的视频,保留该通道有关的音频")
    private int closeType;
    @Field(length = 1, desc = "切换码流类型：0.主码流 1.子码流")
    private int streamType;

}