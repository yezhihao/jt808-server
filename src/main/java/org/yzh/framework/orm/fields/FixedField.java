package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

/**
 * 原子域
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class FixedField<T> extends BasicField<T> {

    protected final Schema<T> schema;

    public FixedField(Field field, PropertyDescriptor property, Schema<T> schema) {
        super(field, property);
        this.schema = schema;
    }

    public boolean readFrom(ByteBuf input, Object message) throws Exception {
        Object value = schema.readFrom(input);
        writeMethod.invoke(message, value);
        return true;
    }

    public void writeTo(ByteBuf output, Object message) throws Exception {
        Object value = readMethod.invoke(message);
        if (value != null)
            schema.writeTo(output, (T) value);
    }

    public static class Logger<T> extends FixedField<T> {

        public Logger(Field field, PropertyDescriptor property, Schema<T> schema) {
            super(field, property, schema);
        }

        public boolean readFrom(ByteBuf input, Object message) throws Exception {
            int before = input.readerIndex();

            Object value = schema.readFrom(input);
            writeMethod.invoke(message, value);

            int after = input.readerIndex();
            String hex = ByteBufUtil.hexDump(input.slice(before, after - before));
            println(this.index, this.desc, hex, value);
            return true;
        }

        public void writeTo(ByteBuf output, Object message) throws Exception {
            int before = output.writerIndex();

            Object value = readMethod.invoke(message);
            if (value != null)
                schema.writeTo(output, (T) value);

            int after = output.writerIndex();
            String hex = ByteBufUtil.hexDump(output.slice(before, after - before));
            println(this.index, this.desc, hex, value);
        }
    }
}