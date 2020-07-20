package org.yzh.web.jt808.dto.basics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.ParameterId;

/**
 * 终端参数项
 */
public class TerminalParameter extends AbstractBody {

    private Integer id;
    private ParameterId idType;
    private Integer length;
    private byte[] bytesValue;
    private Object value;

    public TerminalParameter() {
    }

    public TerminalParameter(Integer id) {
        this.id = id;
    }

    @Property(index = 0, type = DataType.DWORD, desc = "参数ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.idType = ParameterId.toEnum(id);
    }

    public ParameterId getIdType() {
        return idType;
    }

    public void setIdType(ParameterId idType) {
        this.idType = idType;
        this.id = idType.value;
    }

    @Property(index = 2, type = DataType.BYTE, desc = "参数长度")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Property(index = 3, type = DataType.BYTES, lengthName = "length", desc = "参数值")
    public byte[] getBytesValue() {
        return bytesValue;
    }

    public void setBytesValue(byte[] bytesValue) {
        this.bytesValue = bytesValue;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}