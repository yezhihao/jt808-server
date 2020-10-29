package org.yzh.protocol.commons;

import io.netty.buffer.ByteBuf;

public class JTUtils {

    /**
     * BCC校验(异或校验)
     */
    public static byte bcc(ByteBuf byteBuf) {
        byte cs = 0;
        while (byteBuf.isReadable())
            cs ^= byteBuf.readByte();
        byteBuf.resetReaderIndex();
        return cs;
    }

    public static int headerLength(int version, boolean isSubpackage) {
        if (version > 0)
            return isSubpackage ? 21 : 17;
        else
            return isSubpackage ? 16 : 12;
    }
}