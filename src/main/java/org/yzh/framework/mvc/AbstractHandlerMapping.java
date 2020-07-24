package org.yzh.framework.mvc;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {

    private static Map<Integer, Handler> handlerMap = new HashMap(55);

    private String endpointPackage;

    public AbstractHandlerMapping(String endpointPackage) {
        this.endpointPackage = endpointPackage;
    }

    public final void initial() {
        List<Class<?>> handlerClassList = ClassUtils.getClassList(this.endpointPackage, Endpoint.class);

        for (Class<?> handlerClass : handlerClassList) {

            Method[] methods = handlerClass.getDeclaredMethods();
            if (methods != null) {

                Object endpoint = getEndpoint(handlerClass);

                for (Method method : methods) {
                    if (method.isAnnotationPresent(Mapping.class)) {

                        Mapping annotation = method.getAnnotation(Mapping.class);
                        String desc = annotation.desc();
                        int[] types = annotation.types();
                        Handler value = new Handler(endpoint, method, desc);
                        for (int type : types) {
                            handlerMap.put(type, value);
                        }
                    }
                }
            }
        }
    }

    public abstract Object getEndpoint(Class<?> clazz);

    public Handler getHandler(int messageId) {
        return handlerMap.get(messageId);
    }
}