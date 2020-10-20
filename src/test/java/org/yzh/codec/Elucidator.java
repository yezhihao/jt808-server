package org.yzh.codec;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.orm.FieldFactory;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageDecoder;

/**
 * 解码分析
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Elucidator {

    private static JTMessageDecoder elucidator;

    static {
        FieldFactory.EXPLAIN = true;
        elucidator = new JTMessageDecoder("org.yzh.protocol");
    }

    public static void main(String[] args) {
        String hex = "020000cc0123456789017fff000004000000080006eeb6ad02633df7013800030063200707192359642e000000400102020a0a02010a1e00640001e0f3000392fa2007071923590300717765313233200827111111010301652b000000410102020a0a1e00640001e0f3000392fa2007071923590300717765313233200827111111010301662700000042011e00640001e0f3000392fa200707192359030071776531323320082711111101030167280000004301021e00640001e0f3000392fa200707192359030071776531323320082711111101030189";

        JTMessage decode = elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
        System.out.println(hex);
        System.out.println(decode.getHeader());
    }
}