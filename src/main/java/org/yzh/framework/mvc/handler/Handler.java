package org.yzh.framework.mvc.handler;

import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@SuppressWarnings("unchecked")
public abstract class Handler {

    public static final int MESSAGE = 0;
    public static final int SESSION = 1;
    public static final int HEADER = 2;

    public final Object targetObject;
    public final Method targetMethod;
    public final int[] parameterTypes;
    public final boolean hasReturn;
    public final String desc;

    public Handler(Object actionClass, Method actionMethod, String desc) {
        this.targetObject = actionClass;
        this.targetMethod = actionMethod;
        this.hasReturn = !actionMethod.getReturnType().isAssignableFrom(Void.TYPE);
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

                if (AbstractMessage.class.isAssignableFrom(clazz))
                    parameterTypes[i] = MESSAGE;
                else if (AbstractHeader.class.isAssignableFrom(clazz))
                    parameterTypes[i] = HEADER;
                else if (Session.class.isAssignableFrom(clazz))
                    parameterTypes[i] = SESSION;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.parameterTypes = parameterTypes;
    }

    public <T extends AbstractMessage> T invoke(T request, Session session) throws Exception {
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            int type = parameterTypes[i];
            switch (type) {
                case Handler.MESSAGE:
                    args[i] = request;
                    break;
                case Handler.SESSION:
                    args[i] = session;
                    break;
                case Handler.HEADER:
                    args[i] = request.getHeader();
                    break;
            }
        }
        return (T) targetMethod.invoke(targetObject, args);
    }

    @Override
    public String toString() {
        return desc;
    }
}