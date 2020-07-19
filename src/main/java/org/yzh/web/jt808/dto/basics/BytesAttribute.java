package org.yzh.web.jt808.dto.basics;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;

/**
 * 位置附加信息项
 */
public class BytesAttribute extends AbstractBody {

    private Integer id;
    private Integer length;
    private byte[] value;

    public BytesAttribute() {
    }

    public BytesAttribute(Integer id, byte[] value) {
        this.id = id;
        this.length = value.length;
        this.value = value;
    }

    @Property(index = 0, type = DataType.BYTE, desc = "附加信息ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "附加信息长度")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Property(index = 2, type = DataType.BYTES, lengthName = "length", desc = "参数值")
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}