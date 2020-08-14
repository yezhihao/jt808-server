package org.yzh.framework.session;

import io.netty.channel.Channel;
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
            sessionMap.remove(session.getClientId());
    };

    public Session get(String clientId) {
        return sessionMap.get(clientId);
    }

    public Collection<Session> all() {
        return sessionMap.values();
    }

    protected void put(String clientId, Session session) {
        Channel channel = session.channel;
        boolean added = sessionMap.putIfAbsent(clientId, session) == null;
        if (added) {
            channel.closeFuture().addListener(remover);
        }
    }
}