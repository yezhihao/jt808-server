package org.yzh.framework.orm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.framework.session.Session;

import java.io.Serializable;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class AbstractMessage<T extends AbstractHeader> implements Serializable {

    private Session session;
    private T header;

    public AbstractMessage() {
    }

    public AbstractMessage(T header) {
        this.header = header;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setHeader(T header) {
        this.header = header;
    }

    public T getHeader() {
        return header;
    }

    public int getMessageId() {
        return header.getMessageId();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(512);
        sb.append(header).append(",");
        sb.append(new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE, new StringBuffer(256), null, true, false, true).setExcludeFieldNames("header"));
        return sb.toString();
    }
}