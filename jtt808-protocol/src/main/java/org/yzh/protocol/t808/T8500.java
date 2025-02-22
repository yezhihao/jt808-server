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
@Message(JT808.车辆控制)
public class T8500 extends JTMessage {

    @Field(length = 1, desc = "控制标志：[0]0.车门解锁 1.车门加锁 [1~7]保留")
    @Field(length = 2, desc = "控制类型：1.车门加锁 2~8.为标准修订预留", version = 1)
    private int type;
    @Field(length = 1, desc = "控制标志：0.车门解锁 1.车门加锁", version = 1)
    private int param;

}