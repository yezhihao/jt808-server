package org.yzh.protocol.basics;

import io.github.yezhihao.netmc.core.model.Message;
import io.github.yezhihao.netmc.session.Session;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.beans.Transient;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessage implements Message<Header> {

    private transient Session session;

    private transient byte[] payload;

    protected Header header;

    public JTMessage() {
    }

    public JTMessage(Header header) {
        this.header = header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    @Override
    public Object getMessageType() {
        if (header == null)
            return null;
        return header.getMessageId();
    }

    public String getMobileNo() {
        if (header == null)
            return null;
        return header.getMobileNo();
    }

    @Transient
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(512);
        sb.append(header).append(",");
        sb.append(new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE, new StringBuffer(256), null, true, false, true).setExcludeFieldNames("header"));
        return sb.toString();
    }
}