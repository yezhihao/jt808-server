package org.yzh.framework.mvc.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class RawMessage<T extends AbstractHeader> extends AbstractMessage<T> {

    private byte[] payload;

    public RawMessage() {
    }

    public RawMessage(T header) {
        super(header);
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}