package org.yzh.client;

import org.yzh.client.netty.ClientConfig;
import org.yzh.client.netty.HandlerMapping;
import org.yzh.client.netty.TCPClient;
import org.yzh.commons.util.DateUtils;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.attribute.AlarmADAS;
import org.yzh.protocol.commons.transform.attribute.AlarmBSD;
import org.yzh.protocol.commons.transform.attribute.AlarmDSM;
import org.yzh.protocol.commons.transform.attribute.AlarmTPMS;
import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0200;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class ClientTest {

    public static final JTMessageAdapter messageAdapter = new JTMessageAdapter("org.yzh.protocol");

    public static final ClientConfig jtConfig = new ClientConfig.Builder()
            .setIp("127.0.0.1")
            .setPort(7611)
            .setMaxFrameLength(2 + 21 + 1023 * 2 + 1 + 2)
            .setDelimiters(new byte[]{0x7e})
            .setDecoder(messageAdapter)
            .setEncoder(messageAdapter)
            .setHandlerMapping(new HandlerMapping("org.yzh.client"))
            .build();

    public static void main(String[] args) {
        TCPClient tcpClient = new TCPClient("0", jtConfig).start();
        tcpClient.writeObject(T0100("1"));
        while (true) {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tcpClient.writeObject(T0200("1"));
        }
    }

    public static T0100 T0100(String id) {
        String deviceId = "T" + StrUtils.leftPad(id, 6, '0');
        String clientId = "1" + StrUtils.leftPad(id, 10, '0');
        String plateNo = "测A" + StrUtils.leftPad(id, 5, '0');

        T0100 message = new T0100();
        message.setMessageId(JT808.终端注册);
        message.setProtocolVersion(1);
        message.setVersion(true);
        message.setClientId(clientId);

        message.setProvinceId(31);
        message.setCityId(115);
        message.setMakerId("yzh");
        message.setDeviceModel("www.jtt808.cn");
        message.setDeviceId(deviceId);
        message.setPlateColor(1);
        message.setPlateNo(plateNo);
        return message;
    }

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static T0200 T0200(String id) {
        String clientId = "1" + StrUtils.leftPad(id, 10, '0');

        T0200 message = new T0200();
        message.setMessageId(JT808.位置信息汇报);
        message.setProtocolVersion(1);
        message.setVersion(true);
        message.setClientId(clientId);

        message.setWarnBit(1024);
        message.setStatusBit(2048);
        message.setLatitude(39915931);
        message.setLongitude(116403829);
        message.setAltitude(312);
        message.setSpeed(111);
        message.setDirection(99);
        LocalDateTime dateTime = LocalDateTime.now();
        message.setDateTime(DateUtils.yyMMddHHmmss.format(dateTime));

        AlarmADAS alarmADAS = new AlarmADAS();
        alarmADAS.setId(64);
        alarmADAS.setState(1);
        alarmADAS.setType(random.nextInt(1, 11));
        alarmADAS.setLevel(1);
        alarmADAS.setFrontSpeed(10);
        alarmADAS.setFrontDistance(10);
        alarmADAS.setDeviateType(1);
        alarmADAS.setRoadSign(1);
        alarmADAS.setRoadSignValue(10);
        alarmADAS.setSpeed(10);
        alarmADAS.setAltitude(100);
        alarmADAS.setLatitude(32111111);
        alarmADAS.setLongitude(123111111);
        alarmADAS.setAlarmTime(dateTime);
        alarmADAS.setStatusBit(1);
        alarmADAS.setAlarmId(new AlarmId(clientId, dateTime, 1, 1, 1));

        AlarmDSM alarmDSM = new AlarmDSM();
        alarmDSM.setId(65);
        alarmDSM.setState(2);
        alarmDSM.setType(random.nextInt(1, 17));
        alarmDSM.setLevel(2);
        alarmDSM.setFatigueDegree(20);
        alarmDSM.setReserved(20);
        alarmDSM.setSpeed(20);
        alarmDSM.setAltitude(200);
        alarmDSM.setLatitude(32222222);
        alarmDSM.setLongitude(123222222);
        alarmDSM.setAlarmTime(dateTime);
        alarmDSM.setStatusBit(2);
        alarmDSM.setAlarmId(new AlarmId(clientId, dateTime, 2, 2, 2));

        AlarmTPMS alarmTPMS = new AlarmTPMS();
        alarmTPMS.setId(66);
        alarmTPMS.setState(3);
        alarmTPMS.setSpeed(30);
        alarmTPMS.setAltitude(300);
        alarmTPMS.setLatitude(32333333);
        alarmTPMS.setLongitude(123333333);
        alarmTPMS.setAlarmTime(dateTime);
        alarmTPMS.setStatusBit(3);
        alarmTPMS.setAlarmId(new AlarmId(clientId, dateTime, 3, 3, 3));

        AlarmBSD alarmBSD = new AlarmBSD();
        alarmBSD.setId(67);
        alarmBSD.setState(4);
        alarmBSD.setType(random.nextInt(1, 3));
        alarmBSD.setSpeed(40);
        alarmBSD.setAltitude(400);
        alarmBSD.setLatitude(32444444);
        alarmBSD.setLongitude(123444444);
        alarmBSD.setAlarmTime(dateTime);
        alarmBSD.setStatusBit(4);
        alarmBSD.setAlarmId(new AlarmId(clientId, dateTime, 4, 4, 4));


        Map<Integer, Object> attributes = new TreeMap<>();
        attributes.put(AlarmADAS.key, alarmADAS);
        attributes.put(AlarmDSM.key, alarmDSM);
        attributes.put(AlarmTPMS.key, alarmTPMS);
        attributes.put(AlarmBSD.key, alarmBSD);

        message.setAttributes(attributes);
        return message;
    }
}