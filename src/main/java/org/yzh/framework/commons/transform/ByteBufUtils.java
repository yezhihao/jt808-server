package org.yzh.framework.commons.transform;

import io.netty.buffer.ByteBuf;

public class ByteBufUtils {

    public static byte xor(ByteBuf byteBuf) {
        byte cs = 0;
        while (byteBuf.isReadable())
            cs ^= byteBuf.readByte();
        byteBuf.resetReaderIndex();
        return cs;
    }
}