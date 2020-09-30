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

    private static Map<Integer, BeanMetadata> beanMetadataMap = new HashMap(140);

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
        return beanMetadataMap.get(hashCode(clazz.hashCode(), version));
    }

    public static final Class<? extends AbstractHeader> getHeaderClass() {
        return headerClass;
    }

    private static void initMessageClassMap(Class<?> messageClass) throws IntrospectionException {
        Class<?> superclass = messageClass.getSuperclass();
        List<Class<?>> types = new ArrayList<>();
        if (superclass != null) {

            if (superclass.isAssignableFrom(AbstractMessage.class)) {
                types.add(messageClass);

                Message type = messageClass.getAnnotation(Message.class);
                if (type != null) {
                    int[] values = type.value();
                    for (int value : values)
                        messageIdMap.put(value, (Class<? extends AbstractMessage>) messageClass);
                }

            } else if (superclass.isAssignableFrom(AbstractHeader.class)) {
                headerClass = (Class<? extends AbstractHeader>) messageClass;
                types.add(messageClass);
            }
        } else {
            Class<?> enclosingClass = messageClass.getEnclosingClass();
            if (enclosingClass != null) {

                superclass = enclosingClass.getSuperclass();
                if (superclass.isAssignableFrom(AbstractMessage.class)) {
                    types.add(messageClass);
                }
            }
        }
        initMessageFieldMap(types);
        Introspector.flushCaches();
    }

    private static void initMessageFieldMap(List<Class<?>> types) throws IntrospectionException {
        Set<FieldMetadata> fillObjectField = new HashSet<>();

        for (Class<?> clazz : types) {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
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

                    String lengthName = fieldMetadata.field.lengthName();
                    if (!lengthName.equals(""))
                        try {
                            fieldMetadata.lengthMethod = fieldMap.get(lengthName.toLowerCase()).readMethod;
                        } catch (Exception e) {
                            throw new RuntimeException("not found read length method at lengthName is" + lengthName);
                        }
                }

                BeanMetadata value = new BeanMetadata(clazz, fields);
                BeanMetadata old = beanMetadataMap.put(hashCode(clazz.hashCode(), entry.getKey()), value);
                if (old != null)
                    throw new RuntimeException("BeanMetadata重复的Key" + clazz.getName());
            }
        }

        for (FieldMetadata obj : fillObjectField) {
            if (obj.dataType == DataType.OBJ)
                obj.beanMetadata = beanMetadataMap.get(hashCode(obj.classType.hashCode(), obj.version));
            else if (obj.dataType == DataType.LIST)
                obj.beanMetadata = beanMetadataMap.get(hashCode(obj.actualType.hashCode(), obj.version));
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
        Method readMethod = propertyDescriptor.getReadMethod();
        Method writeMethod = propertyDescriptor.getWriteMethod();
        Class<?> classType = propertyDescriptor.getPropertyType();


        int[] versions = field.version();
        for (int ver : versions) {
            Map<String, FieldMetadata> fields = multiVersionFields.get(ver);
            if (fields == null)
                multiVersionFields.put(ver, fields = new TreeMap());
            fields.put(readMethod.getName().substring(3).toLowerCase(), new FieldMetadata(field, ver, classType, readMethod, writeMethod));
        }
    }

    public static int hashCode(int clazz, int version) {
        int result = clazz;
        result = 31 * result + version;
        return result;
    }
}