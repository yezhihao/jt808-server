package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;

public class FieldStringBCD extends FieldMetadata {

    public FieldStringBCD(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public Object readValue(ByteBuf buf, int length) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return Bcd.leftTrim(Bcd.toStr(bytes), '0');
    }

    @Override
    public void writeValue(ByteBuf buf, Object value) {
        buf.writeBytes(Bcd.fromStr(Bcd.leftPad((String) value, length * 2, '0')));
    }
}