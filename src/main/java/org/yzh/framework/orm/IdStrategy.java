package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.fields.BasicField;
import org.yzh.framework.orm.model.DataType;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Schema加载策略
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class IdStrategy {

    protected Map<Object, Schema> typeIdMapping = new HashMap<>(64);

    public Object readFrom(Object typeId, ByteBuf input) {
        Schema schema = typeIdMapping.get(typeId);
        return schema.readFrom(input);
    }

    public void writeTo(Object typeId, ByteBuf output, Object element) {
        Schema schema = typeIdMapping.get(typeId);
        schema.writeTo(output, element);
    }

    public Schema getSchema(Object typeId) {
        Schema schema = typeIdMapping.get(typeId);
        return schema;
    }

    public abstract <T> Schema<T> getSchema(Class<T> typeClass);

    protected <T> Schema<T> loadSchema(Map<Object, Schema> root, Object typeId, Class<T> typeClass) {
        Schema<T> schema = typeIdMapping.get(typeId);
        if (schema == null) {
            schema = loadSchema(root, typeClass);
            typeIdMapping.put(typeId, schema);
        }
        return schema;
    }

    protected static <T> Schema<T> loadSchema(Map<Object, Schema> root, Class<T> typeClass) {
        Schema schema = root.get(typeClass.getName());
        //不支持循环引用
        if (schema != null)
            return (Schema<T>) schema;

        List<PropertyDescriptor> properties = findFieldProperties(typeClass);
        if (properties.isEmpty())
            return null;

        List<BasicField> fieldList = findFields(root, properties);
        BasicField[] fields = fieldList.toArray(new BasicField[fieldList.size()]);
        Arrays.sort(fields);

        schema = new RuntimeSchema(typeClass, 0, fields);
        root.put(typeClass.getName(), schema);
        return (Schema<T>) schema;
    }

    protected static List<PropertyDescriptor> findFieldProperties(Class typeClass) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(typeClass);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        List<PropertyDescriptor> result = new ArrayList<>(properties.length);

        for (PropertyDescriptor property : properties) {
            Method readMethod = property.getReadMethod();

            if (readMethod != null) {
                if (readMethod.isAnnotationPresent(Field.class)) {
                    result.add(property);
                }
            }
        }
        return result;
    }

    protected static List<BasicField> findFields(Map<Object, Schema> root, List<PropertyDescriptor> properties) {
        List<BasicField> fields = new ArrayList<>(properties.size());

        for (PropertyDescriptor property : properties) {
            Method readMethod = property.getReadMethod();

            Field field = readMethod.getDeclaredAnnotation(Field.class);
            if (field != null) {
                fillField(root, fields, property, field);
            }
        }
        return fields;
    }

    protected static void fillField(Map<Object, Schema> root, List<BasicField> fields, PropertyDescriptor propertyDescriptor, Field field) {
        Class typeClass = propertyDescriptor.getPropertyType();
        Method readMethod = propertyDescriptor.getReadMethod();

        BasicField value;

        if (field.type() == DataType.OBJ || field.type() == DataType.LIST) {
            if (Collection.class.isAssignableFrom(typeClass))
                typeClass = (Class) ((ParameterizedType) readMethod.getGenericReturnType()).getActualTypeArguments()[0];
            loadSchema(root, typeClass);
            Schema schema = root.get(typeClass.getName());
            value = FieldFactory.create(field, propertyDescriptor, schema);
            fields.add(value);
        } else {
            value = FieldFactory.create(field, propertyDescriptor);
            fields.add(value);
        }
    }
}