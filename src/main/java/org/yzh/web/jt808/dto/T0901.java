package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

@Type(MessageId.数据压缩上报)
public class T0901 extends AbstractBody {

    private Integer length;
    private byte[] body;

    public T0901() {
    }

    @Property(index = 0, type = DataType.DWORD, desc = "压缩消息长度")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Property(index = 4, type = DataType.BYTES, desc = "压缩消息体")
    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}