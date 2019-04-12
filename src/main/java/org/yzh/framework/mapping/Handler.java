package org.yzh.framework.mapping;

import org.yzh.framework.message.AbstractMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Handler {

    private Object targetObject;
    private Method targetMethod;
    private String desc;

    public Handler(Object actionClass, Method actionMethod, String desc) {
        this.targetObject = actionClass;
        this.targetMethod = actionMethod;
        this.desc = desc;
    }

    public Handler(Object targetObject, Method actionMethod) {
        this.targetObject = targetObject;
        this.targetMethod = actionMethod;
    }

    public <T extends AbstractMessage> T invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return (T) targetMethod.invoke(targetObject, args);
    }

    public Type[] getTargetParameterTypes() {
        return targetMethod.getGenericParameterTypes();
    }

    @Override
    public String toString() {
        return desc;
    }
}