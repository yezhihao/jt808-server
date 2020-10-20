package org.yzh.client.netty;

import org.yzh.framework.mvc.model.Message;

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

    public <T extends Message> T invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return (T) targetMethod.invoke(targetObject, args);
    }

    @Override
    public String toString() {
        return desc;
    }
}