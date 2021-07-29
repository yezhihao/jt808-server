package org.yzh.client;

import org.yzh.client.netty.TCPClient;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.StrUtils;

import java.time.LocalDateTime;

/**
 * 压力测试
 * 模拟1250台设备，每100毫秒上报一次位置信息
 */
public class StressTest {

    //连接数量
    private static final int Total = 1250;

    //上报间隔(毫秒)
    private static final long Interval = 100;

    public static void main(String[] args) {
        LocalDateTime deviceTime = LocalDateTime.now();

        TCPClient[] clients = new TCPClient[Total];
        for (int i = 0; i < Total; i++) {
            String id = String.valueOf(i);
            clients[i] = new TCPClient(id, ClientTest.jtConfig).start();
            clients[i].writeObject(T0100(id));
        }

        while (true) {
            String strTime = DateUtils.yyMMddHHmmss.format(deviceTime);
            deviceTime = deviceTime.plusSeconds(1L);

            for (int i = 0; i < Total; i++) {
                clients[i].writeObject(T0200(String.valueOf(i), strTime));
            }
            try {
                Thread.sleep(Interval);
            } catch (InterruptedException e) {
            }
        }
    }

    private static int ProtocolVersion = 1;

    public static T0100 T0100(String id) {
        String deviceId = "T" + StrUtils.leftPad(id, 6, '0');
        String clientId = "1" + StrUtils.leftPad(id, 10, '0');
        String plateNo = "测A" + StrUtils.leftPad(id, 5, '0');

        T0100 message = new T0100();
        message.setMessageId(JT808.终端注册);
        message.setProtocolVersion(ProtocolVersion);
        message.setVersion(true);
        message.setClientId(clientId);

        message.setProvinceId(31);
        message.setCityId(115);
        message.setMakerId("4");
        message.setDeviceModel("BSJ-GF-06");
        message.setDeviceId(deviceId);
        message.setPlateColor(1);
        message.setPlateNo(plateNo);
        return message;
    }

    public static T0200 T0200(String id, String time) {
        String clientId = StrUtils.leftPad(id, 12, '0');

        T0200 message = new T0200();
        message.setMessageId(JT808.位置信息汇报);
        message.setProtocolVersion(ProtocolVersion);
        message.setVersion(true);
        message.setClientId(clientId);

        message.setWarnBit(1024);
        message.setStatusBit(2048);
        message.setLatitude(116307629);
        message.setLongitude(40058359);
        message.setAltitude(312);
        message.setSpeed(3);
        message.setDirection(99);
        message.setDateTime(time);
        return message;
    }
}