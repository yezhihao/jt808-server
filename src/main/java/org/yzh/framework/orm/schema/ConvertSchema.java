package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义结构转换
 */
public class ConvertSchema<T> implements Schema<T> {

    private static volatile Map<String, ConvertSchema> cache = new HashMap<>();

    public static Schema getInstance(Class<? extends Converter> clazz) {
        String name = clazz.getName();
        ConvertSchema instance = cache.get(name);
        if (instance == null) {
            synchronized (cache) {
                if (instance == null) {
                    try {
                        Converter converter = clazz.newInstance();
                        instance = new ConvertSchema(converter);
                        cache.put(name, instance);
                        log.debug("new ConvertSchema({})", clazz);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }

    private final Converter<T> converter;

    private ConvertSchema(Converter<T> converter) {
        this.converter = converter;
    }

    @Override
    public T readFrom(ByteBuf input) {
        return converter.convert(input);
    }

    @Override
    public T readFrom(ByteBuf input, int length) {
        if (length > 0)
            input = input.readSlice(length);
        return converter.convert(input);
    }

    @Override
    public void writeTo(ByteBuf output, T message) {
        converter.convert(output, message);
    }

    @Override
    public void writeTo(ByteBuf output, int length, T obj) {
        converter.convert(output, obj);
    }
}