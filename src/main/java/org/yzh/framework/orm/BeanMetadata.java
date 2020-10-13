package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息元数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class BeanMetadata<T> {
    protected static Logger log = LoggerFactory.getLogger(BeanMetadata.class.getSimpleName());

    protected final int version;
    protected final int length;
    protected final Class<T> typeClass;
    protected final FieldMetadata[] fields;

    public BeanMetadata(Class<T> typeClass, int version, FieldMetadata[] fields) {
        this.typeClass = typeClass;
        this.version = version;
        this.fields = fields;
        FieldMetadata lastField = fields[fields.length - 1];
        int lastIndex = lastField.index;
        int lastLength = lastField.length < 0 ? 4 : lastField.length;
        this.length = lastIndex + lastLength;
    }

    public int getLength() {
        return length;
    }

    public T decode(ByteBuf buf) {
        T result = null;
        boolean isEmpty = true;//防止死循环
        FieldMetadata field = null;
        int length;
        try {
            result = typeClass.newInstance();
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];

                length = field.getLength(result);

                if (!buf.isReadable(length))
                    break;
                Object value = field.readValue(buf, length);
                field.setValue(result, value);
                isEmpty = false;
            }
        } catch (Exception e) {
            log.error("解码异常：" + typeClass.getName() + field, e);
        }
        if (isEmpty)
            return null;
        return result;
    }

    public void encode(ByteBuf buf, Object obj) {
        try {
            for (FieldMetadata field : fields) {
                Object value = field.getValue(obj);
                if (value != null)
                    field.writeValue(buf, value);
            }
        } catch (Exception e) {
            log.error("获取对象值失败", e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("typeClass", typeClass)
                .append("version", version)
                .append("length", length)
                .toString();
    }
}