package org.yzh.framework.session;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class Session {

    private String ip;
    private String id;
    private String terminalId;
    private Channel channel = null;
    private boolean isAuthenticated = false;
    // 消息流水号 word(16) 按发送顺序从 0 开始循环累加
    private int serialNo = 0;
    // 客户端上次的连接时间，该值改变的情况:
    // 1. terminal --> server 心跳包
    // 2. terminal --> server 数据包
    private long lastCommunicateTimeStamp = 0l;

    public Session() {
    }

    public static String buildId(Channel channel) {
        return channel.id().asLongText();
    }

    public static Session buildSession(Channel channel) {
        return buildSession(channel, null);
    }

    public static Session buildSession(Channel channel, String terminalId) {
        Session session = new Session();
        session.setIp(channel.remoteAddress().toString());
        session.setChannel(channel);
        session.setId(buildId(channel));
        session.setTerminalId(terminalId);
        session.setLastCommunicateTimeStamp(System.currentTimeMillis());
        return session;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public long getLastCommunicateTimeStamp() {
        return lastCommunicateTimeStamp;
    }

    public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
        this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
    }

    public SocketAddress getRemoteAddr() {
        return this.channel.remoteAddress();
    }

    public synchronized int nextSerialNo() {
        if (serialNo >= 0xffff)
            serialNo = 0;
        return serialNo++;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (getClass() != that.getClass())
            return false;
        Session other = (Session) that;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[ip=" + ip + ", terminalId=" + terminalId + ']';
    }
}