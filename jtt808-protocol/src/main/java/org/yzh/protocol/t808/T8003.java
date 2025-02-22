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
@Message({JT808.服务器补传分包请求, JT808.终端补传分包请求})
public class T8003 extends JTMessage implements Response {

    @Field(length = 2, desc = "原始消息流水号")
    private int responseSerialNo;
    @Field(totalUnit = 1, desc = "重传包ID列表", version = {-1, 0})
    @Field(totalUnit = 2, desc = "重传包ID列表", version = 1)
    private short[] id;

}