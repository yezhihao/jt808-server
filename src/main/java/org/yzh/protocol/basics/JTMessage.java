package org.yzh.protocol.basics;

import io.github.yezhihao.netmc.core.model.Message;
import io.github.yezhihao.netmc.session.Session;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.protocol.commons.MessageId;

import java.beans.Transient;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessage implements Message {

    private transient Session session;

    private transient byte[] payload;

    protected Header header;

    public JTMessage() {
    }

    public JTMessage(int messageId) {
        this.header = new Header(messageId);
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    @Transient
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Transient
    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Transient
    @Override
    public String getClientId() {
        return header.getMobileNo();
    }

    @Transient
    @Override
    public int getMessageId() {
        return header.getMessageId();
    }

    @Transient
    @Override
    public String getMessageName() {
        return MessageId.get(header.getMessageId());
    }

    @Transient
    @Override
    public int getSerialNo() {
        return header.getSerialNo();
    }

    public int reflectMessageId() {
        io.github.yezhihao.protostar.annotation.Message messageType = this.getClass().getAnnotation(io.github.yezhihao.protostar.annotation.Message.class);
        if (messageType != null && messageType.value().length > 0)
            return messageType.value()[0];
        return 0;
    }

    public void transform() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(768);
        sb.append(header).append(',');
        sb.append(new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE, sb, null, false, false, true).setExcludeFieldNames("header"));
        return sb.toString();
    }
}