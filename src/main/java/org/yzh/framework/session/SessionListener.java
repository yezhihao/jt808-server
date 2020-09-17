package org.yzh.framework.session;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface SessionListener {
    default void sessionCreated(Session session) {
    }

    default void sessionDestroyed(Session session) {
    }
}