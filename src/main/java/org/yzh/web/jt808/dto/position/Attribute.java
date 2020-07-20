package org.yzh.web.jt808.dto.position;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Attribute {

    public abstract int getAttributeId();

    public abstract <T extends Attribute> T formBytes(byte... bytes);

    public abstract byte[] toBytes();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}