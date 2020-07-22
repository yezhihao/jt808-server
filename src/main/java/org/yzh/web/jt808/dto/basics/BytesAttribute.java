package org.yzh.web.jt808.dto.basics;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.framework.orm.annotation.Type;

/**
 * 位置附加信息项
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type
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

    @Property(index = 0, type = DataType.BYTE, desc = "附加信息ID", version = {0, 1})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "附加信息长度", version = {0, 1})
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Property(index = 2, type = DataType.BYTES, lengthName = "length", desc = "参数值", version = {0, 1})
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}