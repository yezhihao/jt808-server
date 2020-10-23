package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Convert;
import org.yzh.framework.orm.converter.Converter;
import org.yzh.framework.orm.util.ByteBufUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * 字典结构
 */
public class MapSchema<T> implements Schema<Map<Integer, T>> {

    private int keySize;
    private int valueSize;
    private Converter converter;

    public MapSchema(PropertyDescriptor property) {
        try {
            log.debug("new ObjectSchema({})", property);
            Convert annotation = property.getReadMethod().getAnnotation(Convert.class);
            this.keySize = annotation.keySize();
            this.valueSize = annotation.valueSize();
            this.converter = annotation.converter().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, T> readFrom(ByteBuf input) {
        if (!input.isReadable())
            return null;
        Map<Integer, T> map = new HashMap<>();
        do {
            int id = ByteBufUtils.readInt(input, keySize);
            int len = ByteBufUtils.readInt(input, valueSize);
            Object value = converter.convert(id, input.readSlice(len));
            if (value == null) break;
            map.put(id, (T) value);
        } while (input.isReadable());
        return map;
    }

    @Override
    public void writeTo(ByteBuf output, Map<Integer, T> map) {
        if (map == null || map.isEmpty())
            return;
        for (Map.Entry<Integer, T> entry : map.entrySet()) {
            Integer key = entry.getKey();
            T value = entry.getValue();

            ByteBufUtils.writeInt(output, keySize, key);
            int begin = output.writerIndex();
            output.writeBytes(ByteBufUtils.BLOCKS[valueSize]);
            converter.convert(key, output, value);
            int len = output.writerIndex() - begin - valueSize;
            ByteBufUtils.setInt(output, valueSize, begin, len);
        }
    }
}