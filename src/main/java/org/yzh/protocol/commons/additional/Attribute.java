package org.yzh.protocol.commons.additional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 位置附加信息
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public abstract class Attribute {

    public abstract int getAttributeId();

    public abstract <T extends Attribute> T formBytes(byte... bytes);

    public abstract byte[] toBytes();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}