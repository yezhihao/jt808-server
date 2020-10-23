package org.yzh.framework.orm;

import org.yzh.framework.orm.model.DataType;

import java.util.HashMap;
import java.util.Map;

public abstract class PrepareLoadStrategy extends IdStrategy {

    private final Map<Object, Schema> typeClassMapping = new HashMap<>();

    protected PrepareLoadStrategy() {
        this.addSchemas(this);
    }

    protected abstract void addSchemas(PrepareLoadStrategy schemaRegistry);

    @Override
    public <T> Schema<T> getSchema(Class<T> typeClass) {
        return typeClassMapping.get(typeClass);
    }

    public PrepareLoadStrategy addSchema(Object key, Schema schema) {
        typeIdMapping.put(key, schema);
        return this;
    }

    public PrepareLoadStrategy addSchema(Object key, Class typeClass) {
        loadSchema(typeClassMapping, key, typeClass);
        return this;
    }

    public PrepareLoadStrategy addSchema(Object key, DataType dataType) {
        switch (dataType) {
            case BYTE:
                this.typeIdMapping.put(key, SchemaInt8.instance);
                break;
            case WORD:
                this.typeIdMapping.put(key, SchemaInt16.instance);
                break;
            case DWORD:
                this.typeIdMapping.put(key, SchemaInt32.instance);
                break;
            case BYTES:
                this.typeIdMapping.put(key, SchemaBytes.instance);
                break;
            case STRING:
                this.typeIdMapping.put(key, SchemaString.instance);
                break;
            default:
                throw new RuntimeException("不支持的类型转换");
        }
        return this;
    }
}
