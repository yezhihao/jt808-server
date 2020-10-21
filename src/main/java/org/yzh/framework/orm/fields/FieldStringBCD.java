package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldStringBCD extends BasicField<String> {

    private int strLen;

    public FieldStringBCD(Field field, PropertyDescriptor property) {
        super(field, property);
        this.strLen = length * 2;
    }

    @Override
    public String readValue(ByteBuf input, int length) {
        byte[] bytes = new byte[length];
        input.readBytes(bytes);
        char[] chars = Bcd.toChars(bytes);

        int i = Bcd.indexOf(chars, '0');
        if (i == 0)
            return new String(chars);
        return new String(chars, i, chars.length - i);
    }

    @Override
    public void writeValue(ByteBuf output, String value) {
        char[] chars = new char[strLen];
        int i = strLen - value.length();
        if (i >= 0) {
            value.getChars(0, strLen - i, chars, i);
            while (i > 0)
                chars[--i] = '0';
        } else {
            value.getChars(-i, strLen - i, chars, 0);
            log.error("字符长度超出限制: {}长度[{}],[{}]", desc, strLen, value);
        }
        byte[] src = Bcd.from(chars);
        output.writeBytes(src);
    }
}