package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.yzh.framework.commons.lang.RandomUtils;
import org.yzh.framework.commons.transform.JsonUtils;
import org.yzh.framework.message.AbstractBody;
import org.yzh.framework.message.AbstractMessage;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.codec.JT808MessageEncoder;
import org.yzh.web.jt808.common.ParameterUtils;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Message;
import org.yzh.web.jt808.dto.basics.TerminalParameter;
import org.yzh.web.jt808.dto.position.Attribute;
import org.yzh.web.jt808.dto.position.attribute.*;

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

    /** 2013版消息头 */
    public static Message m2013(AbstractBody body) {
        Message message = new Message();
        message.setMessageId(999);
        message.setMobileNo("12345678901");
        message.setSerialNo((int) Short.MAX_VALUE);
        message.setEncryption(0);
        message.setReserved(false);
        message.setBody(body);
        return message;
    }

    /** 2019版消息头 */
    public static Message m2019(AbstractBody body) {
        Message message = new Message();
        message.setMessageId(999);
        message.setVersionNo(1);
        message.setMobileNo("9876543210");
        message.setSerialNo(65535);
        message.setEncryption(0);
        message.setVersion(true);
        message.setReserved(false);
        message.setBody(body);
        return message;
    }


    // 位置信息汇报 0x0200
    @Test
    public void testPositionReport() {
//        String hex1 = "0200006a064762924976014d000003500004100201d9f1230743425e000300a6ffff190403133450000000250400070008000000e2403836373733323033383535333838392d627566322d323031392d30342d30332d31332d33342d34392d3735372d70686f6e652d2e6a706700000020000c14cde78d";
//        selfCheck(T0200.class, hex1);
        selfCheck(positionReport());
    }

    public static Message positionReport() {
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
//        attributes.put(CarriageTemperature.attributeId, new CarriageTemperature(2));

        attributes.put(OverSpeedAlarm.attributeId, new OverSpeedAlarm((byte) 66, 66));
        attributes.put(InOutAreaAlarm.attributeId, new InOutAreaAlarm((byte) 77, 77, (byte) 77));
        attributes.put(RouteDriveTimeAlarm.attributeId, new RouteDriveTimeAlarm(88, 88, (byte) 88));

        attributes.put(Signal.attributeId, new Signal(99));
        attributes.put(IoState.attributeId, new IoState(10));
        attributes.put(AnalogQuantity.attributeId, new AnalogQuantity(20));
        attributes.put(SignalStrength.attributeId, new SignalStrength(30));
        attributes.put(GnssCount.attributeId, new GnssCount(40));

        bean.setAttributes(attributes);
        return m2013(bean);
    }


    // 终端注册应答 0x8100
    @Test
    public void testRegisterResult() {
        selfCheck(T8100.class, "8100000306476292482425b4000201cd");
    }


    // 终端注册 0x0100
    @Test
    public void testRegister() {
//        selfCheck(T0100.class, "0100002e064762924824000200000000484f4f5000bfb5b4ef562d31000000000000000000000000000000015a0d5dff02bba64450393939370002");
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
        return m2019(bean);
//        return m2013(bean);
    }


    // 查询终端参数应答 0x0104
    @Test
    public void testQuerySettings() {
        selfCheck(querySettings());
    }

    public static Message querySettings() {
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
        return m2019(bean);
//        return m2013(bean);
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
        return m2013(bean);
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
        return m2013(bean);
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
        return m2013(bean);
    }

    // 终端&T0702 0x0001 0x8001
    @Test
    public void testCommonResult() {
        selfCheck(T0001.class, "0001000501770184020701840038810300cd");
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
        selfCheck(T8804.class, "880100050641629242524a43010001000a28");
    }

    public static Message<T8804> cameraShot() {
        T8801 body = new T8801();
        body.setChannelId(1);
        body.setCommand(2);
        body.setTime(3);
        body.setSaveSign(1);
        body.setResolution(4);
        body.setQuality(5);
        body.setBrightness(255);
        body.setContrast(127);
        body.setSaturation(127);
        body.setChroma(255);
        return m2013(body);
    }
}