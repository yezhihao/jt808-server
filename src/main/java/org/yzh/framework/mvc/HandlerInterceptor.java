package org.yzh.framework.mvc;

import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

/**
 * 消息拦截器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface HandlerInterceptor<T extends AbstractHeader> {
    /** 未找到对应的Handle */
    AbstractMessage notSupported(AbstractMessage<T> request, Session session);

    /** 调用之前 */
    boolean beforeHandle(AbstractMessage<T> request, Session session);

    /** 调用之后，返回值为void的 */
    AbstractMessage successful(AbstractMessage<T> request, Session session);

    /** 调用之后，有返回值的 */
    void afterHandle(AbstractMessage<T> request, AbstractMessage<T> response, Session session);

    /** 调用之后抛出异常的 */
    AbstractMessage exceptional(AbstractMessage<T> request, Session session, Exception e);
}