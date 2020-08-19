package org.yzh.framework.session;

import io.netty.channel.ChannelFutureListener;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public enum SessionManager {

    Instance;

    public static SessionManager getInstance() {
        return Instance;
    }

    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private ChannelFutureListener remover = future -> {
        Session session = future.channel().attr(Session.KEY).get();
        if (session != null)
            sessionMap.remove(session.getClientId(), session);
    };

    public Session get(String clientId) {
        return sessionMap.get(clientId);
    }

    public Collection<Session> all() {
        return sessionMap.values();
    }

    protected void put(String clientId, Session newSession) {
        Session oldSession = sessionMap.put(clientId, newSession);
        if (!newSession.equals(oldSession)) {
            newSession.channel.closeFuture().addListener(remover);
        }
    }
}