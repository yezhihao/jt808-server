package org.yzh.framework.session;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.ChannelFutureListener;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    private Cache<String, Integer> versionCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).build();

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

    public void putVersion(String clientId, int version) {
        versionCache.put(clientId, version);
    }

    public Integer getVersion(String clientId) {
        return versionCache.getIfPresent(clientId);
    }
}