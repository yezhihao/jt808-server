package org.yzh.framework.orm;

import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.core.ClassHelper;
import org.yzh.framework.orm.annotation.Header;
import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Ps;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.framework.orm.model.AbstractMessage;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 消息ID关系映射
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 * @since 2.0
 */
public class MessageHelper {

    private static Map<Integer, Class<? extends AbstractBody>> messageIdMap = new TreeMap<>();

    private static Map<String, PropertySpec[]> messagePropertySpecMap = new TreeMap();

    private static Class<? extends AbstractMessage> headerClass = null;

    public static Class<? extends AbstractBody> getClass(Integer messageId) {
        return messageIdMap.get(messageId);
    }

    public static PropertySpec[] getPropertySpec(Class<?> clazz, int version) {
        return messagePropertySpecMap.get(clazz.getName() + ":" + version);
    }

    public static final Class<? extends AbstractMessage> getHeaderClass() {
        return headerClass;
    }

    static {
        List<Class<?>> classList = ClassHelper.getClassList();
        for (Class<?> clazz : classList) {
            initMessageClassMap(clazz);
        }
    }

    private static void initMessageClassMap(Class<?> messageClass) {
        if (messageClass.isAnnotationPresent(Header.class)) {
            headerClass = (Class<? extends AbstractMessage>) messageClass;
            initMessageFieldMap(messageClass);
        }

        Type type = messageClass.getAnnotation(Type.class);
        if (type != null) {
            initMessageFieldMap(messageClass);
            int[] values = type.value();
            for (int value : values) {
                messageIdMap.put(value, (Class<? extends AbstractBody>) messageClass);
            }
        }
    }

    private static void initMessageFieldMap(Class<?> clazz) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (beanInfo == null)
            return;

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<Integer, List<PropertySpec>> multiVersionMap = new TreeMap<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod == null)
                continue;
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (readMethod.isAnnotationPresent(Ps.class)) {

                Property[] properties = readMethod.getDeclaredAnnotation(Ps.class).value();
                for (Property property : properties) {

                    PropertySpec propertySpec = new PropertySpec(property, propertyDescriptor.getPropertyType(), readMethod, writeMethod);

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
                PropertySpec propertySpec = new PropertySpec(property, propertyDescriptor.getPropertyType(), readMethod, writeMethod);

                int[] versions = property.version();
                for (int ver : versions) {
                    List<PropertySpec> propertySpecs = multiVersionMap.get(ver);
                    if (propertySpecs == null)
                        multiVersionMap.put(ver, propertySpecs = new ArrayList(propertyDescriptors.length));
                    propertySpecs.add(propertySpec);
                }
            }
        }

        String className = clazz.getName();
        for (Map.Entry<Integer, List<PropertySpec>> entry : multiVersionMap.entrySet()) {

            List<PropertySpec> propertySpecList = entry.getValue();
            Collections.sort(propertySpecList, Comparator.comparingInt(p -> p.property.index()));
            PropertySpec[] propertySpecs = propertySpecList.toArray(new PropertySpec[propertySpecList.size()]);

            Integer v = entry.getKey();
            messagePropertySpecMap.put(className + ":" + v, propertySpecs);
        }
        Introspector.flushCaches();
    }
}