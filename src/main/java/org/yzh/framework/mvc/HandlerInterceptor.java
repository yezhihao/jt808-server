package org.yzh.framework.mvc;

import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

public interface HandlerInterceptor {
    /** 未找到对应的Handle */
    void notFoundHandle(AbstractMessage<?> request, Session session) throws Exception;

    /** 调用之前 */
    boolean beforeHandle(AbstractMessage<?> request, Session session) throws Exception;

    /** 调用之后，返回值为void的 */
    void afterHandle(AbstractMessage<?> request, Session session) throws Exception;

    /** 调用之后，有返回值的 */
    void afterHandle(AbstractMessage<?> request, AbstractMessage<?> response) throws Exception;

    /** 超出队列或者线程处理能力的 */
    void queueOverflow(AbstractMessage<?> request, Session session);

    /** 调用之后抛出异常的 */
    void afterThrow(AbstractMessage<?> request, Session session, Exception e);
}