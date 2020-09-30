package org.yzh.protocol.basics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;

/**
 * 位置附加信息项
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message
public class BytesAttribute {

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

    @Field(index = 0, type = DataType.BYTE, desc = "附加信息ID", version = {-1, 0, 1})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "附加信息长度", version = {-1, 0, 1})
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Field(index = 2, type = DataType.BYTES, lengthName = "length", desc = "参数值", version = {-1, 0, 1})
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
        if (value == null)
            this.length = 0;
        else
            this.length = value.length;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}