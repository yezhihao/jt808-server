package org.yzh.protocol.commons.transform;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 位置附加信息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class Attribute {

    public abstract int getAttributeId();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}