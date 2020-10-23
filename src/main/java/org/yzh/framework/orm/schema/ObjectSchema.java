package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

import java.util.HashMap;
import java.util.Map;

public class ObjectSchema<T> implements Schema<T> {

    private static volatile Map<Schema, ObjectSchema> cache = new HashMap<>();

    public static Schema getInstance(Schema schema) {
        ObjectSchema instance = cache.get(schema);
        if (instance == null) {
            synchronized (cache) {
                if (instance == null) {
                    instance = new ObjectSchema(schema);
                    cache.put(schema, instance);
                    log.debug("new ObjectSchema({})", schema);
                }
            }
        }
        return instance;
    }

    private final Schema<T> schema;

    private ObjectSchema(Schema<T> schema) {
        this.schema = schema;
    }

    @Override
    public T readFrom(ByteBuf input) {
        return schema.readFrom(input);
    }

    @Override
    public T readFrom(ByteBuf input, int length) {
        if (length > 0)
            input = input.readSlice(length);
        return schema.readFrom(input);
    }

    @Override
    public void writeTo(ByteBuf output, T message) {
        schema.writeTo(output, message);
    }

    @Override
    public void writeTo(ByteBuf output, int length, T obj) {
        schema.writeTo(output, obj);
    }
}