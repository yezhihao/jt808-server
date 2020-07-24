package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.FieldSpec;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.web.jt.codec.JTMessageEncoder;

/**
 * 逐暗者
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class DarkRepulsor extends JTMessageEncoder {

    private static final DarkRepulsor darkRepulsor = new DarkRepulsor("org.yzh.web.jt");

    public DarkRepulsor(String basePackage) {
        super(basePackage);
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = darkRepulsor.encode(CoderTest.register());
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }

    @Override
    public void write(ByteBuf buf, FieldSpec propertySpec, Object value, int version) {
        Field prop = propertySpec.field;
        int before = buf.writerIndex();
        super.write(buf, propertySpec, value, version);
        int after = buf.writerIndex();

        String hex = ByteBufUtil.hexDump(buf, before, after - before);
        System.out.println(prop.index() + "\t" + hex + "\t" + prop.desc() + "\t" + value);
    }
}