package org.yzh.framework.orm;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Fs;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 消息ID关系映射
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageHelper {

    private static Map<Integer, Class<? extends AbstractMessage>> messageIdMap = new HashMap<>(64);

    private static Map<String, BeanMetadata> beanMetadataMap = new HashMap(140);

    private static Class<? extends AbstractHeader> headerClass = null;

    private static volatile boolean Initial = false;

    public static void initial(String basePackage) {
        if (!Initial) {
            synchronized (MessageHelper.class) {
                if (!Initial) {
                    Initial = true;
                    List<Class<?>> classList = ClassUtils.getClassList(basePackage);
                    try {
                        for (Class<?> clazz : classList)
                            initMessageClassMap(clazz);
                    } catch (IntrospectionException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static Class<? extends AbstractMessage> getBodyClass(Integer messageId) {
        return messageIdMap.get(messageId);
    }

    public static BeanMetadata getBeanMetadata(Class<?> clazz, int version) {
        return beanMetadataMap.get(clazz.getName() + ":" + version);
    }

    public static final Class<? extends AbstractHeader> getHeaderClass() {
        return headerClass;
    }

    private static void initMessageClassMap(Class<?> messageClass) throws IntrospectionException {
        Class<?> superclass = messageClass.getSuperclass();
        if (superclass != null) {

            if (superclass.isAssignableFrom(AbstractMessage.class)) {
                initMessageFieldMap(messageClass);

                Message type = messageClass.getAnnotation(Message.class);
                if (type != null) {
                    int[] values = type.value();
                    for (int value : values)
                        messageIdMap.put(value, (Class<? extends AbstractMessage>) messageClass);
                }

            } else if (superclass.isAssignableFrom(AbstractHeader.class)) {
                headerClass = (Class<? extends AbstractHeader>) messageClass;
                initMessageFieldMap(messageClass);
            }
        } else {
            Class<?> enclosingClass = messageClass.getEnclosingClass();
            if (enclosingClass != null) {

                superclass = enclosingClass.getSuperclass();
                if (superclass.isAssignableFrom(AbstractMessage.class)) {
                    initMessageFieldMap(messageClass);
                }
            }
        }
        Introspector.flushCaches();
    }

    private static void initMessageFieldMap(Class<?> clazz) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        if (beanInfo == null)
            return;

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<Integer, Map<String, FieldMetadata>> multiVersionMap = new TreeMap<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Class<?> propertyType = propertyDescriptor.getPropertyType();

            if (readMethod == null || writeMethod == null)
                continue;

            if (readMethod.isAnnotationPresent(Fs.class)) {

                Field[] fields = readMethod.getDeclaredAnnotation(Fs.class).value();
                for (Field field : fields)
                    setField(multiVersionMap, readMethod, writeMethod, propertyType, field);

            } else if (readMethod.isAnnotationPresent(Field.class)) {
                setField(multiVersionMap, readMethod, writeMethod, propertyType, readMethod.getDeclaredAnnotation(Field.class));
            }
        }

        String className = clazz.getName();
        for (Map.Entry<Integer, Map<String, FieldMetadata>> entry : multiVersionMap.entrySet()) {

            Map<String, FieldMetadata> fieldMetadataMap = entry.getValue();

            FieldMetadata[] fieldMetadataList = fieldMetadataMap.values().toArray(new FieldMetadata[fieldMetadataMap.size()]);
            Arrays.sort(fieldMetadataList, Comparator.comparingInt(p -> p.index));

            for (FieldMetadata fieldMetadata : fieldMetadataList) {
                String lengthName = fieldMetadata.field.lengthName();
                if (!lengthName.equals(""))
                    try {
                        fieldMetadata.lengthMethod = fieldMetadataMap.get(lengthName.toLowerCase()).readMethod;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            FieldMetadata lastField = fieldMetadataList[fieldMetadataList.length - 1];
            int size = lastField.index;
            int length = lastField.length;
            if (length < 0)
                length = 4;
            size += length;

            Integer v = entry.getKey();
            beanMetadataMap.put(className + ":" + v, new BeanMetadata(fieldMetadataList, size));
        }
    }

    private static void setField(Map<Integer, Map<String, FieldMetadata>> multiVersionMap, Method readMethod, Method writeMethod, Class<?> propertyType, Field field) {
        FieldMetadata fieldMetadata = new FieldMetadata(field, propertyType, readMethod, writeMethod);

        int[] versions = field.version();
        for (int ver : versions) {
            Map<String, FieldMetadata> fieldMetadataMap = multiVersionMap.get(ver);
            if (fieldMetadataMap == null)
                multiVersionMap.put(ver, fieldMetadataMap = new TreeMap());
            fieldMetadataMap.put(readMethod.getName().substring(3).toLowerCase(), fieldMetadata);
        }
    }
}