package org.yzh.codec;

import io.github.yezhihao.protostar.FieldFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.protocol.JT808Beans;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.t808.T0100;

/**
 * 编码分析
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DarkRepulsor {

    private static JTMessageEncoder encoder;

    static {
        FieldFactory.EXPLAIN = true;
        encoder = new JTMessageEncoder("org.yzh.protocol");
    }

    public static void main(String[] args) {
        T0100 message = JT808Beans.T0100();
        JT808Beans.H2013(message);

        ByteBuf byteBuf = encoder.encode(message);
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
}