package org.yzh.framework.message;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractMessage<T extends AbstractBody> {

    /**
     * 消息类型
     */
    public abstract Integer getType();

    /**
     * 是否有子包
     */
    public abstract boolean hasSubPackage();

    /**
     * 消息头长度
     */
    public abstract Integer getHeaderLength();

    /**
     * 消息体长度
     */
    public abstract Integer getBodyLength();

    public abstract void setBodyLength(Integer bodyLength);

    protected T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}