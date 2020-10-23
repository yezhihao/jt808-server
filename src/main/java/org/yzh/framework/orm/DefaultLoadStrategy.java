package org.yzh.framework.orm;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.orm.annotation.Message;

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

    public DefaultLoadStrategy() {
    }

    public DefaultLoadStrategy(String basePackage) {
        List<Class<?>> types = ClassUtils.getClassList(basePackage);
        for (Class<?> type : types) {
            Message message = type.getAnnotation(Message.class);
            if (message != null) {
                int[] values = message.value();
                for (int typeId : values)
                    loadSchema(typeClassMapping, typeId, type);
            }
        }
        Introspector.flushCaches();
    }

    @Override
    public <T> Schema<T> getSchema(Class<T> typeClass, Integer version) {
        Map<Integer, Schema<?>> schemas = typeClassMapping.get(typeClass.getName());
        if (schemas == null) {
            schemas = loadSchema(typeClassMapping, typeClass);
        }
        if (schemas == null) return null;
        return (Schema<T>) schemas.get(version);
    }

    @Override
    public <T> Map<Integer, Schema<T>> getSchema(Class<T> typeClass) {
        Map<Integer, Schema<?>> schemas = typeClassMapping.get(typeClass.getName());
        if (schemas == null) {
            schemas = loadSchema(typeClassMapping, typeClass);
        }
        if (schemas == null) return null;

        HashMap<Integer, Schema<T>> result = new HashMap<>(schemas.size());
        for (Map.Entry<Integer, Schema<?>> entry : schemas.entrySet()) {
            result.put(entry.getKey(), (Schema<T>) entry.getValue());
        }
        return result;
    }
}