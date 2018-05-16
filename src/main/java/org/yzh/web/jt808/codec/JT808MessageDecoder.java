package org.yzh.web.jt808.codec;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.commons.transform.BitOperator;
import org.yzh.web.jt808.dto.basics.Header;

import java.nio.charset.Charset;

public class JT808MessageDecoder extends MessageDecoder {

    public JT808MessageDecoder(Charset charset) {
        super(charset);
    }

    @Override
    public Header decodeHeader(ByteBuf buf) {
        byte checkCode = buf.getByte(buf.readableBytes() - 1);
        buf = buf.slice(0, buf.readableBytes() - 1);
        byte calculatedCheckCode = BitOperator.xor(buf);

        if (checkCode != calculatedCheckCode)
            System.out.println("校验码错误,checkCode=" + checkCode + ",calculatedCheckCode" + calculatedCheckCode);

        return decode(buf, Header.class);
    }

}