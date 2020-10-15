package org.yzh.protocol.basics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.transform.TerminalParameterUtils;

/**
 * 终端参数项
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message
public class BytesParameter {

    private Integer id;
    private byte[] value;

    public BytesParameter() {
    }

    public BytesParameter(Integer id, byte[] value) {
        this.id = id;
        this.value = value;
    }

    public BytesParameter(Integer id, int value) {
        this.id = id;
        byte[] bytes = TerminalParameterUtils.toBytes(id, value);
        this.value = bytes;
    }


    public BytesParameter(Integer id, String value) {
        this.id = id;
        this.value = value.getBytes(Charsets.GBK);
    }

    @Field(index = 0, type = DataType.DWORD, desc = "参数ID", version = {-1, 0, 1})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 3, type = DataType.BYTES, lengthSize = 1, desc = "参数值", version = {-1, 0, 1})
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}