package org.yzh.framework.commons;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Ps;
import org.yzh.framework.commons.bean.BeanUtils;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 获取消息模型的属性定义
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public abstract class PropertyUtils {

    private static final Cache<String, PropertySpec[]> propertyCache = Caffeine.newBuilder()
            .maximumSize(128)
            .build();

    public static PropertySpec[] getPropertySpecs(Class<?> clazz, int version) {
        PropertySpec[] propertySpecs = getPropertySpecsInternal(clazz, version);
        if (propertySpecs == null)
            propertySpecs = getPropertySpecsInternal(clazz, 0);
        return propertySpecs;
    }

    /**
     * 获取消息模型的属性定义
     *
     * @param clazz   模型Class
     * @param version 版本号
     * @return
     */
    private static PropertySpec[] getPropertySpecsInternal(Class<?> clazz, int version) {
        String className = clazz.getName();
        String key = className + ":" + version;

        return propertyCache.get(key, k -> {
            BeanInfo beanInfo = BeanUtils.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Map<Integer, List<PropertySpec>> multiVersionMap = new TreeMap<>();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod.isAnnotationPresent(Ps.class)) {

                    Property[] properties = readMethod.getDeclaredAnnotation(Ps.class).value();
                    for (Property property : properties) {

                        PropertySpec propertySpec = new PropertySpec(property, propertyDescriptor.getPropertyType(), readMethod, propertyDescriptor.getWriteMethod());

                        int[] versions = property.version();
                        for (int ver : versions) {
                            List<PropertySpec> propertySpecs = multiVersionMap.get(ver);
                            if (propertySpecs == null)
                                multiVersionMap.put(ver, propertySpecs = new ArrayList(propertyDescriptors.length));
                            propertySpecs.add(propertySpec);
                        }
                    }

                } else if (readMethod.isAnnotationPresent(Property.class)) {

                    Property property = readMethod.getDeclaredAnnotation(Property.class);
                    PropertySpec propertySpec = new PropertySpec(property, propertyDescriptor.getPropertyType(), readMethod, propertyDescriptor.getWriteMethod());

                    int[] versions = property.version();
                    for (int ver : versions) {
                        List<PropertySpec> propertySpecs = multiVersionMap.get(ver);
                        if (propertySpecs == null)
                            multiVersionMap.put(ver, propertySpecs = new ArrayList(propertyDescriptors.length));
                        propertySpecs.add(propertySpec);
                    }
                }
            }

            PropertySpec[] result = null;
            for (Map.Entry<Integer, List<PropertySpec>> entry : multiVersionMap.entrySet()) {

                List<PropertySpec> propertySpecList = entry.getValue();
                Collections.sort(propertySpecList, Comparator.comparingInt(p -> p.property.index()));
                PropertySpec[] propertySpecs = propertySpecList.toArray(new PropertySpec[propertySpecList.size()]);

                Integer v = entry.getKey();
                if (v == version) {
                    result = propertySpecs;
                } else {
                    propertyCache.put(className + ":" + v, propertySpecs);
                }
            }
            return result;
        });
    }

    public static int getLength(Object obj, Property prop) {
        int length = prop.length();
        if (length == -1)
            if ("".equals(prop.lengthName()))
                length = prop.type().length;
            else
                length = (int) BeanUtils.getValue(obj, prop.lengthName(), 0);
        return length;
    }

    public static int getIndex(Object obj, Property prop) {
        int index = prop.index();
        for (String name : prop.indexOffsetName())
            if (!"".equals(name))
                index += (int) BeanUtils.getValue(obj, name, 0);
        return index;
    }
}