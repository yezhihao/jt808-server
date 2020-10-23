package org.yzh.framework.orm.converter;

import io.netty.buffer.ByteBuf;

public interface Converter {

    Object convert(Integer key, ByteBuf input);

    void convert(Integer key, ByteBuf output, Object value);
}