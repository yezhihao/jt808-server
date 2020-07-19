package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.yzh.framework.commons.transform.JsonUtils;
import org.yzh.framework.message.AbstractBody;
import org.yzh.framework.message.AbstractMessage;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.codec.JT808MessageEncoder;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Message;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JT/T 808协议单元测试类
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public class CoderTest {

    private static final JT808MessageDecoder decoder = new JT808MessageDecoder();

    private static final JT808MessageEncoder encoder = new JT808MessageEncoder();

    public static <T extends AbstractBody> AbstractMessage<T> transform(Class<T> clazz, String hex) {
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        AbstractMessage<T> bean = decoder.decode(buf, Message.class, clazz);
        return bean;
    }

    public static String transform(AbstractMessage bean) {
        ByteBuf buf = encoder.encode(bean);
        String hex = ByteBufUtil.hexDump(buf);
        return hex;
    }

    public static void selfCheck(Class<? extends AbstractBody> clazz, String hex1) {
        AbstractMessage bean1 = transform(clazz, hex1);

        String hex2 = transform(bean1);
        AbstractMessage bean2 = transform(clazz, hex2);

        String json1 = JsonUtils.toJson(bean1);
        String json2 = JsonUtils.toJson(bean2);
        System.out.println(hex1);
        System.out.println(hex2);
        System.out.println(json1);
        System.out.println(json2);
        System.out.println();

        assertEquals("hex not equals", hex1, hex2);
        assertEquals("object not equals", json1, json2);
    }

    public static void selfCheck(AbstractMessage bean1) {
        String hex1 = transform(bean1);

        AbstractMessage bean2 = transform(bean1.getBody().getClass(), hex1);
        String hex2 = transform(bean2);

        String json1 = JsonUtils.toJson(bean1);
        String json2 = JsonUtils.toJson(bean2);
        System.out.println("hex1 " + hex1);
        System.out.println("hex2 " + hex2);
        System.out.println("json1 " + json1);
        System.out.println("json2 " + json2);
        System.out.println();

        assertEquals("hex not equals", hex1, hex2);
        assertEquals("object not equals", json1, json2);
    }

    public static Message newMessage(AbstractBody body) {
        Message message = new Message();
        message.setMessageId(125);
        message.setProperties(1);
        message.setMobileNo("11111111111");
        message.setSerialNo(125);
        message.setVersion(true);
        message.setVersionNo(1);
        message.setEncryption(0);
        message.setReserved(false);
        message.setBody(body);
        return message;
    }


    // 位置信息汇报 0x0200
    @Test
    public void testPositionReport() {
        String hex1 = "0200006a064762924976014d000003500004100201d9f1230743425e000300a6ffff190403133450000000250400070008000000e2403836373733323033383535333838392d627566322d323031392d30342d30332d31332d33342d34392d3735372d70686f6e652d2e6a706700000020000c14cde78d";
        selfCheck(T0200.class, hex1);
    }


    // 终端注册应答 0x8100
    @Test
    public void testRegisterResult() {
        selfCheck(T8100.class, "8100000306476292482425b4000201cd");
    }


    // 终端注册 0x0100
    @Test
    public void testRegister() {
        selfCheck(T0200.class, "0100002e064762924824000200000000484f4f5000bfb5b4ef562d31000000000000000000000000000000015a0d5dff02bba64450393939370002");
        selfCheck(register());
    }

    public static Message register() {
        T0100 bean = new T0100();
        bean.setProvinceId(31);
        bean.setCityId(0115);
        bean.setManufacturerId("4");
        bean.setTerminalType("BSJ-GF-06");
        bean.setTerminalId("test123");
        bean.setLicensePlateColor(1);
        bean.setLicensePlate("测A888888");
        return newMessage(bean);
    }


    // 提问下发 0x8302
    @Test
    public void testQuestionMessage() {
        selfCheck(T8302.class, "8302001a017701840207001010062c2c2c2c2c2101000331323302000334353603000337383954");

        selfCheck(questionMessage());
    }

    public static Message questionMessage() {
        T8302 bean = new T8302();
        List<T8302.Option> options = new ArrayList();

        bean.buildSign(new int[]{1});
        bean.setContent("123");
        bean.setOptions(options);

        options.add(new T8302.Option(1, "asd1"));
        options.add(new T8302.Option(2, "zxc2"));
        return newMessage(bean);
    }


    // 设置电话本 0x8401
    @Test
    public void testPhoneBook() {
        selfCheck(T8401.class, "0001002e02000000001500250203020b043138323137333431383032d5c5c8fd010604313233313233c0eecbc4030604313233313233cdf5cee535");

        selfCheck(phoneBook());
    }

    public static Message phoneBook() {
        T8401 bean = new T8401();
        bean.setType(T8401.Append);
        bean.add(new T8401.Item(2, "18217341802", "张三"));
        bean.add(new T8401.Item(1, "123123", "李四"));
        bean.add(new T8401.Item(3, "123123", "王五"));
        return newMessage(bean);
    }


    // 事件设置 0x8301
    @Test
    public void testEventSetting() {
        selfCheck(T8301.class, "83010010017701840207000c0202010574657374310205746573743268");

        selfCheck(eventSetting());
    }

    public static Message eventSetting() {
        T8301 bean = new T8301();
        bean.setType(T8301.Append);
        bean.addEvent(1, "test");
        bean.addEvent(2, "测试2");
        bean.addEvent(3, "t试2");
        return newMessage(bean);
    }

    // 终端&T0702 0x0001 0x8001
    @Test
    public void testCommonResult() {
        selfCheck(T0001_8001.class, "0001000501770184020701840038810300cd");
    }


    // 终端心跳 0x0002
    @Test
    public void testTerminalHeartbeat() {
        selfCheck(T0002.class, "00020000064762924976042fa7");
    }


    // 文本信息下发 0x8300
    @Test
    public void testTextMessage() {
        selfCheck(T8300.class, "830000050647629242562692015445535480");
    }


    // 摄像头立即拍摄命令 0x8801
    @Test
    public void testCameraShot() {
        selfCheck(cameraShot());
        selfCheck(T8804.class, "8801000c0641629242524a43010001000a0001057d017d017d017d0125");
    }

    public static Message<T8804> cameraShot() {
        T8801 body = new T8801();
        body.setChannelId(125);
        body.setCommand(1);
        body.setTime(125);
        body.setSaveSign(1);
        body.setResolution(125);
        body.setQuality(1);
        body.setBrightness(125);
        body.setContrast(1);
        body.setSaturation(125);
        body.setChroma(1);
        return newMessage(body);
    }
}