package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.Schema;

import java.time.LocalDateTime;

public class FieldDateTimeBCD implements Schema<LocalDateTime> {

    public static final Schema INSTANCE = new FieldDateTimeBCD();

    private FieldDateTimeBCD() {
    }

    @Override
    public LocalDateTime readFrom(ByteBuf input) {
        byte[] bytes = new byte[6];
        input.readBytes(bytes);
        return Bcd.toDateTime(bytes);
    }

    @Override
    public LocalDateTime readFrom(ByteBuf input, int length) {
        byte[] bytes = new byte[length];
        input.readBytes(bytes);
        return Bcd.toDateTime(bytes);
    }

    @Override
    public void writeTo(ByteBuf output, LocalDateTime value) {
        output.writeBytes(Bcd.from(value));
    }

    @Override
    public void writeTo(ByteBuf output, int length, LocalDateTime value) {
        output.writeBytes(Bcd.from(value));
    }
}