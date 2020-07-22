package org.yzh.framework.mvc;

import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.core.ClassHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
public class HandlerMapping {

    private static Map<Integer, Handler> handlerMap = new HashMap(55);

    static {
        List<Class<?>> handlerClassList = ClassHelper.getClassListByAnnotation(Endpoint.class);

        for (Class<?> handlerClass : handlerClassList) {

            Method[] methods = handlerClass.getDeclaredMethods();
            if (methods != null) {

                for (Method method : methods) {
                    if (method.isAnnotationPresent(Mapping.class)) {

                        Mapping annotation = method.getAnnotation(Mapping.class);
                        String desc = annotation.desc();
                        int[] types = annotation.types();
                        Handler value = new Handler(BeanUtils.newInstance(handlerClass), method, desc);
                        for (int type : types) {
                            handlerMap.put(type, value);
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(Integer key) {
        return handlerMap.get(key);
    }
}