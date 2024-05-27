package org.yzh.client;

import io.github.yezhihao.netmc.util.Client;
import io.github.yezhihao.netmc.util.Stopwatch;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.QuickStart;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0200;
import org.yzh.protocol.t808.T0801;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 压力测试
 * 模拟1200台设备，每100毫秒上报一次位置信息
 */
public abstract class StressTest {
    public static final byte[] bytes = ByteBufUtil.decodeHexDump("7e0200407c0100000000017299841738ffff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c051e3737370000000000000000000000000000000000000000000000000000001105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e310128637e");

    public static final JTMessageEncoder encoder = new JTMessageEncoder("org.yzh.protocol");

    private static final Stopwatch s = new Stopwatch().start();

    public static final String host = "127.0.0.1";
    public static final int port = QuickStart.port;

    public static final int size = 1000;
    public static final long Interval = 4;

    public static void main(String[] args) throws Exception {
//        Client[] clients = Client.UDP(host, port, size);
        Client[] clients = Client.TCP(host, port, size);

        for (int i = 0; i < size; i++) {
            clients[i].send(T0100(i));
            s.increment();
        }

        Thread.sleep(500L);
        ByteBuf imagePacket = packet();

        Object[] points = locations();
        LocalDateTime deviceTime = LocalDateTime.now();

        int num = 0;
        while (true) {
            int[] point = (int[]) points[num++];
            if (num >= points.length) num = 0;

            deviceTime = deviceTime.plusSeconds(1);

            for (int i = 0; i < size; i++) {
                T0200 message = T0200(i, deviceTime, point);

//                T0801 message = T0801(i, strTime, point);
//                imagePacket.readerIndex(0);
//                message.setPacket(imagePacket);

                clients[i].send(getBytes(message));
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

    public static T0200 T0200(int id, LocalDateTime time, int[] point) {
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
        message.setDeviceTime(time);

        return message;
    }

    public static T0801 T0801(int id, LocalDateTime time, int[] point) {
        String clientId = "1" + StrUtils.leftPad(String.valueOf(id + 1), 10, '0');

        T0801 bean = new T0801();
        bean.setMessageId(JT808.多媒体数据上传);
        bean.setProtocolVersion(ProtocolVersion);
        bean.setVersion(true);
        bean.setClientId(clientId);

        bean.setId(-1);
        bean.setType(0);
        bean.setFormat(0);
        bean.setEvent(-1);
        bean.setChannelId(1);
        bean.setLocation(T0200(id, time, point));
        return bean;
    }

    private static ByteBuf packet() throws IOException {
        FileInputStream fos = new FileInputStream("D:/test.jpg");
        FileChannel fc = fos.getChannel();
        ByteBuf byteBuf = Unpooled.buffer(9000);
        int size = (int) fc.size();
        byteBuf.writeBytes(fc, 0, size);
        return byteBuf;
    }

    private static byte[] getBytes(JTMessage message) {
        ByteBuf buf = encoder.encode(message);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        buf.release();
        return bytes;
    }
}