package org.yzh.framework.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.annotation.Convert;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.field.BasicField;
import org.yzh.framework.orm.field.DynamicLengthField;
import org.yzh.framework.orm.field.FixedField;
import org.yzh.framework.orm.field.FixedLengthField;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.schema.*;

import java.beans.PropertyDescriptor;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;

/**
 * FieldFactory
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
                fieldSchema = IntSchema.Int8.INSTANCE;
                break;
            case WORD:
                fieldSchema = IntSchema.Int16.INSTANCE;
                break;
            case DWORD:
                if (Integer.TYPE.isAssignableFrom(typeClass) || Integer.class.isAssignableFrom(typeClass))
                    fieldSchema = IntSchema.Int32.INSTANCE;
                else
                    fieldSchema = LongSchema.Long32.INSTANCE;
                break;
            case QWORD:
                fieldSchema = LongSchema.Long64.INSTANCE;
                break;
            case BCD8421:
                if (LocalDateTime.class.isAssignableFrom(typeClass))
                    fieldSchema = DateTimeSchema.BCD.INSTANCE;
                else
                    fieldSchema = StringSchema.BCD.INSTANCE;
                break;
            case BYTES:
                if (String.class.isAssignableFrom(typeClass))
                    fieldSchema = StringSchema.Chars.getInstance(field.pad(), field.charset());
                else if (ByteBuffer.class.isAssignableFrom(typeClass))
                    fieldSchema = ByteBufferSchema.INSTANCE;
                else
                    fieldSchema = ByteArraySchema.INSTANCE;
                break;
            case STRING:
                fieldSchema = StringSchema.Chars.getInstance(field.pad(), field.charset());
                break;
            case OBJ:
                if (schema != null) {
                    fieldSchema = ObjectSchema.getInstance(schema);
                } else {
                    Convert convert = property.getReadMethod().getAnnotation(Convert.class);
                    fieldSchema = ConvertSchema.getInstance(convert.converter());
                }
                break;
            case LIST:
                fieldSchema = CollectionSchema.getInstance(schema);
                break;
            case MAP:
                Convert convert = property.getReadMethod().getAnnotation(Convert.class);
                fieldSchema = ConvertSchema.getInstance(convert.converter());
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