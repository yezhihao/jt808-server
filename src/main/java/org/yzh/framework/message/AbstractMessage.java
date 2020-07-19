package org.yzh.framework.message;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 消息基类
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public abstract class AbstractMessage<T extends AbstractBody> {
    /** 消息类型 */
    public abstract int getMessageId();
    /** 消息版本号 */
    public abstract int getVersionNo();
    /** 消息头长度 */
    public abstract int getHeadLength();
    /** 消息体长度 */
    public abstract int getBodyLength();

    public abstract void setBodyLength(int bodyLength);
    /** 加密方式 */
    public abstract int getEncryption();
    /** 是否分包 */
    public abstract boolean isSubpackage();
    /** 是否有版本 */
    public abstract boolean isVersion();
    /** 包总数 */
    public abstract Integer getPackageTotal();
    /** 包序号 */
    public abstract Integer getPackageNo();

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