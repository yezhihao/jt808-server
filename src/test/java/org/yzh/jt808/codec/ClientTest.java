package org.yzh.jt808.codec;

import org.yzh.framework.netty.client.ClientConfig;
import org.yzh.framework.netty.client.HandlerMapping;
import org.yzh.framework.netty.client.TCPClient;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

import java.util.Scanner;

/**
 * 不依赖spring，快速启动netty服务
 */
public class ClientTest {

    private static final Scanner scanner = new Scanner(System.in);

    private static TCPClient tcpClient;

    static {
        ClientConfig jtConfig = new ClientConfig.Builder()
                .setIp("127.0.0.1")
                .setPort(7611)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[]{0x7e})
                .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                .setHandlerMapping(new HandlerMapping("org.yzh.client"))
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
                        tcpClient.writeObject(Beans.H2013(Beans.T0100()));
                        break;
                    case 2:
                        tcpClient.writeObject(Beans.H2013(Beans.T0200Attributes()));
                        break;
                }
            }
        }
    }
}