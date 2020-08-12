package org.yzh.framework.commons.transform;

import io.netty.buffer.ByteBuf;

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
}