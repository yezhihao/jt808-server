package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
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
@Message({JT808.平台通用应答, JT808.终端通用应答})
public class T0001 extends JTMessage implements Response {

    public static final int Success = 0; //成功、确认
    public static final int Failure = 1;//失败
    public static final int MessageError = 2;//消息有误
    public static final int NotSupport = 3;//不支持
    public static final int AlarmAck = 4;//报警处理确认

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(length = 2, desc = "应答ID")
    private int responseMessageId;
    @Field(length = 1, desc = "结果：0.成功 1.失败 2.消息有误 3.不支持 4.报警处理确认")
    private int resultCode;

    public boolean isSuccess() {
        return this.resultCode == Success;
    }
}