package org.yzh.framework.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.model.Header;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Session {

    private static final Logger log = LoggerFactory.getLogger(Session.class.getSimpleName());

    public static final AttributeKey<Session> KEY = AttributeKey.newInstance(Session.class.getName());

    protected final Channel channel;

    private AtomicInteger serialNo = new AtomicInteger(0);
    private boolean registered = false;
    private Object clientId;

    private final long creationTime;
    private volatile long lastAccessedTime;
    private Map<String, Object> attributes;
    private Object subject;
    private Object snapshot;
    private Integer protocolVersion;

    private SessionManager sessionManager;

    protected Session(Channel channel, SessionManager sessionManager) {
        this.channel = channel;
        this.sessionManager = sessionManager;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.attributes = new TreeMap<>();
    }

    public void writeObject(Object message) {
        log.info("<<<<<<<<<<消息下发{},{}", this, message);
        channel.writeAndFlush(message);
    }

    public int getId() {
        return channel.id().hashCode();
    }

    public int nextSerialNo() {
        int current;
        int next;
        do {
            current = serialNo.get();
            next = current > 0xffff ? 0 : current;
        } while (!serialNo.compareAndSet(current, next + 1));
        return next;
    }

    public boolean isRegistered() {
        return registered;
    }

    /**
     * 注册到SessionManager
     */
    public void register(Header header) {
        this.register(header, null);
    }

    public void register(Header header, Object subject) {
        this.clientId = header.getClientId();
        this.registered = true;
        this.subject = subject;
        sessionManager.put(clientId, this);
    }

    public Object getClientId() {
        return clientId;
    }


    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public long access() {
        lastAccessedTime = System.currentTimeMillis();
        return lastAccessedTime;
    }

    public Collection<String> getAttributeNames() {
        return attributes.keySet();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public Object getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Object snapshot) {
        this.snapshot = snapshot;
    }

    public Integer getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Integer cachedProtocolVersion(Object clientId) {
        return this.sessionManager.getVersion(clientId);
    }

    public void recordProtocolVersion(Object clientId, int protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.sessionManager.putVersion(clientId, protocolVersion);
    }

    public void invalidate() {
        channel.close();
        sessionManager.callSessionDestroyedListener(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Session that = (Session) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(66);
        sb.append("[ip=").append(channel.remoteAddress());
        sb.append(", cid=").append(clientId);
        sb.append(", reg=").append(registered);
        sb.append(']');
        return sb.toString();
    }
}