package org.yzh.framework.message;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractHeader {

    /**
     * 消息类型
     */
    public abstract Integer getType();

    /**
     * 消息头长度
     */
    public abstract Integer getHeaderLength();

    /**
     * 消息体长度
     */
    public abstract Integer getBodyLength();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}