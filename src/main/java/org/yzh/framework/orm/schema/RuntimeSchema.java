package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.field.BasicField;

/**
 * 运行时根据Class生成的消息结构，用于序列化对象
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class RuntimeSchema<T> implements Schema<T> {

    protected final int version;
    protected final int length;
    protected final Class<T> typeClass;
    protected final BasicField[] fields;

    public RuntimeSchema(Class<T> typeClass, int version, BasicField[] fields) {
        this.typeClass = typeClass;
        this.version = version;
        this.fields = fields;
        BasicField lastField = fields[fields.length - 1];
        int lastIndex = lastField.index();
        int lastLength = lastField.length() < 0 ? 256 : lastField.length();
        this.length = lastIndex + lastLength;
    }

    public T readFrom(ByteBuf input) {
        T message = null;
        boolean isEmpty = true;//防止死循环
        BasicField field = null;
        try {
            message = typeClass.newInstance();
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                if (!input.isReadable())
                    break;
                field.readFrom(input, message);
                isEmpty = false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Serialization failed readFrom " + typeClass.getName() + field, e);
        }
        if (isEmpty)
            return null;
        return message;
    }

    public void writeTo(ByteBuf output, T message) {
        BasicField field = null;
        try {
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                field.writeTo(output, message);
            }
        } catch (Exception e) {
            throw new RuntimeException("Serialization failed writeTo " + typeClass.getName() + field, e);
        }
    }

    public int length() {
        return length;
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