package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

/**
 * 动态长度的字段
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class DynamicField<T> extends BasicField<T> {

    /** 长度域占位数据块 */
    protected static final byte[][] BLOCKS = new byte[][]{
            new byte[0],
            new byte[1], new byte[2],
            new byte[3], new byte[4]};

    protected final int lengthSize;

    public DynamicField(Field field, PropertyDescriptor property) {
        super(field, property);
        this.lengthSize = field.lengthSize();
    }

    public boolean readFrom(ByteBuf input, Object message) throws Exception {
        int length = readLength(input);
        if (!input.isReadable(length))
            return false;
        Object value = readValue(input, length);
        writeMethod.invoke(message, value);
        return true;
    }

    public void writeTo(ByteBuf output, Object message) throws Exception {
        Object value = readMethod.invoke(message);
        if (value != null) {
            int begin = output.writerIndex();
            output.writeBytes(BLOCKS[lengthSize]);
            writeValue(output, (T) value);
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
    public int compareTo(BasicField that) {
        int r = Integer.compare(this.index, that.index);
        if (r == 0) {
            if (BasicField.class.isAssignableFrom(that.getClass()))
                r = -1;
        }
        return r;
    }
}