package org.yzh.framework.commons.transform;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ByteBufUtils {

    /**
     * BCC校验(异或校验)
     */
    public static byte bcc(ByteBuf byteBuf) {
        byte cs = byteBuf.readByte();
        while (byteBuf.isReadable())
            cs ^= byteBuf.readByte();
        byteBuf.resetReaderIndex();
        return cs;
    }

    public static ByteBuf[] wrappedBuffer(byte[]... bytes) {
        ByteBuf[] result = new ByteBuf[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            result[i] = Unpooled.wrappedBuffer(bytes[i]);
        return result;
    }
}