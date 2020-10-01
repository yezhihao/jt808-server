package org.yzh.framework.orm;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Fs;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;

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
                    List<Class<?>> types = new ArrayList<>();
                    try {
                        for (Class<?> clazz : classList) {
                            Class<?> aClass = initMessageClassMap(clazz);
                            if (aClass != null)
                                types.add(aClass);
                        }
                    } catch (IntrospectionException e) {
                        throw new RuntimeException(e);
                    }
                    initMessageFieldMap(types);
                    Introspector.flushCaches();
                }
            }
        }
    }

    public static Class<? extends AbstractMessage> getBodyClass(Integer messageId) {
        return messageIdMap.get(messageId);
    }

    public static BeanMetadata getBeanMetadata(Class<?> clazz, int version) {
        return beanMetadataMap.get(hashCode(clazz, version));
    }

    public static final Class<? extends AbstractHeader> getHeaderClass() {
        return headerClass;
    }

    private static Class<?> initMessageClassMap(Class<?> messageClass) throws IntrospectionException {
        Class<?> superclass = messageClass.getSuperclass();
        Class<?> result = null;
        if (superclass != null) {

            if (superclass.isAssignableFrom(AbstractMessage.class)) {
                result = messageClass;

                Message type = messageClass.getAnnotation(Message.class);
                if (type != null) {
                    int[] values = type.value();
                    for (int value : values)
                        messageIdMap.put(value, (Class<? extends AbstractMessage>) messageClass);
                }

            } else if (superclass.isAssignableFrom(AbstractHeader.class)) {
                headerClass = (Class<? extends AbstractHeader>) messageClass;
                result = messageClass;
            }
        } else {
            Class<?> enclosingClass = messageClass.getEnclosingClass();
            if (enclosingClass != null) {

                superclass = enclosingClass.getSuperclass();
                if (superclass.isAssignableFrom(AbstractMessage.class)) {
                    result = messageClass;
                }
            }
        }
        return result;
    }

    private static void initMessageFieldMap(List<Class<?>> types) {
        Set<FieldMetadata> fillObjectField = new HashSet<>();

        for (Class<?> clazz : types) {
            BeanInfo beanInfo;
            try {
                beanInfo = Introspector.getBeanInfo(clazz);
            } catch (IntrospectionException e) {
                throw new RuntimeException(e);
            }
            if (beanInfo == null)
                continue;

            Map<Integer, Map<String, FieldMetadata>> multiVersionFields = getMultiVersionFields(beanInfo);

            for (Map.Entry<Integer, Map<String, FieldMetadata>> entry : multiVersionFields.entrySet()) {

                Map<String, FieldMetadata> fieldMap = entry.getValue();

                FieldMetadata[] fields = fieldMap.values().toArray(new FieldMetadata[fieldMap.size()]);
                Arrays.sort(fields, Comparator.comparingInt(p -> p.index));

                for (FieldMetadata fieldMetadata : fields) {
                    if (fieldMetadata.dataType == DataType.OBJ || fieldMetadata.dataType == DataType.LIST)
                        fillObjectField.add(fieldMetadata);

                    String lengthName = fieldMetadata.getLengthName();
                    if (lengthName != null)
                        try {
                            fieldMetadata.lengthMethod = fieldMap.get(lengthName.toLowerCase()).readMethod;
                        } catch (Exception e) {
                            throw new RuntimeException("not found read length method at lengthName is" + lengthName);
                        }
                }

                BeanMetadata value = new BeanMetadata(clazz, fields);
                BeanMetadata old = beanMetadataMap.put(hashCode(clazz, entry.getKey()), value);
                if (old != null)
                    throw new RuntimeException("重复的Key" + clazz.getName());
            }
        }

        for (FieldMetadata obj : fillObjectField) {
            if (obj.dataType == DataType.OBJ || obj.dataType == DataType.LIST) {
                BeanMetadata beanMetadata = beanMetadataMap.get(hashCode(obj.typeClass, obj.version));
                if (beanMetadata != null)
                    obj.setFieldMetadataList(beanMetadata.fieldMetadataList);
            }
        }

    }

    private static Map<Integer, Map<String, FieldMetadata>> getMultiVersionFields(BeanInfo beanInfo) {
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<Integer, Map<String, FieldMetadata>> result = new TreeMap<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {

                if (readMethod.isAnnotationPresent(Fs.class)) {

                    Field[] fields = readMethod.getDeclaredAnnotation(Fs.class).value();
                    for (Field field : fields)
                        putField(result, propertyDescriptor, field);

                } else if (readMethod.isAnnotationPresent(Field.class)) {
                    putField(result, propertyDescriptor, readMethod.getDeclaredAnnotation(Field.class));
                }
            }
        }
        return result;
    }

    private static void putField(Map<Integer, Map<String, FieldMetadata>> multiVersionFields, PropertyDescriptor propertyDescriptor, Field field) {
        if (field.type() == DataType.OBJ || field.type() == DataType.LIST)
            return;
        Method readMethod = propertyDescriptor.getReadMethod();
        Method writeMethod = propertyDescriptor.getWriteMethod();
        Class<?> classType = propertyDescriptor.getPropertyType();


        int[] versions = field.version();
        for (int ver : versions) {
            Map<String, FieldMetadata> fields = multiVersionFields.get(ver);
            if (fields == null)
                multiVersionFields.put(ver, fields = new TreeMap());
            fields.put(readMethod.getName().substring(3).toLowerCase(), FieldMetadata.newInstance(classType, readMethod, writeMethod, ver, field));
        }
    }

    public static String hashCode(Class clazz, int version) {
        return (clazz.getName() + version);
    }
}