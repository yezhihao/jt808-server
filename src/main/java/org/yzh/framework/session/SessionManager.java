package org.yzh.framework.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public enum SessionManager {

    Instance;

    public static SessionManager getInstance() {
        return Instance;
    }

    private static final Logger log = LoggerFactory.getLogger(SessionManager.class.getSimpleName());

    private Map<String, Session> sessionIdMap = new ConcurrentHashMap<>();

    private Map<String, Session> terminalIdMap = new ConcurrentHashMap<>();

    public Session getBySessionId(String sessionId) {
        return sessionIdMap.get(sessionId);
    }

    public Session getByTerminalId(String terminalId) {
        return terminalIdMap.get(terminalId);
    }

    public synchronized Session put(String key, Session session) {
        if (session.getTerminalId() != null && !"".equals(session.getTerminalId().trim())) {
            this.terminalIdMap.put(session.getTerminalId(), session);
        }
        return sessionIdMap.put(key, session);
    }

    public synchronized Session removeBySessionId(String sessionId) {
        if (sessionId == null)
            return null;
        Session session = sessionIdMap.remove(sessionId);
        if (session == null)
            return null;
        if (session.getTerminalId() != null)
            this.terminalIdMap.remove(session.getTerminalId());
        return session;
    }

    // public synchronized void release(String sessionId) {
    // if (sessionId == null)
    // return;
    // Session session = sessionIdMap.release(sessionId);
    // if (session == null)
    // return;
    // if (session.getTerminalId() != null)
    // this.terminalIdMap.release(session.getTerminalId());
    // try {
    // if (session.getChannel() != null) {
    // if (session.getChannel().isActive() || session.getChannel().isOpen()) {
    // session.getChannel().close();
    // }
    // session = null;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    public Set<String> keySet() {
        return sessionIdMap.keySet();
    }

    public void forEach(BiConsumer<? super String, ? super Session> action) {
        sessionIdMap.forEach(action);
    }

    public Set<Entry<String, Session>> entrySet() {
        return sessionIdMap.entrySet();
    }

    public List<Session> toList() {
        return this.sessionIdMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

}