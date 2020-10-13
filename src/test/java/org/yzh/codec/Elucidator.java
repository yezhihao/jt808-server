package org.yzh.codec;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.protocol.codec.JTMessageDecoder;

/**
 * 解码分析
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Elucidator {

    private static JTMessageDecoder elucidator;

    static {
        FieldMetadata.EXPLAIN = true;
        elucidator = new JTMessageDecoder("org.yzh.protocol");
    }

    public static void main(String[] args) {
        String hex = "020040610100000000017299841738ffff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c05033737371105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e31012863";

        System.out.println(hex);
        System.out.println();
        elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
    }
}