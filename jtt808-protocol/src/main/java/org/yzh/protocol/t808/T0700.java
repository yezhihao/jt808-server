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
@Message(JT808.行驶记录数据上传)
public class T0700 extends JTMessage implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(length = 1, desc = "命令字")
    private int command;
    @Field(desc = "数据块")
    private byte[] data;

}