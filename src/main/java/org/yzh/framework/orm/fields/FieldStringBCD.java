package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.Schema;

public class FieldStringBCD implements Schema<String> {

    public static final Schema INSTANCE = new FieldStringBCD();

    private FieldStringBCD() {
    }

    @Override
    public String readFrom(ByteBuf input) {
        return readFrom(input, input.readableBytes());
    }

    @Override
    public String readFrom(ByteBuf input, int length) {
        byte[] bytes = new byte[length];
        input.readBytes(bytes);
        char[] chars = Bcd.toChars(bytes);

        int i = Bcd.indexOf(chars, '0');
        if (i == 0)
            return new String(chars);
        return new String(chars, i, chars.length - i);
    }

    @Override
    public void writeTo(ByteBuf output, String value) {
        writeTo(output, value.length() >> 1, value);
    }

    @Override
    public void writeTo(ByteBuf output, int length, String value) {
        int charLength = length << 1;
        char[] chars = new char[charLength];
        int i = charLength - value.length();
        if (i >= 0) {
            value.getChars(0, charLength - i, chars, i);
            while (i > 0)
                chars[--i] = '0';
        } else {
            value.getChars(-i, charLength - i, chars, 0);
            log.warn("字符长度超出限制: 长度[{}],[{}]", charLength, value);
        }
        byte[] src = Bcd.from(chars);
        output.writeBytes(src);
    }
}