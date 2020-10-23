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

    public static BasicField create(Field field, PropertyDescriptor property) {
        return create(field, property, null);
    }

    public static BasicField create(Field field, PropertyDescriptor property, Schema schema) {
        DataType dataType = field.type();
        Class<?> typeClass = property.getPropertyType();

        Schema fieldSchema;
        switch (dataType) {
            case BYTE:
                fieldSchema = FieldInt8.INSTANCE;
                break;
            case WORD:
                fieldSchema = FieldInt16.INSTANCE;
                break;
            case DWORD:
                if (Long.class.isAssignableFrom(typeClass) || Long.TYPE.isAssignableFrom(typeClass))
                    fieldSchema = FieldLong32.INSTANCE;
                else
                    fieldSchema = FieldInt32.INSTANCE;
                break;
            case BCD8421:
                if (LocalDateTime.class.isAssignableFrom(typeClass))
                    fieldSchema = FieldDateTimeBCD.INSTANCE;
                else
                    fieldSchema = FieldStringBCD.INSTANCE;
                break;
            case BYTES:
                if (String.class.isAssignableFrom(typeClass))
                    fieldSchema = FieldString.getInstance(field.pad(), field.charset());
                else if (ByteBuffer.class.isAssignableFrom(typeClass))
                    fieldSchema = FieldByteBuffer.INSTANCE;
                else
                    fieldSchema = FieldBytes.INSTANCE;
                break;
            case STRING:
                fieldSchema = FieldString.getInstance(field.pad(), field.charset());
                break;
            case OBJ:
                fieldSchema = FieldObject.getInstance(schema);
                break;
            case LIST:
                fieldSchema = FieldList.getInstance(schema);
                break;
            case MAP:
                fieldSchema = new FieldMap(property);
                break;
            default:
                throw new RuntimeException("不支持的类型转换");
        }


        BasicField result;
        if (EXPLAIN) {
            if (field.lengthSize() > 0) {
                result = new DynamicLengthField.Logger(field, property, fieldSchema);
            } else if (field.length() > 0) {
                result = new FixedLengthField.Logger(field, property, fieldSchema);
            } else {
                result = new FixedField.Logger(field, property, fieldSchema);
            }
        } else {
            if (field.lengthSize() > 0) {
                result = new DynamicLengthField(field, property, fieldSchema);
            } else if (field.length() > 0) {
                result = new FixedLengthField(field, property, fieldSchema);
            } else {
                result = new FixedField(field, property, fieldSchema);
            }
        }
        return result;
    }
}