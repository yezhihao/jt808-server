package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Field;

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
        int length = readLength(input);
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
            output.writeBytes(Schema.BLOCKS[lengthSize]);
            schema.writeTo(output, (T) value);
            int length = output.writerIndex() - begin - lengthSize;
            setLength(output, begin, length);
        }
    }

    protected int readLength(ByteBuf input) {
        int length;
        switch (lengthSize) {
            case 1:
                length = input.readUnsignedByte();
                break;
            case 2:
                length = input.readUnsignedShort();
                break;
            case 3:
                length = input.readUnsignedMedium();
                break;
            case 4:
                length = input.readInt();
                break;
            default:
                throw new RuntimeException("unsupported lengthSize: " + lengthSize + " (expected: 1, 2, 3, 4)");
        }
        return length;
    }

    protected void setLength(ByteBuf output, int offset, int length) {
        switch (lengthSize) {
            case 1:
                output.setByte(offset, length);
                break;
            case 2:
                output.setShort(offset, length);
                break;
            case 3:
                output.setMedium(offset, length);
                break;
            case 4:
                output.setInt(offset, length);
                break;
            default:
                throw new RuntimeException("unsupported lengthSize: " + lengthSize + " (expected: 1, 2, 3, 4)");
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

            int length = readLength(input);
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
                output.writeBytes(Schema.BLOCKS[lengthSize]);
                schema.writeTo(output, (T) value);
                int length = output.writerIndex() - begin - lengthSize;
                setLength(output, begin, length);
            }

            int after = output.writerIndex();
            String hex = ByteBufUtil.hexDump(output.slice(before, after - before));
            println(this.index, this.desc, hex, value);
        }
    }
}