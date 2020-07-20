package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.web.jt808.codec.JT808MessageEncoder;

/**
 * 逐暗者
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public class DarkRepulsor extends JT808MessageEncoder {

    private static final DarkRepulsor darkRepulsor = new DarkRepulsor();

    public static void main(String[] args) {
        ByteBuf byteBuf = darkRepulsor.encode(CoderTest.register());
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }

    @Override
    public void write(ByteBuf buf, PropertySpec propertySpec, Object value, int version) {
        Property prop = propertySpec.property;
        int before = buf.writerIndex();
        super.write(buf, propertySpec, value, version);
        int after = buf.writerIndex();

        String hex = ByteBufUtil.hexDump(buf, before, after - before);
        System.out.println(prop.index() + "\t" + hex + "\t" + prop.desc() + "\t" + value);
    }
}