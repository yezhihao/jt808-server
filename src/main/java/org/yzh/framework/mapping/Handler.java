package org.yzh.framework.mapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return targetMethod.invoke(targetObject, args);
    }

    public Class<?>[] getTargetParameterTypes() {
        return targetMethod.getParameterTypes();
    }

    @Override
    public String toString() {
        return desc;
    }
}