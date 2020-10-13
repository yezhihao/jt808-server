package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class FieldDateTimeBCD extends FieldMetadata<LocalDateTime> {

    public FieldDateTimeBCD(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public LocalDateTime readValue(ByteBuf buf, int length) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return Bcd.toDateTime(bytes);
    }

    @Override
    public void writeValue(ByteBuf buf, LocalDateTime value) {
        buf.writeBytes(Bcd.from(value));
    }
}