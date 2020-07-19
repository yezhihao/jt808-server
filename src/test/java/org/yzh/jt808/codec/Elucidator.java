package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.dto.Register;
import org.yzh.web.jt808.dto.basics.Message;

/**
 * 阐释者
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public class Elucidator extends JT808MessageDecoder {

    private static final Elucidator elucidator = new Elucidator();

    public static void main(String[] args) {
        Class<? extends AbstractBody> clazz = Register.class;
        String hex = "007d0140550100000000001111111111007d01baf0bb63202020202020202020203420202020202020202020202020202020202020202042534a2d47462d303600000000000000000000000000000000000000000000007465737431323301b2e241383838383838e9";

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