package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;

public class FieldLoggerProxy extends BasicField {

    private BasicField target;

    public FieldLoggerProxy(BasicField target) {
        super(target.field, target.property);
        this.target = target;
    }

    @Override
    public Object readValue(ByteBuf buf, int length) {
        int before = buf.readerIndex();
        Object value = target.readValue(buf, length);
        int after = buf.readerIndex();

        println(buf, before, after, value);
        return value;
    }

    @Override
    public void writeValue(ByteBuf buf, Object value) {
        int before = buf.writerIndex();
        target.writeValue(buf, value);
        int after = buf.writerIndex();
        println(buf, before, after, value);
    }

    @Override
    public int compareTo(BasicField that) {
        return target.compareTo(that);
    }
}