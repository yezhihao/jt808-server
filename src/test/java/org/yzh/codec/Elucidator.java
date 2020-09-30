package org.yzh.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.ArrayUtils;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.protocol.codec.JTMessageDecoder;

/**
 * 解码分析
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Elucidator extends JTMessageDecoder {

    private static final Elucidator elucidator = new Elucidator("org.yzh.protocol");

    public Elucidator(String basePackage) {
        super(basePackage);
    }

    public static void main(String[] args) {
        String hex = "020040610100000000017299841738ffff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c05033737371105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e31012863";

        System.out.println(hex);
        System.out.println();
        elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
    }

    @Override
    public Object read(ByteBuf buf, FieldMetadata fieldMetadata, int length) {
        int before = buf.readerIndex();
        Object value = super.read(buf, fieldMetadata, length);
        int after = buf.readerIndex();

        String hex = ByteBufUtil.hexDump(buf.slice(before, after - before));
        System.out.println(fieldMetadata.index + "\t" + hex + "\t" + fieldMetadata.desc + "\t" + (value.getClass().isArray() ? ArrayUtils.toString(value) : value));
        return value;
    }
}