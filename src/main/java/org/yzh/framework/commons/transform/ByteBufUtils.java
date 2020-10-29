package org.yzh.framework.commons.transform;

import io.netty.buffer.ByteBuf;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ByteBufUtils {

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

    public static boolean startsWith(ByteBuf haystack, byte[] prefix) {
        for (int i = 0, j = haystack.readerIndex(); i < prefix.length; )
            if (prefix[i++] != haystack.getByte(j++))
                return false;
        return true;
    }
}