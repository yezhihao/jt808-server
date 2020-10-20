package org.yzh.client;

import org.yzh.client.netty.ClientConfig;
import org.yzh.client.netty.HandlerMapping;
import org.yzh.client.netty.TCPClient;
import org.yzh.protocol.JT808Beans;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.component.adapter.JTMessageAdapter;

import java.util.Scanner;

/**
 * 不依赖spring，快速启动netty服务
 */
public class ClientTest {

    private static final Scanner scanner = new Scanner(System.in);

    private static TCPClient tcpClient;

    static {
        JTMessageAdapter messageAdapter = new JTMessageAdapter(
                new JTMessageEncoder("org.yzh.protocol"),
                new JTMessageDecoder("org.yzh.protocol")
        );

        ClientConfig jtConfig = new ClientConfig.Builder()
                .setIp("127.0.0.1")
                .setPort(7611)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[]{0x7e})
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(new HandlerMapping("org.yzh.netty"))
                .build();

        tcpClient = new TCPClient(jtConfig).start();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("选择发送的消息：1.注册 2.位置信息上报");
            while (scanner.hasNext()) {
                int i = scanner.nextInt();
                switch (i) {
                    case 0:
                        tcpClient.stop();
                        return;
                    case 1:
                        tcpClient.writeObject(JT808Beans.H2013(JT808Beans.T0100()));
                        break;
                    case 2:
                        tcpClient.writeObject(JT808Beans.H2013(JT808Beans.T0200Attributes()));
                        break;
                }
            }
        }
    }
}