package org.yzh.framework.netty.client;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerMapping {

    private Map<Integer, Handler> handlerMap = new HashMap(55);

    public HandlerMapping(String... packageNames) {
        for (String packageName : packageNames) {
            initial(packageName);
        }
    }

    private void initial(String packageName) {
        List<Class<?>> handlerClassList = ClassUtils.getClassList(packageName, Endpoint.class);

        for (Class<?> handlerClass : handlerClassList) {
            Method[] methods = handlerClass.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Mapping.class)) {
                        Mapping annotation = method.getAnnotation(Mapping.class);
                        String desc = annotation.desc();
                        int[] types = annotation.types();
                        Handler value = new Handler(newInstance(handlerClass), method, desc);
                        for (int type : types) {
                            handlerMap.put(type, value);
                        }
                    }
                }
            }
        }
    }

    private Object newInstance(Class<?> handlerClass) {
        try {
            return handlerClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Handler getHandler(Integer key) {
        return handlerMap.get(key);
    }

}