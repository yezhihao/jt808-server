package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.信息点播_取消)
public class T0303 extends JTMessage {

    @Field(length = 1, desc = "消息类型")
    private int type;
    @Field(length = 1, desc = "点播/取消标志：0.取消 1.点播")
    private int action;

}