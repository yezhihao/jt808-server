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
    public LocalDateTime readValue(ByteBuf input, int length) {
        byte[] bytes = new byte[length];
        input.readBytes(bytes);
        return Bcd.toDateTime(bytes);
    }

    @Override
    public void writeValue(ByteBuf output, LocalDateTime value) {
        output.writeBytes(Bcd.from(value));
    }
}