package org.yzh.web.config;

import io.github.yezhihao.netmc.NettyConfig;
import io.github.yezhihao.netmc.Server;
import io.github.yezhihao.netmc.codec.Delimiter;
import io.github.yezhihao.netmc.codec.LengthField;
import io.github.yezhihao.netmc.core.HandlerMapping;
import io.github.yezhihao.netmc.session.SessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.web.endpoint.JTHandlerInterceptor;

@Order(Integer.MIN_VALUE)
@Configuration
@ConditionalOnProperty(value = "jt-server.jt808.enable", havingValue = "true")
public class JTConfig {

    private final JTMessageAdapter messageAdapter;
    private final HandlerMapping handlerMapping;
    private final JTHandlerInterceptor handlerInterceptor;
    private final SessionManager sessionManager;

    public JTConfig(JTMessageAdapter messageAdapter, HandlerMapping handlerMapping, JTHandlerInterceptor handlerInterceptor, SessionManager sessionManager) {
        this.messageAdapter = messageAdapter;
        this.handlerMapping = handlerMapping;
        this.handlerInterceptor = handlerInterceptor;
        this.sessionManager = sessionManager;
    }

    @ConditionalOnProperty(value = "jt-server.jt808.port.tcp")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jt808TCPServer(@Value("${jt-server.jt808.port.tcp}") int port) {
        return NettyConfig.custom()
                .setIdleStateTime(180, 0, 0)
                .setPort(port)
                //标识位[2] + 消息头[21] + 消息体[1023 * 2(转义预留)]  + 校验码[1] + 标识位[2]
                .setMaxFrameLength(2 + 21 + 1023 * 2 + 1 + 2)
                .setDelimiters(new Delimiter(new byte[]{0x7e}))
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setSessionManager(sessionManager)
                .setName("808-TCP")
                .build();
    }

    @ConditionalOnProperty(value = "jt-server.jt808.port.udp")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jt808UDPServer(@Value("${jt-server.jt808.port.udp}") int port) {
        return NettyConfig.custom()
                .setPort(port)
                .setDelimiters(new Delimiter(new byte[]{0x7e}))
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setSessionManager(sessionManager)
                .setName("808-UDP")
                .setEnableUDP(true)
                .build();
    }

    @ConditionalOnProperty(value = "jt-server.alarm-file.enable", havingValue = "true")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server alarmFileServer(@Value("${jt-server.alarm-file.port}") int port, JTMessageAdapter alarmFileMessageAdapter) {
        return NettyConfig.custom()
                .setPort(port)
                .setMaxFrameLength(2 + 21 + 1023 * 2 + 1 + 2)
                .setLengthField(new LengthField(new byte[]{0x30, 0x31, 0x63, 0x64}, 1024 * 65, 58, 4))
                .setDelimiters(new Delimiter(new byte[]{0x7e}))
                .setDecoder(alarmFileMessageAdapter)
                .setEncoder(alarmFileMessageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setName("AlarmFile")
                .build();
    }
}