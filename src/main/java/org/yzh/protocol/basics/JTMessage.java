package org.yzh.protocol.basics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.framework.mvc.model.AbstractMessage;
import org.yzh.framework.session.Session;

import java.beans.Transient;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessage extends AbstractMessage<Header> {

    private transient Session session;

    public JTMessage() {
    }

    public JTMessage(Header header) {
        super(header);
    }

    @Transient
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(512);
        sb.append(header).append(",");
        sb.append(new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE, new StringBuffer(256), null, true, false, true).setExcludeFieldNames("header"));
        return sb.toString();
    }
}