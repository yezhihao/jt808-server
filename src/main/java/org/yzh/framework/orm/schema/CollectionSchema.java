package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionSchema<T> implements Schema<List<T>> {

    private static volatile Map<Schema, CollectionSchema> cache = new HashMap<>();

    public static Schema<List> getInstance(Schema schema) {
        CollectionSchema instance = cache.get(schema);
        if (instance == null) {
            synchronized (cache) {
                if (instance == null) {
                    instance = new CollectionSchema(schema);
                    cache.put(schema, instance);
                    log.debug("new CollectionSchema({})", schema);
                }
            }
        }
        return instance;
    }

    private final Schema<T> schema;

    private CollectionSchema(Schema<T> schema) {
        this.schema = schema;
    }

    @Override
    public List<T> readFrom(ByteBuf input) {
        if (!input.isReadable())
            return null;
        List<T> list = new ArrayList<>();
        do {
            T obj = schema.readFrom(input);
            if (obj == null) break;
            list.add(obj);
        } while (input.isReadable());
        return list;
    }


    @Override
    public List<T> readFrom(ByteBuf input, int length) {
        return this.readFrom(input.readSlice(length));
    }

    @Override
    public void writeTo(ByteBuf output, List<T> list) {
        if (list == null || list.isEmpty())
            return;

        for (T obj : list) {
            schema.writeTo(output, obj);
        }
    }

    @Override
    public void writeTo(ByteBuf output, int length, List<T> list) {
        if (list == null || list.isEmpty())
            return;

        for (T obj : list) {
            schema.writeTo(output, length, obj);
        }
    }
}