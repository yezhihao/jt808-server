package org.yzh.framework.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.mvc.annotation.AsyncBatch;
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

                    Mapping mapping = method.getAnnotation(Mapping.class);
                    if (mapping != null) {

                        String desc = mapping.desc();
                        int[] types = mapping.types();

                        Handler handler;
                        AsyncBatch asyncBatch = method.getAnnotation(AsyncBatch.class);

                        if (asyncBatch == null) {
                            handler = new SimpleHandler(endpoint, method, desc);
                        } else {
                            Class<?> parameterType = method.getParameterTypes()[0];
                            if (!parameterType.isAssignableFrom(List.class))
                                throw new TypeMismatchException("@AsyncBatch方法的参数不是List类型:" + method);
                            handler = new AsyncBatchHandler(endpoint, method, desc, asyncBatch.capacity(), asyncBatch.maxElements(), asyncBatch.maxWait());

                        }

                        for (int type : types) {
                            handlerMap.put(type, handler);
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