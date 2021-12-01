package org.yzh.client;

import org.yzh.client.netty.ClientConfig;
import org.yzh.client.netty.HandlerMapping;
import org.yzh.client.netty.TCPClient;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0100;

import java.util.Scanner;

public class ClientTest {

    public static final JTMessageAdapter messageAdapter = new JTMessageAdapter(
            new JTMessageEncoder("org.yzh.protocol"),
            new JTMessageDecoder("org.yzh.protocol")
    );

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
        final Scanner scanner = new Scanner(System.in);
        TCPClient tcpClient = new TCPClient("0", jtConfig).start();
        while (true) {
            System.out.println("选择发送的消息：1.注册 2.位置信息上报");
            while (scanner.hasNext()) {
                int i = scanner.nextInt();
                switch (i) {
                    case 0:
                        tcpClient.stop();
                        return;
                    case 1:
                        tcpClient.writeObject(T0100("1"));
                        break;
                    case 2:
//                        tcpClient.writeObject(BeanTest.H2019(JT808Beans.T0200Attributes()));
                        break;
                }
            }
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
}