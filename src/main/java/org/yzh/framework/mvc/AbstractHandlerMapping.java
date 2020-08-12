package org.yzh.framework.mvc;

import org.yzh.framework.mvc.annotation.AsyncBatch;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.mvc.handler.AsyncBatchHandler;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.mvc.handler.SimpleHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {

    private final Map<Integer, Handler> handlerMap = new HashMap(60);

    protected synchronized void registerHandlers(Object bean) {
        Class<?> beanClass = bean.getClass();
        Method[] methods = beanClass.getDeclaredMethods();
        if (methods == null)
            return;

        for (Method method : methods) {

            Mapping mapping = method.getAnnotation(Mapping.class);
            if (mapping != null) {

                String desc = mapping.desc();
                int[] types = mapping.types();

                AsyncBatch asyncBatch = method.getAnnotation(AsyncBatch.class);
                Handler handler;

                if (asyncBatch != null) {
                    handler = new AsyncBatchHandler(bean, method, desc, asyncBatch.poolSize(), asyncBatch.maxElements(), asyncBatch.maxWait());

                } else {
                    handler = new SimpleHandler(bean, method, desc);
                }

                for (int type : types) {
                    handlerMap.put(type, handler);
                }
            }
        }
    }

    public Handler getHandler(int messageId) {
        return handlerMap.get(messageId);
    }
}