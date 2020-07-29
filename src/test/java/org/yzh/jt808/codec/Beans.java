package org.yzh.jt808.codec;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.RawMessage;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.TerminalParameter;
import org.yzh.protocol.commons.Action;
import org.yzh.protocol.commons.ParameterUtils;
import org.yzh.protocol.commons.ShapeAction;
import org.yzh.protocol.commons.additional.Attribute;
import org.yzh.protocol.commons.additional.attribute.*;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.RandomUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * JT/T 808协议单元测试数据
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class Beans {

    /** 2013版消息头 */
    public static AbstractMessage H2013(AbstractMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        if (type != null)
            header.setMessageId(type.value()[0]);
        header.setMobileNo("12345678901");
        header.setSerialNo((int) Short.MAX_VALUE);
        header.setEncryption(0);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }

    /** 2019版消息头 */
    public static AbstractMessage H2019(AbstractMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        if (type != null)
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

    //平台RSA公钥|终端RSA公钥
    public static T0A00_8A00 T0A00_8A00() {
        T0A00_8A00 bean = new T0A00_8A00();
        bean.setE(999);
        bean.setN(new byte[128]);
        return bean;
    }

    //终端通用应答|平台通用应答
    public static T0001 T0001() {
        T0001 bean = new T0001();
        bean.setSerialNo(123);
        bean.setReplyId(456);
        bean.setResultCode(3);
        return bean;
    }

    //终端心跳
    public static RawMessage T0002() {
        RawMessage bean = new RawMessage();
        return bean;
    }

    //终端注册
    public static T0100 T0100() {
        T0100 bean = new T0100();
        bean.setProvinceId(31);
        bean.setCityId(115);
        bean.setManufacturerId("4");
        bean.setTerminalType("BSJ-GF-06");
        bean.setTerminalId("test123");
        bean.setLicensePlateColor(1);
        bean.setLicensePlate("测A888888");
        return bean;
    }

    //终端鉴权
    public static T0102 T0102() {
        T0102 bean = new T0102();
        bean.setToken("abc@123&456");
        return bean;
    }

    //查询终端参数应答
    public static T0104 T0104() {
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
                    bean.addItem(new TerminalParameter(p.id, random.nextInt()));
                default:
                    bean.addItem(new TerminalParameter(p.id, RandomUtils.nextString(16)));
            }
        }
        return bean;
    }

    //查询终端属性应答
    public static T0107 T0107() {
        T0107 bean = new T0107();
        bean.setType(127);
        bean.setManufacturerId("2D_ANEr");
        bean.setTerminalType("RlkgWwRKr");
        bean.setTerminalId("5kw_noL");
        bean.setSimId("12812121212");
        bean.setFirmwareVersion("DDhZr9hy9");
        bean.setHardwareVersion("ohrnEniST");
        bean.setGnssAttribute(127);
        bean.setNetworkAttribute(127);
        return bean;
    }

    //终端升级结果通知
    public static T0108 T0108() {
        T0108 bean = new T0108();
        bean.setType(T0108.Beidou);
        bean.setResult(2);
        return bean;
    }

    //位置信息汇报
    public static T0200 T0200() {
        T0200 bean = new T0200();
        bean.setWarningMark(1024);
        bean.setStatus(2048);
        bean.setLatitude(116307629);
        bean.setLongitude(40058359);
        bean.setAltitude(312);
        bean.setSpeed(3);
        bean.setDirection(99);
        bean.setDateTime("200707192359");
        return bean;
    }

    //位置信息汇报
    public static T0200 T0200Attributes() {
        T0200 bean = T0200();
        Map<Integer, Attribute> attributes = new TreeMap();
        attributes.put(Mileage.attributeId, new Mileage(11));
        attributes.put(Oil.attributeId, new Oil(22));
        attributes.put(Speed.attributeId, new Speed(33));
        attributes.put(AlarmEventId.attributeId, new AlarmEventId(44));
        attributes.put(TirePressure.attributeId, new TirePressure((byte) 55, (byte) 55, (byte) 55));
        attributes.put(CarriageTemperature.attributeId, new CarriageTemperature(2));
        attributes.put(OverSpeedAlarm.attributeId, new OverSpeedAlarm((byte) 66, 66));
        attributes.put(InOutAreaAlarm.attributeId, new InOutAreaAlarm((byte) 77, 77, (byte) 77));
        attributes.put(RouteDriveTimeAlarm.attributeId, new RouteDriveTimeAlarm(88, 88, (byte) 88));
        attributes.put(Signal.attributeId, new Signal(99));
        attributes.put(IoState.attributeId, new IoState(10));
        attributes.put(AnalogQuantity.attributeId, new AnalogQuantity(20));
        attributes.put(SignalStrength.attributeId, new SignalStrength(30));
        attributes.put(GnssCount.attributeId, new GnssCount(40));
        bean.setAttributes(attributes);
        return bean;
    }

    //位置信息查询应答|车辆控制应答
    public static T0201_0500 T0201_0500() {
        T0201_0500 bean = new T0201_0500();
        bean.setSerialNo(26722);
        bean.setWarningMark(10842);
        bean.setStatus(29736);
        bean.setLatitude(41957);
        bean.setLongitude(56143);
        bean.setAltitude(48243);
        bean.setSpeed(10001);
        bean.setDirection(300);
        bean.setDateTime("2005121212");
        List<BytesAttribute> attributes = new ArrayList<>();
        attributes.add(new BytesAttribute(1, "123".getBytes()));
        bean.setBytesAttributes(attributes);
        return bean;
    }

    //事件报告
    public static T0301 T0301() {
        T0301 bean = new T0301();
        bean.setEventId(255);
        return bean;
    }

    //提问应答
    public static T0302 T0302() {
        T0302 bean = new T0302();
        bean.setSerialNo(61252);
        bean.setAnswerId(127);
        return bean;
    }

    //信息点播_取消
    public static T0303 T0303() {
        T0303 bean = new T0303();
        bean.setType(255);
        bean.setAction(0);
        return bean;
    }

    //驾驶员身份信息采集上报
    public static T0702 T0702() {
        T0702 bean = new T0702();
        bean.setStatus(2);
        bean.setDateTime("200721183000");
        bean.setCardStatus(0);
        bean.setName("张三");
        bean.setLicenseNo("1234567890123");
        bean.setInstitution("中华人民共和国道路运输从业人员从业资格证");
        bean.setLicenseValidPeriod("20190630");
        return bean;
    }

    //定位数据批量上传
    public static T0704 T0704() {
        T0704 bean = new T0704();
        bean.setType(1);
        List<T0704.Item> item = new ArrayList<>();
        //TODO 该协议服务端仅能够解析，未实现对象length的自动设置
        item.add(new T0704.Item(28, T0200()));
        item.add(new T0704.Item(28, T0200()));
        item.add(new T0704.Item(28, T0200()));
        bean.setItems(item);
        return bean;
    }

    //CAN总线数据上传
    public static T0705 T0705() {
        T0705 bean = new T0705();
        bean.setDateTime("235959");
        List<T0705.Item> items = new ArrayList<>();
        items.add(new T0705.Item(new byte[]{1, 2, 3, 4}, new byte[]{1, 2, 3, 4, 5, 6, 7, 8}));
        items.add(new T0705.Item(new byte[]{1, 2, 3, 4}, new byte[]{1, 2, 3, 4, 5, 6, 7, 8}));
        items.add(new T0705.Item(new byte[]{1, 2, 3, 4}, new byte[]{1, 2, 3, 4, 5, 6, 7, 8}));
        bean.setItems(items);
        return bean;
    }

    //多媒体事件信息上传
    public static T0800 T0800() {
        T0800 bean = new T0800();
        bean.setId(123);
        bean.setType(0);
        bean.setFormat(0);
        bean.setEvent(7);
        bean.setChannelId(1);
        return bean;
    }

    //多媒体数据上传
    public static T0801 T0801() {
        T0801 bean = new T0801();
        bean.setId(123);
        bean.setType(1);
        bean.setFormat(2);
        bean.setEvent(1);
        bean.setChannelId(2);
        bean.setPosition(T0200());
        bean.setPacket(new byte[]{13, 123, 13, 123, 123});
        return bean;
    }

    //存储多媒体数据检索应答
    public static T0802 T0802() {
        T0802 bean = new T0802();
        bean.setSerialNo(123);
        List<T0802.Item> items = new ArrayList<>();
        items.add(new T0802.Item(1, 1, 1, 1, T0200()));
        items.add(new T0802.Item(2, 1, 1, 1, T0200()));
        items.add(new T0802.Item(3, 1, 1, 1, T0200()));
        items.add(new T0802.Item(4, 1, 1, 1, T0200()));
        bean.setItems(items);
        return bean;
    }

    //摄像头立即拍摄命令应答
    public static T0805 T0805() {
        T0805 bean = new T0805();
        bean.setSerialNo(62656);
        bean.setResult(0);
        bean.setItems(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //数据压缩上报
    public static T0901 T0901() {
        T0901 bean = new T0901();
        bean.setBody(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //补传分包请求
    public static T8003 T8003() {
        T8003 bean = new T8003();
        bean.setSerialNo(4249);
        bean.setItems(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //终端注册应答
    public static T8100 T8100() {
        T8100 bean = new T8100();
        bean.setSerialNo(38668);
        bean.setResultCode(T8100.Success);
        bean.setToken("chwD0SE1fchwD0SE1fchwD0SE1f");
        return bean;
    }

    //设置终端参数
    public static T8103 T8103() {
        T8103 bean = new T8103();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        ParameterUtils[] values = ParameterUtils.values();
        for (int i = 0; i < 38; i++) {
            ParameterUtils p = values[i];
            switch (p.type) {
                case BYTE:
                case WORD:
                case DWORD:
                    bean.addItem(new TerminalParameter(p.id, random.nextInt()));
                default:
                    bean.addItem(new TerminalParameter(p.id, RandomUtils.nextString(16)));
            }
        }
        return bean;
    }

    //终端控制
    public static T8105 T8105() {
        T8105 bean = new T8105();
        bean.setCommand(123);
        bean.setParameter("as;123;zxc;123;");
        return bean;
    }

    //查询指定终端参数
    public static T8106 T8106() {
        T8106 bean = new T8106();
        bean.setIds(new byte[]{1, 3, 5, 7, 9, 127});
        return bean;
    }

    //下发终端升级包
    public static T8108 T8108() {
        T8108 bean = new T8108();
        bean.setType(T8108.Beidou);
        bean.setManufacturerId("asd");
        bean.setVersion("1.1.12");
        bean.setPacket(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //临时位置跟踪控制
    public static T8202 T8202() {
        T8202 bean = new T8202();
        bean.setInterval(5);
        bean.setValidityPeriod(600);
        return bean;
    }

    //人工确认报警消息
    public static T8203 T8203() {
        T8203 bean = new T8203();
        bean.setSerialNo(123);
        bean.setType(40286);
        return bean;
    }

    //提问下发
    public static T8302 T8302() {
        T8302 bean = new T8302();
        List<T8302.Option> options = new ArrayList();
        bean.buildSign(new int[]{1});
        bean.setContent("123");
        bean.setOptions(options);
        options.add(new T8302.Option(1, "asd1"));
        options.add(new T8302.Option(2, "zxc2"));
        return bean;
    }

    //信息点播菜单设置
    public static T8303 T8303() {
        T8303 bean = new T8303();
        bean.setType(Action.Append);
        int i = 0;
        bean.addItem(i++, "军事");
        bean.addItem(i++, "国内");
        bean.addItem(i++, "国际");
        bean.addItem(i++, "股票");
        bean.addItem(i++, "基金");
        bean.addItem(i++, "外汇");
        bean.addItem(i++, "体育");
        bean.addItem(i++, "娱乐");
        bean.addItem(i++, "汽车");
        return bean;
    }

    //信息服务
    public static T8304 T8304() {
        T8304 bean = new T8304();
        bean.setType(123);
        bean.setContent("美上将:中国导弹可将关岛\"从地图上移除\"");
        return bean;
    }

    //电话回拨
    public static T8400 T8400() {
        T8400 bean = new T8400();
        bean.setType(T8400.Normal);
        bean.setContent("1234567890123");
        return bean;
    }

    //设置电话本
    public static T8401 T8401() {
        T8401 bean = new T8401();
        bean.setType(Action.Append);
        bean.add(new T8401.Item(2, "18217341802", "张三"));
        bean.add(new T8401.Item(1, "123123", "李四"));
        bean.add(new T8401.Item(3, "123123", "王五"));
        return bean;
    }

    //车辆控制
    public static T8500 T8500() {
        T8500 bean = new T8500();
        bean.setSign(123);
        return bean;
    }

    //设置圆形区域
    public static T8600 T8600() {
        T8600 bean = new T8600();
        bean.setAction(ShapeAction.Modify);
        List<T8600.Item> items = new ArrayList<>();
        items.add(new T8600.Item(1, 2, 123123, 112312, 123, "200726000000", "200726232359", 200, 30));
        items.add(new T8600.Item(2, 2, 123123, 112312, 123, "200726000000", "200726232359", 200, 30));
        items.add(new T8600.Item(3, 2, 123123, 112312, 123, "200726000000", "200726232359", 200, 30));
        items.add(new T8600.Item(4, 2, 123123, 112312, 123, "200726000000", "200726232359", 200, 30));
        items.add(new T8600.Item(5, 2, 123123, 112312, 123, "200726000000", "200726232359", 200, 30));
        bean.setItems(items);
        return bean;
    }

    //删除圆形区域|删除矩形区域|删除多边形区域
    public static T8601 T8601() {
        T8601 bean = new T8601();
        bean.addItem(1);
        bean.addItem(2);
        bean.addItem(3);
        bean.addItem(65535);
        return bean;
    }

    //设置矩形区域
    public static T8602 T8602() {
        T8602 bean = new T8602();
        bean.setAction(ShapeAction.Update);
        List<T8602.Item> items = new ArrayList<>();
        items.add(new T8602.Item(1, 2, 123123, 112312, 123123, 112312, "200726000000", "200726232359", 200, 30));
        items.add(new T8602.Item(2, 2, 123123, 112312, 123123, 112312, "200726000000", "200726232359", 200, 30));
        items.add(new T8602.Item(3, 2, 123123, 112312, 123123, 112312, "200726000000", "200726232359", 200, 30));
        items.add(new T8602.Item(4, 2, 123123, 112312, 123123, 112312, "200726000000", "200726232359", 200, 30));
        items.add(new T8602.Item(5, 2, 123123, 112312, 123123, 112312, "200726000000", "200726232359", 200, 30));
        bean.setItems(items);
        return bean;
    }

    //设置多边形区域
    public static T8604 T8604() {
        T8604 bean = new T8604();
        bean.setId(3);
        bean.setAttribute(2);
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        bean.setMaxSpeed(123);
        bean.setDuration(60);
        List<T8604.Coordinate> items = new ArrayList<>();
        items.add(new T8604.Coordinate(123, 345));
        items.add(new T8604.Coordinate(123, 345));
        items.add(new T8604.Coordinate(123, 345));
        items.add(new T8604.Coordinate(123, 345));
        items.add(new T8604.Coordinate(123, 345));
        bean.setItems(items);
        return bean;
    }

    //设置路线
    public static T8606 T8606() {
        T8606 bean = new T8606();
        bean.setId(59397);
        bean.setAttribute(65195);
        bean.setStartTime("201231235959");
        bean.setEndTime("201231235959");
        List<T8606.Point> item = new ArrayList<>();
        item.add(new T8606.Point(1, 1, 123, 123, 1, 2, 3, 4, 5, 6));
        item.add(new T8606.Point(2, 1, 123, 123, 1, 2, 3, 4, 5, 6));
        item.add(new T8606.Point(3, 1, 123, 123, 1, 2, 3, 4, 5, 6));
        item.add(new T8606.Point(4, 1, 123, 123, 1, 2, 3, 4, 5, 6));
        bean.setItem(item);
        return bean;
    }

    //多媒体数据上传应答
    public static T8800 T8800() {
        T8800 bean = new T8800();
        bean.setMediaId(49503);
        bean.setItems(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //摄像头立即拍摄命令
    public static T8801 T8801() {
        T8801 bean = new T8801();
        bean.setChannelId(1);
        bean.setCommand(2);
        bean.setTime(3);
        bean.setSaveSign(1);
        bean.setResolution(4);
        bean.setQuality(5);
        bean.setBrightness(255);
        bean.setContrast(127);
        bean.setSaturation(127);
        bean.setChroma(255);
        return bean;
    }

    //存储多媒体数据检索
    public static T8802 T8802() {
        T8802 bean = new T8802();
        bean.setType(0);
        bean.setChannelId(1);
        bean.setEvent(3);
        bean.setEndTime("201201235859");
        bean.setStartTime("201201235859");
        return bean;
    }

    //存储多媒体数据上传命令
    public static T8803 T8803() {
        T8803 bean = new T8803();
        bean.setType(0);
        bean.setChannelId(1);
        bean.setEvent(3);
        bean.setEndTime("2012121212");
        bean.setStartTime("2012121212");
        bean.setDeleteSign(0);
        return bean;
    }

    //录音开始命令
    public static T8804 T8804() {
        T8804 bean = new T8804();
        bean.setCommand(0x01);
        bean.setTime(6328);
        bean.setSaveSign(1);
        bean.setAudioSampleRate(0);
        return bean;
    }

    //单条存储多媒体数据检索上传命令
    public static T8805 T8805() {
        T8805 bean = new T8805();
        bean.setId(28410);
        bean.setDeleteSign(1);
        return bean;
    }

    //数据上行透传|数据下行透传
    public static T8900_0900 T8900_0900() {
        T8900_0900 bean = new T8900_0900();
        bean.setType(T8900_0900.GNSSLocation);
        bean.setContent("asdzxcqwe12335".getBytes());
        return bean;
    }

    //事件设置
    public static T8301 T8301() {
        T8301 bean = new T8301();
        bean.setType(Action.Append);
        bean.addEvent(1, "test");
        bean.addEvent(2, "测试2");
        bean.addEvent(3, "t试2");
        return bean;
    }

    public static T8300 T8300() {
        T8300 bean = new T8300();
        bean.setSign(9);
        bean.setContent("测试123@456#abc!...结束");
        return bean;
    }


    public static void main(String[] args) throws Exception {
        List<Class<?>> classList = ClassUtils.getClassList("org.yzh.web.jt.t808");
        for (Class<?> aClass : classList) {
            String simpleName = aClass.getSimpleName();
            BeanInfo beanInfo = Introspector.getBeanInfo(aClass);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            String ps = "";
            for (PropertyDescriptor p : propertyDescriptors) {
                Method writeMethod = p.getWriteMethod();
                if (writeMethod == null)
                    continue;
                Object val = genBaseData(p.getPropertyType());
                String name = writeMethod.getName();
                ps += "\t\tbean." + name + "(" + val + ");\n";
            }
            System.out.println("    //xxxx\n" +
                    "    public static " + simpleName + " " + simpleName + "() {\n" +
                    "        " + simpleName + " bean = new " + simpleName + "();\n" +
                    ps +
                    "        return bean;\n" +
                    "    }");
        }
    }

    private static <T> Object genBaseData(Class<T> clazz) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(Boolean.TYPE))
            return random.nextBoolean();
        if (clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(Byte.TYPE))
            return (byte) random.nextInt(0, 65536);
        if (clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(Short.TYPE))
            return (short) random.nextInt(0, 65536);
        if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(Integer.TYPE))
            return random.nextInt(0, 65536);
        if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(Long.TYPE))
            return random.nextLong(0, 65536);
        if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(Float.TYPE))
            return random.nextFloat();
        if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(Double.TYPE))
            return random.nextDouble();
        if (clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(Character.TYPE))
            return RandomUtils.nextString(1).charAt(0);
        if (clazz.isAssignableFrom(String.class))
            return "\"" + RandomUtils.nextString(9) + "\"";
        if (clazz.isAssignableFrom(Date.class))
            return new Date();
        return null;
    }
}