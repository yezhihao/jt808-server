package org.yzh.framework.mvc;

import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public abstract class Handler {

    public static final int MESSAGE = 0;
    public static final int SESSION = 1;
    public static final int HEADER = 2;

    public final Object targetObject;
    public final Method targetMethod;
    public final int[] parameterTypes;
    public final String desc;

    public Handler(Object actionClass, Method actionMethod, String desc) {
        this.targetObject = actionClass;
        this.targetMethod = actionMethod;
        this.desc = desc;

        Type[] types = actionMethod.getGenericParameterTypes();
        int[] parameterTypes = new int[types.length];
        try {
            for (int i = 0; i < types.length; i++) {
                Type type = types[i];
                Class clazz;
                if (type instanceof ParameterizedTypeImpl)
                    clazz = (Class<?>) ((ParameterizedTypeImpl) type).getActualTypeArguments()[0];
                else
                    clazz = (Class<?>) type;

                if (clazz.isAssignableFrom(Session.class)) {
                    parameterTypes[i] = SESSION;
                } else {
                    Class<?> superclass = clazz.getSuperclass();
                    if (superclass != null) {
                        if (superclass.isAssignableFrom(AbstractHeader.class))
                            parameterTypes[i] = HEADER;
                        else if (superclass.isAssignableFrom(AbstractMessage.class))
                            parameterTypes[i] = MESSAGE;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.parameterTypes = parameterTypes;
    }

    public abstract <T extends AbstractMessage> T invoke(Object... args) throws Exception;

    @Override
    public String toString() {
        return desc;
    }
}