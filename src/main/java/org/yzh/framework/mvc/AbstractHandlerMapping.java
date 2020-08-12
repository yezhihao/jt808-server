package org.yzh.framework.mvc;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.mvc.annotation.AsyncBatch;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.mvc.handler.AsyncBatchHandler;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.mvc.handler.SyncHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
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

                        AsyncBatch asyncBatch = method.getAnnotation(AsyncBatch.class);
                        Handler handler;

                        if (asyncBatch != null) {
                            handler = new AsyncBatchHandler(endpoint, method, desc, asyncBatch.poolSize(), asyncBatch.maxElements(), asyncBatch.maxWait());

                        } else {
                            handler = new SyncHandler(endpoint, method, desc);
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