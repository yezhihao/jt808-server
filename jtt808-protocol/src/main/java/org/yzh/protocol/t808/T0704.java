package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.定位数据批量上传)
public class T0704 extends JTMessage {

    @Field(length = 2, desc = "数据项个数")
    private int total;
    @Field(length = 1, desc = "位置数据类型：0.正常位置批量汇报 1.盲区补报")
    private int type;
    @Field(lengthUnit = 2, desc = "位置汇报数据项")
    private List<T0200> items;

}