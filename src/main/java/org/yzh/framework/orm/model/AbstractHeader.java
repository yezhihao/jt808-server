package org.yzh.framework.orm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 消息基类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class AbstractHeader<Type, ClientId> implements Serializable {

    /** 消息类型 */
    public abstract Type getMessageType();

    /** 客户端唯一标识 */
    public abstract ClientId getClientId();

    /** 消息流水号 */
    public abstract int getSerialNo();

    public abstract void setSerialNo(int serialNo);

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}