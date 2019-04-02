package org.yzh.web.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.commons.transform.BitOperator;
import org.yzh.web.jt808.dto.basics.Header;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 808协议解码器
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public class JT808MessageDecoder extends MessageDecoder {

    public JT808MessageDecoder(Charset charset) {
        super(charset);
    }

    @Override
    public Header decodeHeader(ByteBuf buf) {
        buf = unEscape(buf);

        byte checkCode = buf.getByte(buf.readableBytes() - 1);
        buf = buf.slice(0, buf.readableBytes() - 1);
        byte calculatedCheckCode = BitOperator.xor(buf);

        if (checkCode != calculatedCheckCode)
            System.out.println("校验码错误,checkCode=" + checkCode + ",calculatedCheckCode" + calculatedCheckCode);

        return decode(buf, Header.class);
    }

    /**
     * 转义还原
     */
    private static ByteBuf unEscape(ByteBuf source) {
        int low = source.readerIndex();
        int high = source.writerIndex();

        int mark = source.indexOf(low, high, (byte) 0x7d);
        if (mark == -1)
            return source;

        List<ByteBuf> bufList = new ArrayList<>(3);

        int len;
        do {

            len = mark + 2 - low;
            bufList.add(slice(source, low, len));
            low += len;

            mark = source.indexOf(low, high, (byte) 0x7d);
        } while (mark > 0);

        bufList.add(source.slice(low, high - low));

        ByteBuf[] bufs = bufList.toArray(new ByteBuf[bufList.size()]);

        return Unpooled.wrappedBuffer(bufs);
    }

    /**
     * 截取转义前报文，并还原转义位
     */
    private static ByteBuf slice(ByteBuf byteBuf, int index, int length) {
        byte second = byteBuf.getByte(index + length - 1);
        if (second == 0x02)
            byteBuf.setByte(index + length - 2, 0x7e);

        // 0x01 不做处理 p47
        // if (second == 0x01) {
        // }
        return byteBuf.slice(index, length - 1);
    }
}