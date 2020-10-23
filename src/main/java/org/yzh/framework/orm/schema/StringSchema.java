package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.Schema;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringSchema {

    public static class Chars implements Schema<String> {
        private static volatile Map<String, Chars> cache = new HashMap<>();

        public static Schema<String> getInstance(byte pad, String charset) {
            String key = new StringBuilder(10).append((char) pad).append('/').append(charset).toString();
            Chars instance = cache.get(key);
            if (instance == null) {
                synchronized (cache) {
                    if (instance == null) {
                        instance = new Chars(pad, charset);
                        cache.put(key, instance);
                        log.debug("new StringSchema({},{})", pad, charset);
                    }
                }
            }
            return instance;
        }

        private final byte pad;
        private final Charset charset;

        private Chars(byte pad, String charset) {
            this.pad = pad;
            this.charset = Charset.forName(charset);
        }

        @Override
        public String readFrom(ByteBuf input) {
            return readFrom(input, input.readableBytes());
        }

        @Override
        public String readFrom(ByteBuf input, int length) {
            int len = length > 0 ? length : input.readableBytes();
            byte[] bytes = new byte[len];
            input.readBytes(bytes);

            int st = 0;
            while ((st < len) && (bytes[st] == pad))
                st++;
            while ((st < len) && (bytes[len - 1] == pad))
                len--;
            return new String(bytes, st, len - st, charset);
        }

        @Override
        public void writeTo(ByteBuf output, String value) {
            byte[] bytes = value.getBytes(charset);
            output.writeBytes(bytes);
        }

        @Override
        public void writeTo(ByteBuf output, int length, String value) {
            byte[] bytes = value.getBytes(charset);
            if (length > 0) {
                int srcPos = length - bytes.length;

                if (srcPos > 0) {
                    byte[] pads = new byte[srcPos];
                    if (pad != 0x00)
                        Arrays.fill(pads, pad);
                    output.writeBytes(pads);
                    output.writeBytes(bytes);
                } else if (srcPos < 0) {
                    output.writeBytes(bytes, -srcPos, length);
                    log.error("字符长度超出限制: 长度[{}],数据长度[{}],{}", length, bytes.length, value);
                } else {
                    output.writeBytes(bytes);
                }
            } else {
                output.writeBytes(bytes);
            }
        }
    }


    public static class BCD implements Schema<String> {
        public static final Schema INSTANCE = new BCD();

        private BCD() {
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
}