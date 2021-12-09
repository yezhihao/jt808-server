package org.yzh.codec;

import io.github.yezhihao.protostar.FieldFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.protocol.BeanTest;
import org.yzh.protocol.JT808Beans;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageEncoder;

/**
 * 编码分析
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DarkRepulsor {

    public static final JTMessageEncoder encoder;

    static {
        FieldFactory.EXPLAIN = true;
        encoder = new JTMessageEncoder("org.yzh.protocol");
    }

    public static void main(String[] args) {
        JTMessage message = JT808Beans.T0100();
        BeanTest.H2013(message);

        ByteBuf byteBuf = encoder.encode(message);
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
        System.out.println(message);
    }
}