package org.yzh.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.yzh.framework.annotation.Endpoint;
import org.yzh.framework.annotation.Mapping;
import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.mapping.Handler;
import org.yzh.framework.mapping.HandlerMapper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
public class SpringHandlerMapper implements HandlerMapper, ApplicationContextAware {

    protected String[] packageNames;
    private Map<Integer, Handler> handlerMap = new HashMap(55);

    public SpringHandlerMapper(String... packageNames) {
        this.packageNames = packageNames;
    }

    public Handler getHandler(Integer key) {
        return handlerMap.get(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        for (String packageName : packageNames) {
            List<Class<?>> handlerClassList = ClassUtils.getClassList(packageName, Endpoint.class);

            for (Class<?> handlerClass : handlerClassList) {
                Method[] methods = handlerClass.getDeclaredMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Mapping.class)) {
                            Mapping annotation = method.getAnnotation(Mapping.class);
                            String desc = annotation.desc();
                            int[] types = annotation.types();
                            Handler value = new Handler(applicationContext.getBean(handlerClass), method, desc);
                            for (int type : types) {
                                handlerMap.put(type, value);
                            }
                        }
                    }
                }
            }
        }
    }
}