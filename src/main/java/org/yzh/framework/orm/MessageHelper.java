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
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 * @since 2.0
 */
public class MessageHelper {

    private static Map<Integer, Class<? extends AbstractMessage>> messageIdMap = new TreeMap<>();

    private static Map<String, MessageSpec> messageSpecMap = new TreeMap();

    private static Class<? extends AbstractHeader> headerClass = null;

    private static volatile boolean Initial = false;

    public static void initial(String basePackage) {
        if (!Initial) {
            synchronized (MessageHelper.class) {
                if (!Initial) {
                    Initial = true;
                    List<Class<?>> classList = ClassUtils.getClassList(basePackage);
                    for (Class<?> clazz : classList) {
                        initMessageClassMap(clazz);
                    }
                }
            }
        }
    }

    public static Class<? extends AbstractMessage> getBodyClass(Integer messageId) {
        return messageIdMap.get(messageId);
    }

    public static MessageSpec getMessageSpec(Class<?> clazz, int version) {
        return messageSpecMap.get(clazz.getName() + ":" + version);
    }

    public static final Class<? extends AbstractHeader> getHeaderClass() {
        return headerClass;
    }

    private static void initMessageClassMap(Class<?> messageClass) {
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
        Map<Integer, List<FieldSpec>> multiVersionMap = new TreeMap<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (readMethod == null || writeMethod == null)
                continue;

            if (readMethod.isAnnotationPresent(Fs.class)) {

                Field[] fields = readMethod.getDeclaredAnnotation(Fs.class).value();
                for (Field field : fields) {

                    FieldSpec fieldSpec = new FieldSpec(field, propertyDescriptor.getPropertyType(), readMethod, writeMethod);

                    int[] versions = field.version();
                    for (int ver : versions) {
                        List<FieldSpec> fieldSpecs = multiVersionMap.get(ver);
                        if (fieldSpecs == null)
                            multiVersionMap.put(ver, fieldSpecs = new ArrayList(propertyDescriptors.length));
                        fieldSpecs.add(fieldSpec);
                    }
                }

            } else if (readMethod.isAnnotationPresent(Field.class)) {

                Field property = readMethod.getDeclaredAnnotation(Field.class);
                FieldSpec propertySpec = new FieldSpec(property, propertyDescriptor.getPropertyType(), readMethod, writeMethod);

                int[] versions = property.version();
                for (int ver : versions) {
                    List<FieldSpec> propertySpecs = multiVersionMap.get(ver);
                    if (propertySpecs == null)
                        multiVersionMap.put(ver, propertySpecs = new ArrayList(propertyDescriptors.length));
                    propertySpecs.add(propertySpec);
                }
            }
        }

        String className = clazz.getName();
        for (Map.Entry<Integer, List<FieldSpec>> entry : multiVersionMap.entrySet()) {

            List<FieldSpec> propertySpecList = entry.getValue();
            Collections.sort(propertySpecList, Comparator.comparingInt(p -> p.field.index()));
            FieldSpec[] propertySpecs = propertySpecList.toArray(new FieldSpec[propertySpecList.size()]);

            Field lastField = propertySpecs[propertySpecs.length - 1].field;
            int size = lastField.index();
            int length = lastField.length();
            if (length < 0)
                length = lastField.type().length;
            if (length < 0)
                length = 4;
            size += length;

            Integer v = entry.getKey();
            messageSpecMap.put(className + ":" + v, new MessageSpec(propertySpecs, size));
        }
        Introspector.flushCaches();
    }
}