package org.yzh.client;

import org.yzh.client.netty.ClientConfig;
import org.yzh.client.netty.HandlerMapping;
import org.yzh.client.netty.TCPClient;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

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
//                        tcpClient.writeObject(BeanTest.H2019(JT808Beans.T0100()));
                        break;
                    case 2:
//                        tcpClient.writeObject(BeanTest.H2019(JT808Beans.T0200Attributes()));
                        break;
                }
            }
        }
    }
}