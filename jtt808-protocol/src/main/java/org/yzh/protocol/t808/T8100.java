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
@Message(JT808.终端注册应答)
public class T8100 extends JTMessage implements Response {

    /** 0.成功 */
    public static final int Success = 0;
    /** 1.车辆已被注册 */
    public static final int AlreadyRegisteredVehicle = 1;
    /** 2.数据库中无该车辆 */
    public static final int NotFoundVehicle = 2;
    /** 3.终端已被注册 */
    public static final int AlreadyRegisteredTerminal = 3;
    /** 4.数据库中无该终端 */
    public static final int NotFoundTerminal = 4;

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(length = 1, desc = "结果：0.成功 1.车辆已被注册 2.数据库中无该车辆 3.终端已被注册 4.数据库中无该终端")
    private int resultCode;
    @Field(desc = "鉴权码(成功后才有该字段)")
    private String token;

}