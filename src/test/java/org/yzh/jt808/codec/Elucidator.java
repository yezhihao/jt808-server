package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.web.jt.codec.JTMessageDecoder;

/**
 * 解码分析
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class Elucidator extends JTMessageDecoder {

    private static final Elucidator elucidator = new Elucidator("org.yzh.web.jt");

    public Elucidator(String basePackage) {
        super(basePackage);
    }

    public static void main(String[] args) {
        String hex = "810000030138014398460000108f09b1";

        System.out.println(hex);
        System.out.println();
        elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
    }

    @Override
    public Object read(ByteBuf buf, FieldMetadata fieldMetadata, int length, int version) {
        int before = buf.readerIndex();
        Object value = super.read(buf, fieldMetadata, length, version);
        int after = buf.readerIndex();

        String hex = ByteBufUtil.hexDump(buf.slice(before, after - before));
        System.out.println(fieldMetadata.index + "\t" + hex + "\t" + fieldMetadata.desc + "\t" + value);
        return value;
    }
}