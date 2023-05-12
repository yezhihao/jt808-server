package org.yzh.protocol;

import io.github.yezhihao.protostar.util.KeyValuePair;
import io.netty.buffer.Unpooled;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Action;
import org.yzh.protocol.commons.ShapeAction;
import org.yzh.protocol.commons.transform.AttributeKey;
import org.yzh.protocol.commons.transform.attribute.*;
import org.yzh.protocol.commons.transform.parameter.*;
import org.yzh.protocol.commons.transform.passthrough.PeripheralSystem;
import org.yzh.protocol.jsatl12.T1210;
import org.yzh.protocol.jsatl12.T1211;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.jsatl12.T9212;
import org.yzh.protocol.t1078.*;
import org.yzh.protocol.t808.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * JT/T 808协议单元测试数据
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class JT808Beans {

    public static final String ALARM_NO = "ad72131579e54be0b0f737cfc72c5db8";
    public static final String DEVICE_ID = "09876543210987654321";
    public static final String STR_TIME = "200707192359";
    public static final LocalDateTime TIME = LocalDateTime.of(2020, 7, 7, 19, 23, 59);
    public static final LocalDateTime START_TIME = LocalDateTime.of(2020, 7, 26, 0, 0, 0);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2020, 7, 26, 23, 23, 59);

    /** 2013版消息头 */
    public static <T extends JTMessage> T H2013(T message) {
        int messageId = message.reflectMessageId();
        if (messageId != 0) message.setMessageId(messageId);
        message.setClientId("123456789012");
        message.setSerialNo(1);
        message.setEncryption(0);
        message.setReserved(false);
        return message;
    }

    /** 2019版消息头 */
    public static <T extends JTMessage> T H2019(T message) {
        int messageId = message.reflectMessageId();
        if (messageId != 0) message.setMessageId(messageId);
        message.setProtocolVersion(1);
        message.setClientId("12345678901234567890");
        message.setSerialNo(65535);
        message.setEncryption(0);
        message.setVersion(true);
        message.setReserved(false);
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
        bean.setResponseSerialNo(123);
        bean.setResponseMessageId(456);
        bean.setResultCode(3);
        return bean;
    }

    //终端注册
    public static T0100 T0100() {
        T0100 bean = new T0100();
        bean.setProvinceId(31);
        bean.setCityId(115);
        bean.setMakerId("yzh");
        bean.setDeviceModel("www.jtt808.cn");
        bean.setDeviceId(DEVICE_ID);
        bean.setPlateColor(1);
        bean.setPlateNo("测A888888");
        return bean;
    }

    //终端鉴权
    public static T0102 T0102_2013() {
        T0102 bean = new T0102();
        bean.setToken("pmYGzGukO8K4Z5lpIOTg8dqb3eprYaHBbXSPLtdbyG8=");
        return bean;
    }

    public static T0102 T0102_2019() {
        T0102 bean = new T0102();
        bean.setToken("pmYGzGukO8K4Z5lpIOTg8dqb3eprYaHBbXSPLtdbyG8=");
        bean.setImei("123456789012345");
        bean.setSoftwareVersion("www.jtt808.cn");
        return bean;
    }

    //查询终端参数应答
    public static T0104 T0104() {
        T0104 bean = new T0104();
        bean.setResponseSerialNo(104);
        bean.setParameters(parameters());
        return bean;
    }

    //终端参数列表
    public static Map<Integer, Object> parameters() {
        ParamVideo paramVideo = new ParamVideo();
        paramVideo.setRealtimeEncode((byte) 1);
        paramVideo.setRealtimeResolution((byte) 1);
        paramVideo.setRealtimeFrameInterval((byte) 1);
        paramVideo.setRealtimeFrameRate((byte) 1);
        paramVideo.setRealtimeBitRate((byte) 1);
        paramVideo.setStorageEncode((byte) 2);
        paramVideo.setStorageResolution((byte) 2);
        paramVideo.setStorageFrameInterval((byte) 2);
        paramVideo.setStorageFrameRate((byte) 2);
        paramVideo.setStorageBitRate((byte) 2);
        paramVideo.setOdsConfig((byte) 3);
        paramVideo.setAudioEnable((byte) 3);

        Map<Integer, Object> parameters = new TreeMap<>();
        parameters.put(ParamVideo.key, paramVideo);
        parameters.put(ParamADAS.key, new ParamADAS());
        parameters.put(ParamBSD.key, new ParamBSD());
        parameters.put(ParamSleepWake.key, new ParamSleepWake());
        parameters.put(ParamVideoSpecialAlarm.key, new ParamVideoSpecialAlarm());
        parameters.put(ParamImageIdentifyAlarm.key, new ParamImageIdentifyAlarm());
        return parameters;
    }

    //查询终端属性应答
    public static T0107 T0107() {
        T0107 bean = new T0107();
        bean.setDeviceType(127);
        bean.setMakerId("yzh");
        bean.setDeviceModel("www.jtt808.cn");
        bean.setDeviceId(DEVICE_ID);
        bean.setIccid("12345678901234567890");
        bean.setFirmwareVersion("www.jtt808.cn");
        bean.setHardwareVersion("www.jtt808.cn");
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
        bean.setWarnBit(1024);
        bean.setStatusBit(2048);
        bean.setLatitude(116307629);
        bean.setLongitude(40058359);
        bean.setAltitude(312);
        bean.setSpeed(3);
        bean.setDirection(99);
        bean.setDeviceTime(TIME);
        return bean;
    }

    //位置信息汇报
    public static T0200 T0200_() {
        T0200 bean = new T0200();
        bean.setWarnBit(1024 * 2);
        bean.setStatusBit(2048 * 2);
        bean.setLatitude(116307629 * 2);
        bean.setLongitude(40058359 * 2);
        bean.setAltitude(312 * 2);
        bean.setSpeed(3 * 2);
        bean.setDirection(99 * 2);
        bean.setDeviceTime(TIME);
        return bean;
    }

    //位置信息汇报
    public static T0200 T0200Attributes() {
        T0200 bean = T0200();
        Map<Integer, Object> attributes = new TreeMap<>();
        attributes.put(AttributeKey.Mileage, 11);
        attributes.put(AttributeKey.Fuel, 22);
        attributes.put(AttributeKey.Speed, 33);
        attributes.put(AttributeKey.AlarmEventId, 44);
        attributes.put(AttributeKey.TirePressure, new TirePressure(new byte[]{55, 55, 55}));
        attributes.put(AttributeKey.CarriageTemperature, 2);

        attributes.put(AttributeKey.OverSpeedAlarm, new OverSpeedAlarm((byte) 66, 66));
        attributes.put(AttributeKey.InOutAreaAlarm, new InOutAreaAlarm((byte) 77, 77, (byte) 77));
        attributes.put(AttributeKey.RouteDriveTimeAlarm, new RouteDriveTimeAlarm(88, 88, (byte) 88));

        attributes.put(AttributeKey.Signal, 99);
        attributes.put(AttributeKey.IoState, 10);
        attributes.put(AttributeKey.AnalogQuantity, 20);
        attributes.put(AttributeKey.SignalStrength, 30);
        attributes.put(AttributeKey.GnssCount, 40);
        bean.setAttributes(attributes);
        return bean;
    }

    //位置信息汇报
    public static T0200 T0200JSATL12() {
        AlarmADAS alarmADAS = new AlarmADAS();
        alarmADAS.setId(64);
        alarmADAS.setState(1);
        alarmADAS.setType(1);
        alarmADAS.setLevel(1);
        alarmADAS.setFrontSpeed(10);
        alarmADAS.setFrontDistance(10);
        alarmADAS.setDeviateType(1);
        alarmADAS.setRoadSign(1);
        alarmADAS.setRoadSignValue(10);
        alarmADAS.setSpeed(10);
        alarmADAS.setAltitude(100);
        alarmADAS.setLatitude(111111);
        alarmADAS.setLongitude(222222);
        alarmADAS.setAlarmTime(TIME);
        alarmADAS.setStatusBit(1);
        alarmADAS.setDeviceId(DEVICE_ID);
        alarmADAS.setDateTime(TIME);
        alarmADAS.setSequenceNo(1);
        alarmADAS.setFileTotal(1);
        alarmADAS.setReserved(1);

        AlarmDSM alarmDSM = new AlarmDSM();
        alarmDSM.setId(65);
        alarmDSM.setState(2);
        alarmDSM.setType(2);
        alarmDSM.setLevel(2);
        alarmDSM.setFatigueDegree(20);
        alarmDSM.setReserves(20);
        alarmDSM.setSpeed(20);
        alarmDSM.setAltitude(200);
        alarmDSM.setLatitude(333333);
        alarmDSM.setLongitude(444444);
        alarmDSM.setAlarmTime(TIME);
        alarmDSM.setStatusBit(2);
        alarmDSM.setDeviceId(DEVICE_ID);
        alarmDSM.setDateTime(TIME);
        alarmDSM.setSequenceNo(2);
        alarmDSM.setFileTotal(2);
        alarmDSM.setReserved(2);

        AlarmTPMS alarmTPMS = new AlarmTPMS();
        alarmTPMS.setId(66);
        alarmTPMS.setState(3);
        alarmTPMS.setSpeed(30);
        alarmTPMS.setAltitude(300);
        alarmTPMS.setLatitude(555555);
        alarmTPMS.setLongitude(666666);
        alarmTPMS.setAlarmTime(TIME);
        alarmTPMS.setStatusBit(3);
        alarmTPMS.setDeviceId(DEVICE_ID);
        alarmTPMS.setDateTime(TIME);
        alarmTPMS.setSequenceNo(3);
        alarmTPMS.setFileTotal(3);
        alarmTPMS.setReserved(3);

        AlarmBSD alarmBSD = new AlarmBSD();
        alarmBSD.setId(67);
        alarmBSD.setState(4);
        alarmBSD.setType(4);
        alarmBSD.setSpeed(40);
        alarmBSD.setAltitude(400);
        alarmBSD.setLatitude(777777);
        alarmBSD.setLongitude(888888);
        alarmBSD.setAlarmTime(TIME);
        alarmBSD.setStatusBit(4);
        alarmBSD.setDeviceId(DEVICE_ID);
        alarmBSD.setDateTime(TIME);
        alarmBSD.setSequenceNo(4);
        alarmBSD.setFileTotal(4);
        alarmBSD.setReserved(4);


        T0200 bean = T0200();
        Map<Integer, Object> attributes = new TreeMap<>();
        attributes.put(AlarmADAS.key, alarmADAS);
        attributes.put(AlarmDSM.key, alarmDSM);
        attributes.put(AlarmTPMS.key, alarmTPMS);
        attributes.put(AlarmBSD.key, alarmBSD);

        bean.setAttributes(attributes);
        return bean;
    }

    //位置信息查询应答|车辆控制应答
    public static T0201_0500 T0201_0500() {
        T0201_0500 bean = new T0201_0500();
        bean.setResponseSerialNo(26722);
        bean.setWarnBit(10842);
        bean.setStatusBit(29736);
        bean.setLatitude(41957);
        bean.setLongitude(56143);
        bean.setAltitude(48243);
        bean.setSpeed(10001);
        bean.setDirection(300);
        bean.setDeviceTime(TIME);
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
        bean.setResponseSerialNo(61252);
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
        List<T0200> item = new ArrayList<>(4);
        item.add(T0200());
        item.add(T0200_());
        item.add(T0200());
        item.add(T0200_());
        bean.setItems(item);
        return bean;
    }

    //CAN总线数据上传
    public static T0705 T0705() {
        T0705 bean = new T0705();
        bean.setDateTime("235959");
        List<T0705.Item> items = new ArrayList<>(3);
        items.add(new T0705.Item(16909060, new byte[]{1, 2, 3, 4, 5, 6, 7, 8}));
        items.add(new T0705.Item(16909060, new byte[]{1, 2, 3, 4, 5, 6, 7, 8}));
        items.add(new T0705.Item(16909060, new byte[]{1, 2, 3, 4, 5, 6, 7, 8}));
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
        bean.setLocation(T0200());
        bean.setPacket(Unpooled.wrappedBuffer(new byte[]{13, 123, 13, 123, 123}));
        return bean;
    }

    //存储多媒体数据检索应答
    public static T0802 T0802() {
        T0802 bean = new T0802();
        bean.setResponseSerialNo(123);
        List<T0802.Item> items = new ArrayList<>(4);
        items.add(new T0802.Item(1, 1, 1, 1, T0200()));
        items.add(new T0802.Item(2, 1, 1, 1, T0200_()));
        items.add(new T0802.Item(3, 1, 1, 1, T0200()));
        items.add(new T0802.Item(4, 1, 1, 1, T0200_()));
        bean.setItems(items);
        return bean;
    }

    //摄像头立即拍摄命令应答
    public static T0805 T0805() {
        T0805 bean = new T0805();
        bean.setResponseSerialNo(62656);
        bean.setResult(0);
        bean.setId(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
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
        bean.setResponseSerialNo(4249);
        bean.setId(new short[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //终端注册应答
    public static T8100 T8100() {
        T8100 bean = new T8100();
        bean.setResponseSerialNo(38668);
        bean.setResultCode(T8100.Success);
        bean.setToken("chwD0SE1fchwD0SE1fchwD0SE1f");
        return bean;
    }

    //设置终端参数
    public static T8103 T8103() {
        T8103 bean = new T8103();
        bean.setParameters(parameters());
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
        return new T8106(1, 3, 5, 7, 9, 127);
    }

    //下发终端升级包
    public static T8108 T8108() {
        T8108 bean = new T8108();
        bean.setType(T8108.Beidou);
        bean.setMakerId("yzh");
        bean.setVersion("www.jtt808.cn");
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
        bean.setResponseSerialNo(123);
        bean.setType(40286);
        return bean;
    }

    //提问下发
    public static T8302 T8302() {
        List<T8302.Option> options = new ArrayList<>(2);
        options.add(new T8302.Option(1, "www.jtt808.cn"));
        options.add(new T8302.Option(2, "www.jtt808.cn"));
        T8302 bean = new T8302("123", 1);
        bean.setOptions(options);
        return bean;
    }

    //信息点播菜单设置
    public static T8303 T8303() {
        T8303 bean = new T8303();
        bean.setType(Action.Append);
        int i = 0;
        bean.addInfo(++i, "军事");
        bean.addInfo(++i, "国内");
        bean.addInfo(++i, "国际");
        bean.addInfo(++i, "股票");
        bean.addInfo(++i, "基金");
        bean.addInfo(++i, "外汇");
        bean.addInfo(++i, "体育");
        bean.addInfo(++i, "娱乐");
        bean.addInfo(++i, "汽车");
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
        bean.setPhoneNumber("1234567890123");
        return bean;
    }

    //设置电话本
    public static T8401 T8401() {
        T8401 bean = new T8401();
        bean.setType(Action.Append);
        bean.addContact(new T8401.Contact(2, "12345678901", "张三"));
        bean.addContact(new T8401.Contact(1, "123123", "李四"));
        bean.addContact(new T8401.Contact(3, "123123", "王五"));
        return bean;
    }

    //车辆控制
    public static T8500 T8500() {
        T8500 bean = new T8500();
        bean.setType(123);
        return bean;
    }

    //设置圆形区域
    public static T8600 T8600(int version) {
        T8600 bean = new T8600();
        bean.setAction(ShapeAction.Modify);
        List<T8600.Circle> items = new ArrayList<>(5);
        if (version == 2013) {
            items.add(new T8600.Circle(1, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30));
            items.add(new T8600.Circle(2, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30));
            items.add(new T8600.Circle(3, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30));
            items.add(new T8600.Circle(4, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30));
            items.add(new T8600.Circle(5, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30));
        } else {
            items.add(new T8600.Circle(1, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30, 40, "测试围栏1"));
            items.add(new T8600.Circle(2, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30, 40, "测试围栏2"));
            items.add(new T8600.Circle(3, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30, 40, "测试围栏3"));
            items.add(new T8600.Circle(4, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30, 40, "测试围栏4"));
            items.add(new T8600.Circle(5, 0, 123123, 112312, 123, START_TIME, END_TIME, 200, 30, 40, "测试围栏5"));
        }
        bean.setItems(items);
        return bean;
    }

    //删除圆形区域|删除矩形区域|删除多边形区域|删除路线
    public static T8601 T8601() {
        return new T8601(1, 2, 3, 65535);
    }

    //设置矩形区域
    public static T8602 T8602(int version) {
        T8602 bean = new T8602();
        bean.setAction(ShapeAction.Update);
        List<T8602.Rectangle> items = new ArrayList<>(5);
        if (version == 2013) {
            items.add(new T8602.Rectangle(1, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30));
            items.add(new T8602.Rectangle(2, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30));
            items.add(new T8602.Rectangle(3, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30));
            items.add(new T8602.Rectangle(4, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30));
            items.add(new T8602.Rectangle(5, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30));
        } else {
            items.add(new T8602.Rectangle(1, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30, 40, "测试围栏1"));
            items.add(new T8602.Rectangle(2, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30, 40, "测试围栏2"));
            items.add(new T8602.Rectangle(3, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30, 40, "测试围栏3"));
            items.add(new T8602.Rectangle(4, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30, 40, "测试围栏4"));
            items.add(new T8602.Rectangle(5, 0, 123123, 112312, 123123, 112312, START_TIME, END_TIME, 200, 30, 40, "测试围栏5"));
        }
        bean.setItems(items);
        return bean;
    }

    //设置多边形区域
    public static T8604 T8604(int version) {
        T8604 bean = new T8604();
        bean.setId(3);
        bean.setAttribute(0);
        bean.setStartTime(START_TIME);
        bean.setEndTime(END_TIME);
        bean.setMaxSpeed(123);
        bean.setDuration(60);
        bean.addPoint(345, 123);
        bean.addPoint(345, 123);
        bean.addPoint(345, 123);
        bean.addPoint(345, 123);
        bean.addPoint(345, 123);
        if (version >= 2019) {
            bean.setNightMaxSpeed(40);
            bean.setName("测试围栏");
        }
        return bean;
    }

    //设置路线
    public static T8606 T8606(int version) {
        T8606 bean = new T8606();
        bean.setId(59397);
        bean.setAttribute(0);
        bean.setStartTime(START_TIME);
        bean.setEndTime(END_TIME);
        List<T8606.Line> item = new ArrayList<>(4);
        if (version == 2013) {
            item.add(new T8606.Line(1, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4));
            item.add(new T8606.Line(2, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4));
            item.add(new T8606.Line(3, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4));
            item.add(new T8606.Line(4, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4));
        } else {
            item.add(new T8606.Line(1, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4, 5));
            item.add(new T8606.Line(2, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4, 5));
            item.add(new T8606.Line(3, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4, 5));
            item.add(new T8606.Line(4, 1, 31234567, 121234567, 9, 0, 1, 2, 3, 4, 5));
            bean.setName("测试区域");
        }
        bean.setItems(item);
        return bean;
    }

    //查询区域或线路数据
    public static T8608 T8608() {
        T8608 bean = new T8608();
        bean.setType(0);
        bean.setId(new int[]{2, 4, 6, 8, 999999999});
        return bean;
    }

    //多媒体数据上传应答
    public static T8800 T8800() {
        T8800 bean = new T8800();
        bean.setMediaId(49503);
        bean.setId(new short[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return bean;
    }

    //摄像头立即拍摄命令
    public static T8801 T8801() {
        T8801 bean = new T8801();
        bean.setChannelId(1);
        bean.setCommand(2);
        bean.setTime(3);
        bean.setSave(1);
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
        bean.setEndTime(TIME);
        bean.setStartTime(TIME);
        return bean;
    }

    //存储多媒体数据上传命令
    public static T8803 T8803() {
        T8803 bean = new T8803();
        bean.setType(0);
        bean.setChannelId(1);
        bean.setEvent(3);
        bean.setEndTime(TIME);
        bean.setStartTime(TIME);
        bean.setDelete(0);
        return bean;
    }

    //录音开始命令
    public static T8804 T8804() {
        T8804 bean = new T8804();
        bean.setCommand(0x01);
        bean.setTime(6328);
        bean.setSave(1);
        bean.setAudioSamplingRate(0);
        return bean;
    }

    //单条存储多媒体数据检索上传命令
    public static T8805 T8805() {
        T8805 bean = new T8805();
        bean.setId(28410);
        bean.setDelete(1);
        return bean;
    }

    //数据上行透传
    public static T0900 T0900() {
        T0900 bean = new T0900();
        KeyValuePair<Integer, Object> message = new KeyValuePair<>();
        bean.setMessage(message);

        PeripheralSystem peripheralSystem = new PeripheralSystem();
        peripheralSystem.addItem((byte) 1, "测试公司1", "TEST-001", "1.1.0", "1.1.1", "device_id_1", "user_code_1");
        peripheralSystem.addItem((byte) 2, "测试公司2", "TEST-002", "1.2.0", "1.1.2", "device_id_2", "user_code_2");
        peripheralSystem.addItem((byte) 3, "测试公司3", "TEST-003", "1.3.0", "1.1.3", "device_id_3", "user_code_3");

        message.setKey(PeripheralSystem.key);
        message.setValue(peripheralSystem);
        return bean;
    }

    //事件设置
    public static T8301 T8301() {
        T8301 bean = new T8301();
        bean.setType(Action.Append);
        bean.addEvent(1, "test");
        bean.addEvent(2, "测试2");
        bean.addEvent(3, "www.jtt808.cn");
        return bean;
    }

    //文本信息下发
    public static T8300 T8300_2013() {
        return new T8300("测试123@456#abc!...结束", 1, 1, 1, 1, 1, 1);
    }

    public static T8300 T8300_2019() {
        return new T8300(1, "测试123@456#abc!...结束", 1, 1, 1, 1, 1, 1);
    }

    //===================================== 1078

    //终端上传音视频属性
    public static T1003 T1003() {
        T1003 bean = new T1003();
        bean.setAudioFormat(127);
        bean.setAudioChannels(4);
        bean.setAudioSamplingRate(2);
        bean.setAudioBitDepth(0);
        bean.setAudioFrameLength(37961);
        bean.setAudioSupport(1);
        bean.setVideoFormat(32);
        bean.setMaxAudioChannels(8);
        bean.setMaxVideoChannels(8);
        return bean;
    }

    //终端上传乘客流量
    public static T1005 T1005() {
        T1005 bean = new T1005();
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        bean.setGetOffCount(18450);
        bean.setGetOnCount(33269);
        return bean;
    }

    //终端上传音视频资源列表
    public static T1205 T1205() {
        List<T1205.Item> items = new ArrayList<>(2);
        items.add(new T1205.Item(1, START_TIME, END_TIME, 0, 0, 1, 1, 1, 1024));
        items.add(new T1205.Item(2, START_TIME, END_TIME, 0, 0, 2, 2, 2, 2048));

        T1205 bean = new T1205();
        bean.setResponseSerialNo(4321);
        bean.setItems(items);
        return bean;
    }

    //文件上传完成通知
    public static T1206 T1206() {
        T1206 bean = new T1206();
        bean.setResponseSerialNo(7050);
        bean.setResult(1);
        return bean;
    }

    //实时音视频传输请求
    public static T9101 T9101() {
        T9101 bean = new T9101();
        bean.setIp("123.123.123.123");
        bean.setTcpPort(772);
        bean.setUdpPort(16582);
        bean.setChannelNo(12);
        bean.setMediaType(1);
        bean.setStreamType(0);
        return bean;
    }

    //音视频实时传输控制
    public static T9102 T9102() {
        T9102 bean = new T9102();
        bean.setChannelNo(8);
        bean.setCommand(1);
        bean.setCloseType(2);
        bean.setStreamType(3);
        return bean;
    }

    //实时音视频传输状态通知
    public static T9105 T9105() {
        T9105 bean = new T9105();
        bean.setChannelNo(2);
        bean.setPacketLossRate(3);
        return bean;
    }

    //平台下发远程录像回放请求
    public static T9201 T9201() {
        T9201 bean = new T9201();
        bean.setIp("12.12.123.123");
        bean.setTcpPort(42937);
        bean.setUdpPort(15468);
        bean.setChannelNo(26674);
        bean.setMediaType(2);
        bean.setStreamType(0);
        bean.setStorageType(0);
        bean.setPlaybackMode(0);
        bean.setPlaybackSpeed(0);
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        return bean;
    }

    //平台下发远程录像回放控制
    public static T9202 T9202() {
        T9202 bean = new T9202();
        bean.setChannelNo(14865);
        bean.setPlaybackMode(1);
        bean.setPlaybackSpeed(3);
        bean.setPlaybackTime("200707192359");
        return bean;
    }

    //查询资源列表
    public static T9205 T9205() {
        T9205 bean = new T9205();
        bean.setChannelNo(34023);
        bean.setMediaType(20635);
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        bean.setWarnBit1(0);
        bean.setWarnBit2(0);
        bean.setStorageType(42752);
        bean.setStreamType(40558);
        return bean;
    }

    //文件上传指令
    public static T9206 T9206() {
        T9206 bean = new T9206();
        bean.setIp("192.168.1.1");
        bean.setPort(11053);
        bean.setUsername("username");
        bean.setPassword("password");
        bean.setPath("/alarm_file");
        bean.setChannelNo(1);
        bean.setStartTime(START_TIME);
        bean.setEndTime(END_TIME);
        bean.setWarnBit1(0);
        bean.setWarnBit2(0);
        bean.setMediaType(0);
        bean.setStorageType(1);
        bean.setStreamType(1);
        bean.setCondition(1);
        return bean;
    }

    //文件上传控制
    public static T9207 T9207() {
        T9207 bean = new T9207();
        bean.setResponseSerialNo(27133);
        bean.setCommand(2);
        return bean;
    }

    //云台旋转
    public static T9301 T9301() {
        T9301 bean = new T9301();
        bean.setChannelNo(1);
        bean.setParam1(2);
        bean.setParam2(3);
        return bean;
    }

    //云台调整焦距控制
    public static T9302 T9302() {
        T9302 bean = new T9302();
        bean.setChannelNo(1);
        bean.setParam(255);
        return bean;
    }

    //===================================== JSATL

    //报警附件信息消息
    public static T1210 T1210() {
        T1210 bean = new T1210();
        bean.setDeviceId(DEVICE_ID);
        bean.setDeviceId(DEVICE_ID);
        bean.setDateTime(TIME);
        bean.setSequenceNo(1);
        bean.setFileTotal(3);
        bean.setReserved(1);
        bean.setPlatformAlarmId(ALARM_NO);
        bean.setType(0);
        bean.setItems(null);
        return bean;
    }

    //文件信息上传/文件上传完成消息
    public static T1211 T1211() {
        T1211 bean = new T1211();
        bean.setName("www.jtt808.cn");
        bean.setType(1);
        bean.setSize(1024);
        return bean;
    }

    //报警附件上传指令
    public static T9208 T9208() {
        T9208 bean = new T9208();
        bean.setIp("47.104.97.169");
        bean.setTcpPort(8202);
        bean.setUdpPort(8203);
        bean.setDeviceId(DEVICE_ID);
        bean.setDateTime(TIME);
        bean.setSequenceNo(1);
        bean.setFileTotal(1);
        bean.setReserved(1);
        bean.setPlatformAlarmId(ALARM_NO);
        bean.setReserves(new byte[16]);
        return bean;
    }

    //文件上传完成消息
    public static T9212 T9212() {
        T9212 bean = new T9212();
        bean.setName("www.jtt808.cn");
        bean.setType(0);
        bean.setResult(1);
        bean.setItems(new int[]{0, 1024});
        return bean;
    }
}