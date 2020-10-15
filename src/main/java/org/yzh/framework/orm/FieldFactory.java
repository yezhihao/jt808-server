package org.yzh.framework.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.fields.*;
import org.yzh.framework.orm.model.DataType;

import java.beans.PropertyDescriptor;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;

/**
 * 消息定义
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class FieldFactory {
    protected static Logger log = LoggerFactory.getLogger(FieldFactory.class.getSimpleName());
    public static boolean EXPLAIN = false;

    public static BasicField create(Field field, Class typeClass, PropertyDescriptor property) {
        return create(field, typeClass, property, null);
    }

    public static BasicField create(Field field, Class typeClass, PropertyDescriptor property, BeanMetadata beanMetadata) {
        DataType dataType = field.type();

        BasicField result;
        if (field.lengthSize() == -1) {
            switch (dataType) {
                case BYTE:
                    result = new FieldInt8(field, property);
                    break;
                case WORD:
                    result = new FieldInt16(field, property);
                    break;
                case DWORD:
                    if (typeClass.isAssignableFrom(Long.class) || typeClass.isAssignableFrom(Long.TYPE))
                        result = new FieldLong32(field, property);
                    else
                        result = new FieldInt32(field, property);
                    break;
                case BCD8421:
                    if (typeClass.isAssignableFrom(LocalDateTime.class))
                        result = new FieldDateTimeBCD(field, property);
                    else
                        result = new FieldStringBCD(field, property);
                    break;
                case BYTES:
                    if (typeClass.isAssignableFrom(String.class))
                        result = new FieldString(field, property);
                    else if (typeClass.isAssignableFrom(ByteBuffer.class))
                        result = new FieldByteBuffer(field, property);
                    else
                        result = new FieldBytes(field, property);
                    break;
                case STRING:
                    result = new FieldString(field, property);
                    break;
                case OBJ:
                    result = new FieldObject(field, property, beanMetadata);
                    break;
                case LIST:
                    result = new FieldList(field, property, beanMetadata);
                    break;
                default:
                    throw new RuntimeException("不支持的类型转换");
            }
            if (EXPLAIN)
                result = new FieldLoggerProxy(result);
        } else {
            switch (dataType) {
                case BYTES:
                    if (typeClass.isAssignableFrom(String.class))
                        result = new DynamicFieldString(field, property);
                    else if (typeClass.isAssignableFrom(ByteBuffer.class))
                        result = new DynamicFieldByteBuffer(field, property);
                    else
                        result = new DynamicFieldBytes(field, property);
                    break;
                case STRING:
                    result = new DynamicFieldString(field, property);
                    break;
                case OBJ:
                    result = new DynamicFieldObject(field, property, beanMetadata);
                    break;
                default:
                    throw new RuntimeException("不支持的类型转换");
            }
            if (EXPLAIN)
                result = new DynamicFieldLoggerProxy((DynamicField) result);
        }
//        if (EXPLAIN)
//            result = (BasicField) LoggerProxy.newInstance(result);
        return result;
    }
}