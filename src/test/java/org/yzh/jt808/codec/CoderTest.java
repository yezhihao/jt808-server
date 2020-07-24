package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.yzh.framework.commons.lang.RandomUtils;
import org.yzh.framework.commons.transform.JsonUtils;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.basics.TerminalParameter;
import org.yzh.web.jt.codec.JTMessageDecoder;
import org.yzh.web.jt.codec.JTMessageEncoder;
import org.yzh.web.jt.common.ParameterUtils;
import org.yzh.web.jt.t808.*;
import org.yzh.web.jt.t808.position.Attribute;
import org.yzh.web.jt.t808.position.attribute.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

/**
 * JT/T 808协议单元测试类
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class CoderTest {

    private static final JTMessageDecoder decoder = new JTMessageDecoder("org.yzh.web.jt");

    private static final JTMessageEncoder encoder = new JTMessageEncoder("org.yzh.web.jt");

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

    /** 2013版消息头 */
    public static AbstractMessage h2013(AbstractMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        header.setMessageId(type.value()[0]);
        header.setMobileNo("12345678901");
        header.setSerialNo((int) Short.MAX_VALUE);
        header.setEncryption(0);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }

    /** 2019版消息头 */
    public static AbstractMessage h2019(AbstractMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        header.setMessageId(type.value()[0]);
        header.setVersionNo(1);
        header.setMobileNo("17299841738");
        header.setSerialNo(65535);
        header.setEncryption(0);
        header.setVersion(true);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }


    // 位置信息汇报 0x0200
    @Test
    public void testPositionReport() {
//        String hex1 = "0200006a064762924976014d000003500004100201d9f1230743425e000300a6ffff190403133450000000250400070008000000e2403836373733323033383535333838392d627566322d323031392d30342d30332d31332d33342d34392d3735372d70686f6e652d2e6a706700000020000c14cde78d";
//        selfCheck( hex1);
        selfCheck(positionReport());
    }

    public static AbstractMessage positionReport() {
        T0200 bean = new T0200();
        bean.setWarningMark(1024);
        bean.setStatus(2048);
        bean.setLatitude(116307629);
        bean.setLongitude(40058359);
        bean.setAltitude(312);
        bean.setSpeed(3);
        bean.setDirection(99);
        bean.setDateTime("200707192359");
        Map<Integer, Attribute> attributes = new TreeMap();

        attributes.put(Mileage.attributeId, new Mileage(11));
        attributes.put(Oil.attributeId, new Oil(22));
        attributes.put(Speed.attributeId, new Speed(33));
        attributes.put(AlarmEventId.attributeId, new AlarmEventId(44));
        attributes.put(TirePressure.attributeId, new TirePressure((byte) 55, (byte) 55, (byte) 55));
//        attributes.response(CarriageTemperature.attributeId, new CarriageTemperature(2));

        attributes.put(OverSpeedAlarm.attributeId, new OverSpeedAlarm((byte) 66, 66));
        attributes.put(InOutAreaAlarm.attributeId, new InOutAreaAlarm((byte) 77, 77, (byte) 77));
        attributes.put(RouteDriveTimeAlarm.attributeId, new RouteDriveTimeAlarm(88, 88, (byte) 88));

        attributes.put(Signal.attributeId, new Signal(99));
        attributes.put(IoState.attributeId, new IoState(10));
        attributes.put(AnalogQuantity.attributeId, new AnalogQuantity(20));
        attributes.put(SignalStrength.attributeId, new SignalStrength(30));
        attributes.put(GnssCount.attributeId, new GnssCount(40));

        bean.setAttributes(attributes);
        return h2013(bean);
    }


    // 终端注册应答 0x8100
    @Test
    public void testRegisterResult() {
        selfCheck("810000030138014398460000108f09b1");
    }


    // 终端注册 0x0100
    @Test
    public void testRegister() {
//        selfCheck( "010040550100000000009876543210ffff001f0073000000000000000000003400000000000000000000000000000000000000000042534a2d47462d303600000000000000000000000000000000000000000000007465737431323301b2e241383838383838bf");
        selfCheck(register());
    }

    public static AbstractMessage register() {
        T0100 bean = new T0100();
        bean.setProvinceId(31);
        bean.setCityId(115);
        bean.setManufacturerId("4");
        bean.setTerminalType("BSJ-GF-06");
        bean.setTerminalId("test123");
        bean.setLicensePlateColor(1);
        bean.setLicensePlate("测A888888");
        return h2019(bean);
//        return h2013(bean);
    }


    // 查询终端参数应答 0x0104
    @Test
    public void testQuerySettings() {
        selfCheck(querySettings());
    }

    public static AbstractMessage querySettings() {
        T0104 bean = new T0104();
        bean.setSerialNo(104);

        ThreadLocalRandom random = ThreadLocalRandom.current();

        ParameterUtils[] values = ParameterUtils.values();
        for (int i = 0; i < 38; i++) {
            ParameterUtils p = values[i];

            switch (p.type) {
                case BYTE:
                case WORD:
                case DWORD:
                    bean.addTerminalParameter(new TerminalParameter(p.id, random.nextInt()));
                default:
                    bean.addTerminalParameter(new TerminalParameter(p.id, RandomUtils.nextString(16)));
            }
        }

        return h2019(bean);
//        return h2013(bean);
    }


    // 提问下发 0x8302
    @Test
    public void testQuestionMessage() {
        selfCheck("8302001a017701840207001010062c2c2c2c2c2101000331323302000334353603000337383954");

//        selfCheck(questionMessage());
    }

    public static AbstractMessage questionMessage() {
        T8302 bean = new T8302();
        List<T8302.Option> options = new ArrayList();

        bean.buildSign(new int[]{1});
        bean.setContent("123");
        bean.setOptions(options);

        options.add(new T8302.Option(1, "asd1"));
        options.add(new T8302.Option(2, "zxc2"));
        return h2013(bean);
    }


    // 设置电话本 0x8401
    @Test
    public void testPhoneBook() {
        selfCheck("8401002e02000000001500250203020b043138323137333431383032d5c5c8fd010604313233313233c0eecbc4030604313233313233cdf5cee5b1");

        selfCheck(phoneBook());
    }

    public static AbstractMessage phoneBook() {
        T8401 bean = new T8401();
        bean.setType(T8401.Append);
        bean.add(new T8401.Item(2, "18217341802", "张三"));
        bean.add(new T8401.Item(1, "123123", "李四"));
        bean.add(new T8401.Item(3, "123123", "王五"));
        return h2013(bean);
    }


    // 事件设置 0x8301
    @Test
    public void testEventSetting() {
        selfCheck("83010010017701840207000c0202010574657374310205746573743268");

        selfCheck(eventSetting());
    }

    public static AbstractMessage eventSetting() {
        T8301 bean = new T8301();
        bean.setType(T8301.Append);
        bean.addEvent(1, "test");
        bean.addEvent(2, "测试2");
        bean.addEvent(3, "t试2");
        return h2013(bean);
    }

    // 终端&T0702 0x0001 0x8001
    @Test
    public void testCommonResult() {
        selfCheck("0001000501770184020701840038810300cd");
    }


    // 终端心跳 0x0002
    @Test
    public void testTerminalHeartbeat() {
        selfCheck("00020000064762924976042fa7");
    }


    // 文本信息下发 0x8300
    @Test
    public void testTextMessage() {
        selfCheck("830000050647629242562692015445535480");
    }


    // 摄像头立即拍摄命令 0x8801
    @Test
    public void testCameraShot() {
        selfCheck(cameraShot());
        selfCheck("880100050641629242524a43010001000a28");
    }

    public static T8801 cameraShot() {
        T8801 msg = new T8801();
        msg.setChannelId(1);
        msg.setCommand(2);
        msg.setTime(3);
        msg.setSaveSign(1);
        msg.setResolution(4);
        msg.setQuality(5);
        msg.setBrightness(255);
        msg.setContrast(127);
        msg.setSaturation(127);
        msg.setChroma(255);
        h2013(msg);
        return msg;
    }
}