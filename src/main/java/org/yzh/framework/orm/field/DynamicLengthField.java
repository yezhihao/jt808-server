package org.yzh.framework.orm.field;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.util.ByteBufUtils;

import java.beans.PropertyDescriptor;

/**
 * 动态长度的字段
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DynamicLengthField<T> extends BasicField<T> {

    protected final Schema<T> schema;

    protected final int lengthSize;

    public DynamicLengthField(Field field, PropertyDescriptor property, Schema<T> schema) {
        super(field, property);
        this.schema = schema;
        this.lengthSize = field.lengthSize();
    }

    public boolean readFrom(ByteBuf input, Object message) throws Exception {
        int length = ByteBufUtils.readInt(input, lengthSize);
        if (!input.isReadable(length))
            return false;
        Object value = schema.readFrom(input, length);
        writeMethod.invoke(message, value);
        return true;
    }

    public void writeTo(ByteBuf output, Object message) throws Exception {
        Object value = readMethod.invoke(message);
        if (value != null) {
            int begin = output.writerIndex();
            output.writeBytes(ByteBufUtils.BLOCKS[lengthSize]);
            schema.writeTo(output, (T) value);
            int length = output.writerIndex() - begin - lengthSize;
            ByteBufUtils.setInt(output, lengthSize, begin, length);
        }
    }

    @Override
    public int compareTo(BasicField<T> that) {
        int r = Integer.compare(this.index, that.index);
        if (r == 0)
            r = (that instanceof DynamicLengthField) ? 1 : -1;
        return r;
    }

    public static class Logger<T> extends DynamicLengthField<T> {

        public Logger(Field field, PropertyDescriptor property, Schema<T> schema) {
            super(field, property, schema);
        }

        public boolean readFrom(ByteBuf input, Object message) throws Exception {
            int before = input.readerIndex();

            int length = ByteBufUtils.readInt(input, lengthSize);
            if (!input.isReadable(length))
                return false;
            Object value = schema.readFrom(input, length);
            writeMethod.invoke(message, value);

            int after = input.readerIndex();
            String hex = ByteBufUtil.hexDump(input.slice(before, after - before));
            println(this.index, this.desc, hex, value);
            return true;
        }

        public void writeTo(ByteBuf output, Object message) throws Exception {
            int before = output.writerIndex();

            Object value = readMethod.invoke(message);
            if (value != null) {
                int begin = output.writerIndex();
                output.writeBytes(ByteBufUtils.BLOCKS[lengthSize]);
                schema.writeTo(output, (T) value);
                int length = output.writerIndex() - begin - lengthSize;
                ByteBufUtils.setInt(output, lengthSize, begin, length);
            }

            int after = output.writerIndex();
            String hex = ByteBufUtil.hexDump(output.slice(before, after - before));
            println(this.index, this.desc, hex, value);
        }
    }
}