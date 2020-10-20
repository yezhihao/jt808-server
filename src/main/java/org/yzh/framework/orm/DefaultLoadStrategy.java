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

    private Map<String, Map<Integer, BeanMetadata<?>>> typeClassMapping = new HashMap(140);

    private Map<Object, Class<?>> typeIdMapping = new HashMap<>(64);

    public DefaultLoadStrategy() {
    }

    public DefaultLoadStrategy(String basePackage) {
        List<Class<?>> types = ClassUtils.getClassList(basePackage);
        for (Class<?> type : types) {
            if (loadTypeMapping(this.typeIdMapping, type))
                loadBeanMetadata(this.typeClassMapping, type);
        }
        Introspector.flushCaches();
    }

    @Override
    public BeanMetadata getBeanMetadata(Object typeId, Integer version) {
        Class<?> typeClass = typeIdMapping.get(typeId);
        if (typeClass == null)
            return null;
        return getBeanMetadata(typeClass, version);
    }

    @Override
    public <T> BeanMetadata<T> getBeanMetadata(Class<T> typeClass, Integer version) {
        Map<Integer, BeanMetadata<?>> beanMetadata = typeClassMapping.get(typeClass.getName());
        if (beanMetadata == null) {
            loadBeanMetadata(typeClassMapping, typeClass);
            beanMetadata = typeClassMapping.get(typeClass.getName());
        }
        if (beanMetadata == null) return null;
        return (BeanMetadata<T>) beanMetadata.get(version);
    }

    @Override
    public <T> Map<Integer, BeanMetadata<T>> getBeanMetadata(Class<T> typeClass) {
        Map<Integer, BeanMetadata<?>> beanMetadata = typeClassMapping.get(typeClass.getName());
        if (beanMetadata == null) {
            loadBeanMetadata(typeClassMapping, typeClass);
            beanMetadata = typeClassMapping.get(typeClass.getName());
        }
        if (beanMetadata == null) return null;

        HashMap<Integer, BeanMetadata<T>> result = new HashMap<>(beanMetadata.size());
        for (Map.Entry<Integer, BeanMetadata<?>> entry : beanMetadata.entrySet()) {
            result.put(entry.getKey(), (BeanMetadata<T>) entry.getValue());
        }
        return result;
    }
}