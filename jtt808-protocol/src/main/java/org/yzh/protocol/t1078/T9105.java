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
@Message(JT1078.实时音视频传输状态通知)
public class T9105 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "丢包率")
    private int packetLossRate;

}
