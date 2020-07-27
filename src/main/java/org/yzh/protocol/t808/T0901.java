package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.数据压缩上报)
public class T0901 extends AbstractMessage<Header> {

    private Integer length;
    private byte[] body;

    public T0901() {
    }

    @Field(index = 0, type = DataType.DWORD, desc = "压缩消息长度")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Field(index = 4, type = DataType.BYTES, desc = "压缩消息体")
    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}