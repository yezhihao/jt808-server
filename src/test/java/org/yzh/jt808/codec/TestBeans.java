package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.commons.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.yzh.jt808.codec.Beans.*;

/**
 * JT/T 808协议单元测试类
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class TestBeans {

    public static final JTMessageDecoder decoder = new JTMessageDecoder("org.yzh.protocol");

    public static final JTMessageEncoder encoder = new JTMessageEncoder("org.yzh.protocol");

    @Test
    public void testT8303() {
        selfCheck(H2013(T8303()));
        selfCheck(H2019(T8303()));
    }

    @Test
    public void testT0805() {
        selfCheck(H2013(T0805()));
        selfCheck(H2019(T0805()));
    }

    @Test
    public void testT0705() {
        selfCheck(H2013(T0705()));
        selfCheck(H2019(T0705()));
    }

    @Test
    public void testT0A00_8A00() {
        selfCheck(H2013(T0A00_8A00()));
        selfCheck(H2019(T0A00_8A00()));
    }

    @Test
    public void testT8103() {
        selfCheck(H2013(T8103()));
        selfCheck(H2019(T8103()));
    }

    @Test
    public void testT0001() {
        selfCheck(H2013(T0001()));
        selfCheck(H2019(T0001()));
    }

    @Test
    public void testT0200() {
        selfCheck(H2013(T0200Attributes()));
        selfCheck(H2019(T0200Attributes()));
    }

    @Test
    public void testT0301() {
        selfCheck(H2013(T0301()));
        selfCheck(H2019(T0301()));
    }

    @Test
    public void testT0302() {
        selfCheck(H2013(T0302()));
        selfCheck(H2019(T0302()));
    }

    @Test
    public void testT0704() {
        selfCheck(H2013(T0704()));
        selfCheck(H2019(T0704()));
    }

    @Test
    public void testT0800() {
        selfCheck(H2013(T0800()));
        selfCheck(H2019(T0800()));
    }

    @Test
    public void testT8106() {
        selfCheck(H2013(T8106()));
        selfCheck(H2019(T8106()));
    }

    @Test
    public void testT8108() {
        selfCheck(H2013(T8108()));
        selfCheck(H2019(T8108()));
    }

    @Test
    public void testT0100() {
        selfCheck(H2013(T0100()));
        selfCheck(H2019(T0100()));
    }

    @Test
    public void testT0303() {
        selfCheck(H2013(T0303()));
        selfCheck(H2019(T0303()));
    }

    @Test
    public void testT0702() {
        selfCheck(H2013(T0702()));
        selfCheck(H2019(T0702()));
    }

    @Test
    public void testT0802() {
        selfCheck(H2013(T0802()));
        selfCheck(H2019(T0802()));
    }

    @Test
    public void testT0901() {
        selfCheck(H2013(T0901()));
        selfCheck(H2019(T0901()));
    }

    @Test
    public void testT8105() {
        selfCheck(H2013(T8105()));
        selfCheck(H2019(T8105()));
    }

    @Test
    public void testT8003() {
        selfCheck(H2013(T8003()));
        selfCheck(H2019(T8003()));
    }

    @Test
    public void testT8202() {
        selfCheck(H2013(T8202()));
        selfCheck(H2019(T8202()));
    }

    @Test
    public void testT8203() {
        selfCheck(H2013(T8203()));
        selfCheck(H2019(T8203()));
    }

    @Test
    public void testT8302() {
        selfCheck(H2013(T8302()));
        selfCheck(H2019(T8302()));
    }

    @Test
    public void testT0107() {
        selfCheck(H2013(T0107()));
        selfCheck(H2019(T0107()));
    }

    @Test
    public void testT0108() {
        selfCheck(H2013(T0108()));
        selfCheck(H2019(T0108()));
    }

    @Test
    public void testT0104() {
        selfCheck(H2013(T0104()));
        selfCheck(H2019(T0104()));
    }

    @Test
    public void testT0102() {
        selfCheck(H2013(T0102()));
        selfCheck(H2019(T0102()));
    }

    @Test
    public void testT8100() {
        selfCheck(H2013(T8100()));
        selfCheck(H2019(T8100()));
    }

    @Test
    public void testT0801() {
        selfCheck(H2013(T0801()));
        selfCheck(H2019(T0801()));
    }

    @Test
    public void testT0201_0500() {
        selfCheck(H2013(T0201_0500()));
        selfCheck(H2019(T0201_0500()));
    }

    @Test
    public void testT8600() {
        selfCheck(H2013(T8600()));
        selfCheck(H2019(T8600()));
    }

    @Test
    public void testT8401() {
        selfCheck(H2013(T8401()));
        selfCheck(H2019(T8401()));
    }

    @Test
    public void testT8804() {
        selfCheck(H2013(T8804()));
        selfCheck(H2019(T8804()));
    }

    @Test
    public void testT8604() {
        selfCheck(H2013(T8604()));
        selfCheck(H2019(T8604()));
    }

    @Test
    public void testT8900_0900() {
        selfCheck(H2013(T8900_0900()));
        selfCheck(H2019(T8900_0900()));
    }

    @Test
    public void testT8800() {
        selfCheck(H2013(T8800()));
        selfCheck(H2019(T8800()));
    }

    @Test
    public void testT8400() {
        selfCheck(H2013(T8400()));
        selfCheck(H2019(T8400()));
    }

    @Test
    public void testT8304() {
        selfCheck(H2013(T8304()));
        selfCheck(H2019(T8304()));
    }

    @Test
    public void testT8301() {
        selfCheck(H2013(T8301()));
        selfCheck(H2019(T8301()));
    }

    @Test
    public void testT8500() {
        selfCheck(H2013(T8500()));
        selfCheck(H2019(T8500()));
    }

    @Test
    public void testT8606() {
        selfCheck(H2013(T8606()));
        selfCheck(H2019(T8606()));
    }

    @Test
    public void testT8802() {
        selfCheck(H2013(T8802()));
        selfCheck(H2019(T8802()));
    }

    @Test
    public void testT8601() {
        selfCheck(H2013(T8601()));
        selfCheck(H2019(T8601()));
    }

    @Test
    public void testT8300() {
        selfCheck(H2013(T8300()));
        selfCheck(H2019(T8300()));
    }

    @Test
    public void testT8805() {
        selfCheck(H2013(T8805()));
        selfCheck(H2019(T8805()));
    }

    @Test
    public void testT8801() {
        selfCheck(H2013(T8801()));
        selfCheck(H2019(T8801()));
    }

    @Test
    public void testT8602() {
        selfCheck(H2013(T8602()));
        selfCheck(H2019(T8602()));
    }

    @Test
    public void testT8803() {
        selfCheck(H2013(T8803()));
        selfCheck(H2019(T8803()));
    }


    public static AbstractMessage transform(String hex) {
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        AbstractMessage bean = decoder.decode(buf);
        return bean;
    }

    public static String transform(AbstractMessage bean) {
        ByteBuf buf = encoder.encode(bean);
        String hex = ByteBufUtil.hexDump(buf);
        return hex;
    }

    public static void selfCheck(String hex1) {
        System.out.println(hex1);

        AbstractMessage bean1 = transform(hex1);
        String json1 = JsonUtils.toJson(bean1);
        System.out.println(json1);

        String hex2 = transform(bean1);
        System.out.println(hex2);

        AbstractMessage bean2 = transform(hex2);
        String json2 = JsonUtils.toJson(bean2);
        System.out.println(json2);

        System.out.println();
        assertEquals("hex not equals", hex1, hex2);
        assertEquals("object not equals", json1, json2);
    }

    public static void selfCheck(AbstractMessage bean1) {
        String json1 = JsonUtils.toJson(bean1);
        System.out.println("json1 " + json1);

        String hex1 = transform(bean1);
        System.out.println("hex1 " + hex1);

        AbstractMessage bean2 = transform(hex1);
        String json2 = JsonUtils.toJson(bean2);
        System.out.println("json2 " + json2);

        String hex2 = transform(bean2);
        System.out.println("hex2 " + hex2);

        assertEquals("hex not equals", hex1, hex2);
        assertEquals("object not equals", json1, json2);
    }
}