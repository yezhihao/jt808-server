package org.yzh.framework.orm;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Fs;
import org.yzh.framework.orm.model.DataType;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * BeanMetadata加载策略
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class LoadStrategy {

    public abstract Class<?> getHeaderClass();

    public abstract BeanMetadata getBeanMetadata(Object typeId, int version);

    public abstract <T> BeanMetadata<T> getBeanMetadata(Class<T> typeClass, int version);

    protected static void initClass(Map<String, Map<Integer, BeanMetadata>> root, Class<?> typeClass) {
        Map<Integer, BeanMetadata> beanMetadataMap = root.get(typeClass.getName());
        //不支持循环引用
        if (beanMetadataMap != null)
            return;
        root.put(typeClass.getName(), beanMetadataMap = new HashMap(4));
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(typeClass);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, List<FieldMetadata>> multiVersionFields = findMultiVersionFields(root, beanInfo);
        for (Map.Entry<Integer, List<FieldMetadata>> entry : multiVersionFields.entrySet()) {

            Integer version = entry.getKey();
            List<FieldMetadata> fieldList = entry.getValue();

            FieldMetadata[] fields = fieldList.toArray(new FieldMetadata[fieldList.size()]);
            Arrays.sort(fields);

            BeanMetadata beanMetadata = new BeanMetadata(typeClass, version, fields);
            beanMetadataMap.put(version, beanMetadata);
        }
    }

    protected static Map<Integer, List<FieldMetadata>> findMultiVersionFields(Map<String, Map<Integer, BeanMetadata>> root, BeanInfo beanInfo) {
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

    protected static void fillField(Map<String, Map<Integer, BeanMetadata>> root, PropertyDescriptor[] properties, Map<Integer, List<FieldMetadata>> multiVersionFields, PropertyDescriptor propertyDescriptor, Field field) {
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
                value = FieldMetadata.newInstance(field, typeClass, readMethod, writeMethod, lengthMethod, beanMetadata);
                multiVersionFields.get(ver).add(value);
            }
        } else {
            value = FieldMetadata.newInstance(field, typeClass, readMethod, writeMethod, lengthMethod);
            for (int ver : versions) {
                multiVersionFields.get(ver).add(value);
            }
        }
    }

    protected static Method findLengthMethod(PropertyDescriptor[] properties, String lengthName) {
        if ("".equals(lengthName))
            return null;
        for (PropertyDescriptor property : properties)
            if (lengthName.equals(property.getName()))
                return property.getReadMethod();
        throw new RuntimeException("not found method " + lengthName);
    }
}