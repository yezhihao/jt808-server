package org.yzh;

import io.github.yezhihao.netmc.NettyConfig;
import io.github.yezhihao.netmc.Server;
import io.github.yezhihao.netmc.core.DefaultHandlerMapping;
import io.github.yezhihao.netmc.handler.DispatcherHandler;
import io.github.yezhihao.netmc.session.SessionListener;
import io.github.yezhihao.netmc.session.SessionManager;
import org.apache.logging.log4j.Level;
import org.yzh.commons.util.LogUtils;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.web.endpoint.JTHandlerInterceptor;

/**
 * 不依赖spring，快速启动netty服务
 */
public class QuickStart {
    public static final JTMessageAdapter messageAdapter = new JTMessageAdapter("org.yzh.protocol");
    public static final DefaultHandlerMapping handlerMapping = new DefaultHandlerMapping("org.yzh.client");
    public static final JTHandlerInterceptor handlerInterceptor = new JTHandlerInterceptor();
    public static final SessionManager sessionManager = new SessionManager(new SessionListener() {
    });

    public static final int port = 7100;

    public static void main(String[] args) {
        LogUtils.setLevel(Level.WARN);
        DispatcherHandler.STOPWATCH = true;

        Server tcpServer = new NettyConfig.Builder()
                .setPort(port)
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
                .setPort(port)
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