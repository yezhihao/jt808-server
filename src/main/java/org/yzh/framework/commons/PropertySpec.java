package org.yzh.framework.commons;

import org.yzh.framework.orm.annotation.Property;

import java.lang.reflect.Method;

/**
 * 属性定义
 */
public class PropertySpec {

    public final Property property;
    public final Class type;
    public final Method readMethod;
    public final Method writeMethod;

    public PropertySpec(Property property, Class type, Method readMethod, Method writeMethod) {
        this.property = property;
        this.type = type;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
    }
}
