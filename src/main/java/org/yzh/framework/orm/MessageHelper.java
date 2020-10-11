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
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 消息ID关系映射
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageHelper {

    private static Map<Integer, Class<? extends AbstractMessage>> messageIdMap = new HashMap<>(64);

    private static Map<String, Map<Integer, BeanMetadata>> beanMetadataMap = new HashMap(140);

    private static Class<? extends AbstractHeader> headerClass = null;

    private static volatile boolean Initial = false;

    public static void initial(String basePackage) {
        if (!Initial) {
            synchronized (MessageHelper.class) {
                if (!Initial) {
                    Initial = true;
                    List<Class<?>> classList = ClassUtils.getClassList(basePackage);
                    for (Class<?> clazz : classList) {
                        Class<?> aClass = getMessageClass(clazz);
                        if (aClass != null)
                            initClass(beanMetadataMap, aClass);
                    }
                    Introspector.flushCaches();
                }
            }
        }
    }

    public static BeanMetadata getBeanMetadata(int messageId, int version) {
        Class<? extends AbstractMessage> typeClass = messageIdMap.get(messageId);
        if (typeClass == null)
            return null;
        return getBeanMetadata(typeClass, version);
    }

    public static BeanMetadata getBeanMetadata(Class<?> clazz, int version) {
        Map<Integer, BeanMetadata> beanMetadata = beanMetadataMap.get(clazz.getName());
        if (beanMetadata != null)
            return beanMetadata.get(version);
        return null;
    }

    public static final Class<? extends AbstractHeader> getHeaderClass() {
        return headerClass;
    }

    private static Class<?> getMessageClass(Class<?> messageClass) {
        Class<?> superclass = messageClass.getSuperclass();
        Class<?> result = null;
        if (superclass != null) {

            if (AbstractMessage.class.isAssignableFrom(superclass)) {
                result = messageClass;

                Message type = messageClass.getAnnotation(Message.class);
                if (type != null) {
                    int[] values = type.value();
                    for (int value : values)
                        messageIdMap.put(value, (Class<? extends AbstractMessage>) messageClass);
                }

            } else if (AbstractHeader.class.isAssignableFrom(superclass)) {
                headerClass = (Class<? extends AbstractHeader>) messageClass;
                result = messageClass;
            }
        } else {
            Class<?> enclosingClass = messageClass.getEnclosingClass();
            if (enclosingClass != null) {

                superclass = enclosingClass.getSuperclass();
                if (AbstractMessage.class.isAssignableFrom(superclass)) {
                    result = messageClass;
                }
            }
        }
        return result;
    }

    private static void initClass(Map<String, Map<Integer, BeanMetadata>> root, Class<?> clazz) {
        Map<Integer, BeanMetadata> beanMetadataMap = root.get(clazz.getName());
        if (beanMetadataMap != null)
            return;
        root.put(clazz.getName(), beanMetadataMap = new HashMap(4));
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, List<FieldMetadata>> multiVersionFields = findMultiVersionFields(root, beanInfo);
        for (Map.Entry<Integer, List<FieldMetadata>> entry : multiVersionFields.entrySet()) {

            Integer version = entry.getKey();
            List<FieldMetadata> fieldList = entry.getValue();

            FieldMetadata[] fields = fieldList.toArray(new FieldMetadata[fieldList.size()]);
            Arrays.sort(fields);

            BeanMetadata beanMetadata = new BeanMetadata(clazz, version, fields);
            beanMetadataMap.put(version, beanMetadata);
        }
    }

    private static Map<Integer, List<FieldMetadata>> findMultiVersionFields(Map<String, Map<Integer, BeanMetadata>> root, BeanInfo beanInfo) {
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        Map<Integer, List<FieldMetadata>> multiVersionFields = new TreeMap<Integer, List<FieldMetadata>>() {
            @Override
            public List<FieldMetadata> get(Object key) {
                List result = super.get(key);
                if (result == null)
                    super.put((Integer) key, result = new ArrayList<>(properties.length));
                return result;
            }
        };

        for (PropertyDescriptor property : properties) {
            Method readMethod = property.getReadMethod();

            if (readMethod != null) {
                if (readMethod.isAnnotationPresent(Fs.class)) {

                    Field[] fields = readMethod.getDeclaredAnnotation(Fs.class).value();
                    for (Field field : fields)
                        fillField(root, properties, multiVersionFields, property, field);

                } else if (readMethod.isAnnotationPresent(Field.class)) {
                    fillField(root, properties, multiVersionFields, property, readMethod.getDeclaredAnnotation(Field.class));
                }
            }
        }
        return multiVersionFields;
    }

    private static void fillField(Map<String, Map<Integer, BeanMetadata>> root, PropertyDescriptor[] properties, Map<Integer, List<FieldMetadata>> multiVersionFields, PropertyDescriptor propertyDescriptor, Field field) {
        Class<?> typeClass = propertyDescriptor.getPropertyType();
        Method readMethod = propertyDescriptor.getReadMethod();
        Method writeMethod = propertyDescriptor.getWriteMethod();
        Method lengthMethod = findLengthMethod(properties, field.lengthName());

        FieldMetadata value;
        int[] versions = field.version();

        if (field.type() == DataType.OBJ || field.type() == DataType.LIST) {
            if (Collection.class.isAssignableFrom(typeClass))
                typeClass = (Class<?>) ((ParameterizedType) readMethod.getGenericReturnType()).getActualTypeArguments()[0];
            initClass(root, typeClass);
            for (int ver : versions) {
                BeanMetadata beanMetadata = root.get(typeClass.getName()).get(ver);
                value = FieldMetadata.newInstance(typeClass, readMethod, writeMethod, lengthMethod, field, beanMetadata);
                multiVersionFields.get(ver).add(value);
            }
        } else {
            value = FieldMetadata.newInstance(typeClass, readMethod, writeMethod, lengthMethod, field);
            for (int ver : versions) {
                multiVersionFields.get(ver).add(value);
            }
        }
    }

    private static Method findLengthMethod(PropertyDescriptor[] properties, String lengthName) {
        if ("".equals(lengthName))
            return null;
        for (PropertyDescriptor property : properties)
            if (lengthName.equals(property.getName()))
                return property.getReadMethod();
        throw new RuntimeException("not found method " + lengthName);
    }
}