package org.yzh.framework.orm;

import org.yzh.framework.orm.annotation.Field;

import java.lang.reflect.Method;

/**
 * 消息定义
 */
public class FieldSpec {

    public final Field field;
    public final Class type;
    public final Method readMethod;
    public final Method writeMethod;

    public FieldSpec(Field field, Class type, Method readMethod, Method writeMethod) {
        this.field = field;
        this.type = type;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
    }
}
