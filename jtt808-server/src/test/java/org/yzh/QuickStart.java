package org.yzh;

import io.github.yezhihao.netmc.NettyConfig;
import io.github.yezhihao.netmc.Server;
import io.github.yezhihao.netmc.core.DefaultHandlerMapping;
import io.github.yezhihao.netmc.session.SessionManager;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.web.endpoint.JTHandlerInterceptor;

/**
 * 不依赖spring，快速启动netty服务
 */
public class QuickStart {

    public static void main(String[] args) {
        JTMessageAdapter messageAdapter = new JTMessageAdapter("org.yzh.protocol");
        DefaultHandlerMapping handlerMapping = new DefaultHandlerMapping("org.yzh.web.endpoint");
        JTHandlerInterceptor handlerInterceptor = new JTHandlerInterceptor();
        SessionManager sessionManager = new SessionManager();

        Server tcpServer = new NettyConfig.Builder()
                .setPort(7611)
                .setMaxFrameLength(4096)
                .setDelimiters(new byte[][]{{0x7e}})
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setSessionManager(sessionManager)
                .setName("808-TCP")
                .build();
        tcpServer.start();

        Server udpServer = new NettyConfig.Builder()
                .setPort(7611)
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setSessionManager(sessionManager)
                .setEnableUDP(true)
                .setName("808-UDP")
                .build();
        udpServer.start();
    }
}