package org.yzh.client;

import org.yzh.client.netty.TCPClient;
import org.yzh.commons.util.DateUtils;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0200;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 压力测试
 * 模拟1200台设备，每100毫秒上报一次位置信息
 */
public class StressTest {

    //连接数量
    private static final int Total = 1200;

    //上报间隔(毫秒)
    private static final long Interval = 100;

    public static void main(String[] args) throws Exception {
        Object[] points;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("target/test-classes/test_data/轨迹区域测试.txt"), StandardCharsets.UTF_8))) {
            points = reader.lines().map(s -> {
                double[] doubles = StrUtils.toDoubles(s, ",");
                return new int[]{(int) (doubles[0] * 1000000d), (int) (doubles[1] * 1000000d), (int) (doubles[2])};
            }).toArray();
        }

        LocalDateTime deviceTime = LocalDateTime.now();

        TCPClient[] clients = new TCPClient[Total + 1];
        for (int i = 1; i <= Total; i++) {
            String id = String.valueOf(i);
            clients[i] = new TCPClient(id, ClientTest.jtConfig).start();
            clients[i].writeObject(T0100(id));
        }

        int i = 0;
        while (true) {
            int[] point = (int[]) points[i++];
            if (i >= points.length)
                i = 0;

            String strTime = DateUtils.yyMMddHHmmss.format(deviceTime);
            deviceTime = deviceTime.plusSeconds(20);

            for (int j = 1; j <= Total; j++) {
                clients[j].writeObject(T0200(String.valueOf(j), strTime, point));
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
        message.setMakerId("yzh");
        message.setDeviceModel("www.jtt808.cn");
        message.setDeviceId(deviceId);
        message.setPlateColor(1);
        message.setPlateNo(plateNo);
        return message;
    }

    public static T0200 T0200(String id, String time, int[] point) {
        String clientId = "1" + StrUtils.leftPad(id, 10, '0');

        T0200 message = new T0200();
        message.setMessageId(JT808.位置信息汇报);
        message.setProtocolVersion(ProtocolVersion);
        message.setVersion(true);
        message.setClientId(clientId);

        message.setWarnBit(1024);
        message.setStatusBit(2048);
        message.setLongitude(point[0]);
        message.setLatitude(point[1]);
        message.setAltitude(312);
        message.setSpeed(point[2]);
        message.setDirection(99);
        message.setDateTime(time);
        return message;
    }
}