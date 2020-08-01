package org.yzh.framework.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.yzh.framework.orm.model.AbstractHeader;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class Session {

    public static final AttributeKey<Session> KEY = AttributeKey.newInstance(Session.class.getName());

    protected final Channel channel;

    private volatile int serialNo = 0;
    private boolean registered = false;
    private String terminalId;

    private final long creationTime;
    private long lastAccessedTime;
    private final Map<String, Object> attributes;

    public Session(Channel channel) {
        this.channel = channel;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.attributes = new TreeMap<>();
        channel.attr(Session.KEY).set(this);
    }


    public void writeObject(Object message) {
        channel.writeAndFlush(message);
    }

    public int getId() {
        return channel.id().hashCode();
    }

    public int serialNo() {
        return serialNo;
    }

    public int nextSerialNo() {
        if (serialNo >= 0xffff)
            serialNo = 0;
        return serialNo++;
    }

    public boolean isRegistered() {
        return registered;
    }

    /**
     * 注册到SessionManager
     */
    public void register(AbstractHeader header) {
        this.terminalId = header.getTerminalId();
        this.registered = true;
        SessionManager.Instance.put(terminalId, this);
    }

    public String getTerminalId() {
        return terminalId;
    }


    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void access() {
        this.lastAccessedTime = System.currentTimeMillis();
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

    public void invalidate() {
        channel.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Session that = (Session) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return "[ip=" + channel.remoteAddress() +
                ", terminalId=" + terminalId +
                ", registered=" + registered +
                ']';
    }
}