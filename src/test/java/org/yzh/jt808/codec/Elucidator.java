package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.dto.T0100;
import org.yzh.web.jt808.dto.basics.Message;

/**
 * 阐释者
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class Elucidator extends JT808MessageDecoder {

    private static final Elucidator elucidator = new Elucidator();

    public static void main(String[] args) {
        Class<? extends AbstractBody> clazz = T0100.class;
        String hex = "0100002e064762924824000200000000484f4f5000bfb5b4ef562d31000000000000000000000000000000015a0d5dff02bba64450393939370002";

        System.out.println(hex);
        System.out.println();
        elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)), Message.class, clazz);
    }

    @Override
    public Object read(ByteBuf buf, PropertySpec ps, int length, int version) {
        Property prop = ps.property;
        int i = buf.readerIndex();
        String hex = ByteBufUtil.hexDump(buf.readSlice(length));
        buf.readerIndex(i);

        Object value = super.read(buf, ps, length, version);
        System.out.println(prop.index() + "\t" + hex + "\t" + prop.desc() + "\t" + value);
        return value;
    }
}