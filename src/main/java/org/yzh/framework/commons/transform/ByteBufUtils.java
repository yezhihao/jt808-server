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

    /**
     * Returns the number of bytes between the readerIndex of the haystack and
     * the first needle found in the haystack.  -1 is returned if no needle is
     * found in the haystack.
     */
    public static int indexOf(ByteBuf haystack, byte[] needle) {
        for (int i = haystack.readerIndex(); i < haystack.writerIndex(); i++) {
            int haystackIndex = i;
            int needleIndex;
            for (needleIndex = 0; needleIndex < needle.length; needleIndex++) {
                if (haystack.getByte(haystackIndex) != needle[needleIndex]) {
                    break;
                } else {
                    haystackIndex++;
                    if (haystackIndex == haystack.writerIndex() && needleIndex != needle.length - 1) {
                        return -1;
                    }
                }
            }

            if (needleIndex == needle.length) {
                // Found the needle from the haystack!
                return i - haystack.readerIndex();
            }
        }
        return -1;
    }
}