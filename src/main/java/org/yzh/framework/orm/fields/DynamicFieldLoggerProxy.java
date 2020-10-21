package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public class DynamicFieldLoggerProxy extends DynamicField {

    private DynamicField target;

    public DynamicFieldLoggerProxy(DynamicField target) {
        super(target.field, target.property);
        this.target = target;
    }

    @Override
    public Object readValue(ByteBuf input, int length) {
        int before = input.readerIndex();
        Object value = target.readValue(input, length);
        int after = input.readerIndex();
        String hex = ByteBufUtil.hexDump(input.slice(before, after - before));
        println(this.index, this.desc, hex, value);
        return value;
    }

    @Override
    public void writeValue(ByteBuf output, Object value) {
        int before = output.writerIndex();
        target.writeValue(output, value);
        int after = output.writerIndex();
        String hex = ByteBufUtil.hexDump(output.slice(before, after - before));
        println(this.index, this.desc, hex, value);
    }

    @Override
    protected int readLength(ByteBuf input) {
        int before = input.readerIndex();
        int value = target.readLength(input);
        int after = input.readerIndex();
        String hex = ByteBufUtil.hexDump(input.slice(before, after - before));
        println(this.index - this.lengthSize, this.desc + "长度", hex, value);
        return value;
    }

    @Override
    protected void setLength(ByteBuf output, int offset, int length) {
        target.setLength(output, offset, length);
        println(offset, this.desc + "长度", Integer.toHexString(length), length);
    }

    @Override
    public int compareTo(BasicField that) {
        return target.compareTo(that);
    }
}