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
@Message(JT1078.终端上传乘客流量)
public class T1005 extends JTMessage {

    @Field(length = 6, charset = "BCD", desc = "起始时间(YYMMDDHHMMSS)")
    private String startTime;
    @Field(length = 6, charset = "BCD", desc = "结束时间(YYMMDDHHMMSS)")
    private String endTime;
    @Field(length = 2, desc = "从起始时间到结束时间的上车人数")
    private int getOnCount;
    @Field(length = 2, desc = "从起始时间到结束时间的下车人数")
    private int getOffCount;

}