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
@Message(JT1078.云台旋转)
public class T9301 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "方向：0.停止 1.上 2.下 3.左 4.右")
    private int param1;
    @Field(length = 1, desc = "速度(0~255)")
    private int param2;

}
