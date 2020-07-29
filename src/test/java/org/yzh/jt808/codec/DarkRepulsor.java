package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.protocol.codec.JTMessageEncoder;

/**
 * 编码分析
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class DarkRepulsor extends JTMessageEncoder {

    private static final DarkRepulsor darkRepulsor = new DarkRepulsor("org.yzh.protocol");

    public DarkRepulsor(String basePackage) {
        super(basePackage);
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = darkRepulsor.encode(Beans.H2013(Beans.T0100()));
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }

    @Override
    public void write(ByteBuf buf, FieldMetadata fieldMetadata, Object value, int version) {
        int before = buf.writerIndex();
        super.write(buf, fieldMetadata, value, version);
        int after = buf.writerIndex();

        String hex = ByteBufUtil.hexDump(buf, before, after - before);
        System.out.println(fieldMetadata.index + "\t" + hex + "\t" + fieldMetadata.desc + "\t" + value);
    }
}