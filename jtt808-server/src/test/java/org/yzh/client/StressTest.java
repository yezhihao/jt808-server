package org.yzh.client;

import io.github.yezhihao.netmc.util.Client;
import io.github.yezhihao.netmc.util.Stopwatch;
import io.netty.buffer.ByteBuf;
import org.yzh.QuickStart;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.commons.DateUtils;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0200;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 压力测试
 * 模拟1200台设备，每100毫秒上报一次位置信息
 */
public abstract class StressTest {
    public static final byte[] bytes = DatatypeConverter.parseHexBinary("7e0200407c0100000000017299841738ffff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c051e3737370000000000000000000000000000000000000000000000000000001105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e310128637e");

    public static final JTMessageEncoder encoder = new JTMessageEncoder("org.yzh.protocol");

    private static final Stopwatch s = new Stopwatch().start();

    public static final String host = "127.0.0.1";
    public static final int port = QuickStart.port;

    public static final int size = 1000;
    public static final long Interval = 1;

    public static void main(String[] args) throws Exception {
        Client[] clients = Client.UDP(host, port, size);
//        Client[] clients = Client.TCP(host, port, size);

        for (int i = 0; i < size; i++) {
            clients[i].send(T0100(i));
            s.increment();
        }

        Thread.sleep(500L);
        Object[] points = locations();
        LocalDateTime deviceTime = LocalDateTime.now();

        int num = 0;
        while (true) {
            int[] point = (int[]) points[num++];
            if (num >= points.length) num = 0;

            String strTime = DateUtils.yyMMddHHmmss.format(deviceTime);
            deviceTime = deviceTime.plusSeconds(1);

            for (int i = 0; i < size; i++) {
                clients[i].send(T0200(i, strTime, point));
                s.increment();
            }
            try {
                Thread.sleep(Interval);
            } catch (InterruptedException e) {
            }
        }
    }

    private static Object[] locations() throws IOException {
        Object[] points;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("jtt808-server/target/test-classes/轨迹区域测试.txt"), StandardCharsets.UTF_8))) {
            points = reader.lines().map(s -> {
                double[] doubles = StrUtils.toDoubles(s, ",");
                return new int[]{(int) (doubles[0] * 1000000d), (int) (doubles[1] * 1000000d), (int) (doubles[2])};
            }).toArray();
        }
        return points;
    }

    private static int ProtocolVersion = 1;

    public static byte[] T0100(int i) {
        String id = String.valueOf(i + 1);
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

        byte[] bytes = getBytes(message);
        return bytes;
    }

    public static byte[] T0200(int id, String time, int[] point) {
        String clientId = "1" + StrUtils.leftPad(String.valueOf(id + 1), 10, '0');

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

        byte[] bytes = getBytes(message);
        return bytes;
    }

    private static byte[] getBytes(JTMessage message) {
        ByteBuf buf = encoder.encode(message);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        buf.release();
        return bytes;
    }
}