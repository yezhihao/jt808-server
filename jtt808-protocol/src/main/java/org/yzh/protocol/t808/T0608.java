package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询区域或线路数据应答)
public class T0608 extends JTMessage {

    /** @see org.yzh.protocol.commons.Shape */
    @Field(length = 1, desc = "查询类型")
    private int type;
    @Field(totalUnit = 4, desc = "查询返回的数据")
    private byte[] bytes;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}