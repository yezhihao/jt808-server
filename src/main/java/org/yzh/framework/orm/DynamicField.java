package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public abstract class DynamicField<T> extends BasicField<T> {

    protected static final byte[][] BLOCKS = new byte[][]{
            new byte[0],
            new byte[1], new byte[2],
            new byte[3], new byte[4]};

    protected final int lengthSize;

    public DynamicField(Field field, PropertyDescriptor property) {
        super(field, property);
        this.lengthSize = field.lengthSize();
    }

    public boolean readTo(ByteBuf buf, Object target) throws Exception {
        int length = readLength(buf);
        if (!buf.isReadable(length))
            return false;
        Object value = readValue(buf, length);
        writeMethod.invoke(target, value);
        return true;
    }

    public void writeTo(Object source, ByteBuf buf) throws Exception {
        Object value = readMethod.invoke(source);
        if (value != null) {
            int begin = buf.writerIndex();
            buf.writeBytes(BLOCKS[lengthSize]);
            writeValue(buf, (T) value);
            int length = buf.writerIndex() - begin - lengthSize;
            setLength(buf, begin, length);
        }
    }

    protected int readLength(ByteBuf buf) {
        int length;
        switch (lengthSize) {
            case 1:
                length = buf.readUnsignedByte();
                break;
            case 2:
                length = buf.readUnsignedShort();
                break;
            case 3:
                length = buf.readUnsignedMedium();
                break;
            case 4:
                length = buf.readInt();
                break;
            default:
                throw new RuntimeException("unsupported lengthSize: " + lengthSize + " (expected: 1, 2, 3, 4)");
        }
        return length;
    }

    protected void setLength(ByteBuf buf, int offset, int length) {
        switch (lengthSize) {
            case 1:
                buf.setByte(offset, length);
                break;
            case 2:
                buf.setShort(offset, length);
                break;
            case 3:
                buf.setMedium(offset, length);
                break;
            case 4:
                buf.setInt(offset, length);
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