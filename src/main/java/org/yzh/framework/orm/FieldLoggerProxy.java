package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

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
        String hex = ByteBufUtil.hexDump(buf.slice(before, after - before));
        println(this.index, this.desc, hex, value);
        return value;
    }

    @Override
    public void writeValue(ByteBuf buf, Object value) {
        int before = buf.writerIndex();
        target.writeValue(buf, value);
        int after = buf.writerIndex();
        String hex = ByteBufUtil.hexDump(buf.slice(before, after - before));
        println(this.index, this.desc, hex, value);
    }

    @Override
    public int compareTo(BasicField that) {
        return target.compareTo(that);
    }
}