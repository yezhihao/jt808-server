package org.yzh.web.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.commons.transform.BitOperator;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

import java.nio.charset.Charset;

public class JT808MessageEncoder extends MessageEncoder<Header> {

    public JT808MessageEncoder(Charset charset) {
        super(charset);
    }

    @Override
    public ByteBuf encodeAll(PackageData<Header> body) {
        ByteBuf bodyBuf = encode(body);

        Header header = body.getHeader();
        header.setBodyLength(bodyBuf.readableBytes());

        ByteBuf headerBuf = encode(header);

        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);
        byte checkCode = BitOperator.xor(allBuf);

        allBuf.writeByte(checkCode);

        return allBuf;
    }

}
