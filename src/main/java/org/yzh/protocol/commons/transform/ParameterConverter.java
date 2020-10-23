package org.yzh.protocol.commons.transform;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.converter.Converter;

public class ParameterConverter implements Converter {

    @Override
    public Object convert(Integer key, ByteBuf input) {
        return ParameterTypes.INSTANCE.readFrom(key, input);
    }

    @Override
    public void convert(Integer key, ByteBuf output, Object value) {
        ParameterTypes.INSTANCE.writeTo(key, output, value);
    }
}