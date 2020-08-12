package org.yzh.framework.mvc;

import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface HandlerInterceptor {
    /** 未找到对应的Handle */
    AbstractMessage notSupported(AbstractMessage<?> request, Session session);

    /** 调用之前 */
    boolean beforeHandle(AbstractMessage<?> request, Session session);

    /** 调用之后，返回值为void的 */
    AbstractMessage successful(AbstractMessage<?> request, Session session);

    /** 调用之后，有返回值的 */
    void afterHandle(AbstractMessage<?> request, AbstractMessage<?> response, Session session);

    /** 调用之后抛出异常的 */
    AbstractMessage exceptional(AbstractMessage<?> request, Session session, Exception e);
}