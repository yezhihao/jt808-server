package org.yzh.web.jt808.dto.basics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;

/**
 * 位置附加信息项
 */
public class PositionAttribute extends AbstractBody {

    private Integer id;
    private Integer length;
    private byte[] bytesValue;

    private String name;
    private Object value;

    @JsonIgnore
    @Property(index = 0, type = DataType.BYTE, desc = "附加信息ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    @Property(index = 1, type = DataType.BYTE, desc = "附加信息长度")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @JsonIgnore
    @Property(index = 2, type = DataType.BYTES, lengthName = "length", desc = "参数值")
    public byte[] getBytesValue() {
        return bytesValue;
    }

    public void setBytesValue(byte[] bytesValue) {
        this.bytesValue = bytesValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}