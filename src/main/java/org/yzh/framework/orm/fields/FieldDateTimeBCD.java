package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;

public class FieldDateTimeBCD extends BasicField<LocalDateTime> {

    public FieldDateTimeBCD(Field field, PropertyDescriptor property) {
        super(field, property);
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