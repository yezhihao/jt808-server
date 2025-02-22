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
@Message(JT808.单条存储多媒体数据检索上传命令)
public class T8805 extends JTMessage {

    @Field(length = 4, desc = "多媒体ID(大于0)")
    private int id;
    @Field(length = 1, desc = "删除标志：0.保留 1.删除 ")
    private int delete;

}