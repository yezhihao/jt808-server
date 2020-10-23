package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.converter.MapConverter;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class FieldMap<T> extends BasicField<Map<Integer, T>> {

    private int keySize;
    private int valueSize;
    private MapConverter converter;

    public FieldMap(Field field, PropertyDescriptor property) {
        super(field, property);
        try {
            org.yzh.framework.orm.annotation.MapConverter annotation = property.getReadMethod().getAnnotation(org.yzh.framework.orm.annotation.MapConverter.class);
            this.keySize = annotation.keySize();
            this.valueSize = annotation.valueSize();
            this.converter = annotation.converter().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, T> readValue(ByteBuf input, int length) {
        if (!input.isReadable())
            return null;
        Map<Integer, T> map = new HashMap<>();
        do {
            int id = readInt(input, keySize);
            int len = readInt(input, valueSize);
            Object convert = converter.convert(id, input.readSlice(len));
            if (convert == null) break;
            map.put(id, (T) convert);
        } while (input.isReadable());
        return map;
    }

    @Override
    public void writeValue(ByteBuf output, Map<Integer, T> map) {
        if (map == null || map.isEmpty())
            return;
        for (Map.Entry<Integer, T> entry : map.entrySet()) {
            Integer key = entry.getKey();
            T value = entry.getValue();

            writeInt(output, keySize, key);
            int begin = output.writerIndex();
            output.writeBytes(BLOCKS[valueSize]);
            converter.convert(key, output, value);
            int length = output.writerIndex() - begin - valueSize;
            setLength(output, valueSize, begin, length);
        }
    }

    protected int readInt(ByteBuf input, int size) {
        int length;
        switch (size) {
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
                throw new RuntimeException("unsupported size: " + size + " (expected: 1, 2, 3, 4)");
        }
        return length;
    }

    protected void setLength(ByteBuf output, int lengthSize, int offset, int value) {
        switch (lengthSize) {
            case 1:
                output.setByte(offset, value);
                break;
            case 2:
                output.setShort(offset, value);
                break;
            case 3:
                output.setMedium(offset, value);
                break;
            case 4:
                output.setInt(offset, value);
                break;
            default:
                throw new RuntimeException("unsupported lengthSize: " + lengthSize + " (expected: 1, 2, 3, 4)");
        }
    }

    protected void writeInt(ByteBuf output, int length, int value) {
        switch (length) {
            case 1:
                output.writeByte(value);
                break;
            case 2:
                output.writeShort(value);
                break;
            case 3:
                output.writeMedium(value);
                break;
            case 4:
                output.writeInt(value);
                break;
            default:
                throw new RuntimeException("unsupported lengthSize: " + length + " (expected: 1, 2, 3, 4)");
        }
    }
}