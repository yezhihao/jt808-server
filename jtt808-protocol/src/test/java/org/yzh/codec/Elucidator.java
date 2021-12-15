package org.yzh.codec;

import io.github.yezhihao.protostar.util.Explain;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageAdapter;

/**
 * 解码分析
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class Elucidator {

    public static final JTMessageAdapter coder = new JTMessageAdapter("org.yzh.protocol");

    public static void main(String[] args) {
        String hex = "020000d40123456789017fff000004000000080006eeb6ad02633df7013800030063200707192359642f000000400101020a0a02010a1e00640001b2070003640e200707192359000100000061646173200827111111010101652f000000410202020a0000000a1e00c8000516150006c81c20070719235900020000000064736d200827111111020202662900000042031e012c00087a23000a2c2a200707192359000300000074706d732008271111110303030067290000004304041e0190000bde31000d90382007071923590004000000006273642008271111110404049d";
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));

        Explain explain = new Explain();
        JTMessage message = coder.decode(buf, explain);
        System.out.println(message);
        System.out.println(hex);
        explain.println();
    }
}