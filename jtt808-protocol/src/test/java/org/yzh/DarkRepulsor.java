package org.yzh;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

/**
 * 压力测试
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DarkRepulsor {

    private static JTMessageDecoder decoder = new JTMessageDecoder("org.yzh.protocol");
    private static JTMessageEncoder encoder = new JTMessageEncoder("org.yzh.protocol");

    //560
    public static void main(String[] args) {
        String hex = "7e0200407c0100000000017299841738ffff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c051e3737370000000000000000000000000000000000000000000000000000001105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e310128637e";
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));

        while (true) {
            long s = System.currentTimeMillis();

            for (int i = 0; i < 100000; i++) {
                JTMessage message = decoder.decode(buf);
                message.setSerialNo(message.getSerialNo() + 1);

                buf.release();
                buf = encoder.encode(message);
            }
            System.out.println(System.currentTimeMillis() - s);
        }
    }
}