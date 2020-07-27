package org.yzh.framework.orm;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;

import static org.yzh.framework.orm.model.DataType.*;

/**
 * 消息定义
 */
public class FieldMetadata {

    public final DataType dataType;
    public final Method readMethod;
    public final Method writeMethod;

    public final int index;
    public final String desc;
    public final Class classType;
    public final Charset charset;
    public final byte pad;
    public final int length;
    public final boolean isLong;
    public final boolean isString;
    public final Class<?> actualType;
    public final Field field;


    protected Method lengthMethod;

    public FieldMetadata(Field field, Class classType, Method readMethod, Method writeMethod) {
        this.field = field;
        this.classType = classType;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;

        this.index = field.index();
        this.dataType = field.type();
        this.charset = Charset.forName(field.charset());
        this.pad = field.pad();
        this.desc = field.desc();
        if (field.length() > -1)
            this.length = field.length();
        else
            this.length = field.type().length;
        if (dataType == DWORD)
            this.isLong = classType.isAssignableFrom(Long.class);
        else
            this.isLong = false;
        if (dataType == BYTES)
            this.isString = classType.isAssignableFrom(String.class);
        else
            this.isString = false;
        if (dataType == LIST)
            this.actualType = (Class<?>) ((ParameterizedType) readMethod.getGenericReturnType()).getActualTypeArguments()[0];
        else
            this.actualType = null;
    }

    public Integer getLength(Object obj) {
        if (lengthMethod == null)
            return length;
        try {
            return (Integer) lengthMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "desc='" + desc + '\'' +
                ", classType=" + classType +
                ", readMethod=" + readMethod +
                ", writeMethod=" + writeMethod +
                ", dataType=" + dataType +
                ", charset=" + charset +
                ", pad=" + pad +
                ", length=" + length +
                ", lengthMethod=" + lengthMethod +
                '}';
    }
}
