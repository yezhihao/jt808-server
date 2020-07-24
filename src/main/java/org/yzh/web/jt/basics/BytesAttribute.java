package org.yzh.web.jt.basics;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * 位置附加信息项
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class BytesAttribute extends AbstractMessage {

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

    @Field(index = 0, type = DataType.BYTE, desc = "附加信息ID", version = {0, 1})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "附加信息长度", version = {0, 1})
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Field(index = 2, type = DataType.BYTES, lengthName = "length", desc = "参数值", version = {0, 1})
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}