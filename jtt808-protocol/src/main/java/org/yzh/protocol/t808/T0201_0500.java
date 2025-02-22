package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.MergeSuperclass;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@MergeSuperclass
@Message({JT808.位置信息查询应答, JT808.车辆控制应答})
public class T0201_0500 extends T0200 implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;

}