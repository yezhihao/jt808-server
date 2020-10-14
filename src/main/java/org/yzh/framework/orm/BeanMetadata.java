package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
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
            log.error("decode error：" + typeClass.getName() + field, e);
        }
        if (isEmpty)
            return null;
        return result;
    }

    public void encode(ByteBuf buf, Object obj) {
        FieldMetadata field = null;
        try {
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                Object value = field.getValue(obj);
                if (value != null)
                    field.writeValue(buf, value);
            }
        } catch (Exception e) {
            log.error("encode error: " + obj + field, e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(48);
        sb.append('{');
        sb.append("typeClass=").append(typeClass.getSimpleName());
        sb.append(", version=").append(version);
        sb.append(", length=").append(length);
        sb.append('}');
        return sb.toString();
    }
}