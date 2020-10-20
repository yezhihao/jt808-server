package org.yzh;

import org.yzh.framework.mvc.DefaultHandlerMapping;
import org.yzh.framework.netty.NettyConfig;
import org.yzh.framework.netty.TCPServer;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.component.adapter.JTMessageAdapter;
import org.yzh.web.endpoint.JTHandlerInterceptor;

/**
 * 不依赖spring，快速启动netty服务
 */
public class QuickStart {

    public static void main(String[] args) {
        JTMessageAdapter messageAdapter = new JTMessageAdapter(
                new JTMessageEncoder("org.yzh.protocol"),
                new JTMessageDecoder("org.yzh.protocol")
        );
        NettyConfig jtConfig = new NettyConfig.Builder()
                .setPort(7611)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[][]{{0x7e}})
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(new DefaultHandlerMapping("org.yzh.web.endpoint"))
                .setHandlerInterceptor(new JTHandlerInterceptor())
                .setSessionManager(new SessionManager())
                .build();

        TCPServer tcpServer = new TCPServer("808服务", jtConfig);
        tcpServer.start();
    }
}