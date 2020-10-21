package org.yzh.framework.orm;

import org.yzh.framework.commons.ClassUtils;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DefaultLoadStrategy extends LoadStrategy {

    private Map<String, Map<Integer, Schema<?>>> typeClassMapping = new HashMap(140);

    private Map<Object, Class<?>> typeIdMapping = new HashMap<>(64);

    public DefaultLoadStrategy() {
    }

    public DefaultLoadStrategy(String basePackage) {
        List<Class<?>> types = ClassUtils.getClassList(basePackage);
        for (Class<?> type : types) {
            if (loadTypeMapping(this.typeIdMapping, type))
                loadSchema(this.typeClassMapping, type);
        }
        Introspector.flushCaches();
    }

    @Override
    public Schema getSchema(Object typeId, Integer version) {
        Class<?> typeClass = typeIdMapping.get(typeId);
        if (typeClass == null)
            return null;
        return getSchema(typeClass, version);
    }

    @Override
    public <T> Schema<T> getSchema(Class<T> typeClass, Integer version) {
        Map<Integer, Schema<?>> schemas = typeClassMapping.get(typeClass.getName());
        if (schemas == null) {
            loadSchema(typeClassMapping, typeClass);
            schemas = typeClassMapping.get(typeClass.getName());
        }
        if (schemas == null) return null;
        return (Schema<T>) schemas.get(version);
    }

    @Override
    public <T> Map<Integer, Schema<T>> getSchema(Class<T> typeClass) {
        Map<Integer, Schema<?>> schemas = typeClassMapping.get(typeClass.getName());
        if (schemas == null) {
            loadSchema(typeClassMapping, typeClass);
            schemas = typeClassMapping.get(typeClass.getName());
        }
        if (schemas == null) return null;

        HashMap<Integer, Schema<T>> result = new HashMap<>(schemas.size());
        for (Map.Entry<Integer, Schema<?>> entry : schemas.entrySet()) {
            result.put(entry.getKey(), (Schema<T>) entry.getValue());
        }
        return result;
    }
}