package org.yzh.framework.mvc;

import org.yzh.framework.mvc.model.AbstractHeader;
import org.yzh.framework.mvc.model.AbstractMessage;
import org.yzh.framework.session.Session;

/**
 * 消息拦截器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface HandlerInterceptor<T extends AbstractMessage<? extends AbstractHeader>> {
    /** 未找到对应的Handle */
    T notSupported(T request, Session session);

    /** 调用之前 */
    boolean beforeHandle(T request, Session session);

    /** 调用之后，返回值为void的 */
    T successful(T request, Session session);

    /** 调用之后，有返回值的 */
    void afterHandle(T request, T response, Session session);

    /** 调用之后抛出异常的 */
    T exceptional(T request, Session session, Exception e);
}