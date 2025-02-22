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
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.临时位置跟踪控制)
public class T8202 extends JTMessage {

    @Field(length = 2, desc = "时间间隔(秒)")
    private int interval;
    @Field(length = 4, desc = "有效期(秒)")
    private int validityPeriod;

}