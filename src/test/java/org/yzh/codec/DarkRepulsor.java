package org.yzh.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.protocol.JT808Beans;
import org.yzh.protocol.codec.JTMessageEncoder;

/**
 * 编码分析
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DarkRepulsor {

    private static JTMessageEncoder darkRepulsor;

    static {
        FieldMetadata.EXPLAIN = true;
        darkRepulsor = new JTMessageEncoder("org.yzh.protocol");
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = darkRepulsor.encode(JT808Beans.H2013(JT808Beans.T0200Attributes()));
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
}