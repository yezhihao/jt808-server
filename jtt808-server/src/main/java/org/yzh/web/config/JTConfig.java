package org.yzh.web.config;

import io.github.yezhihao.netmc.NettyConfig;
import io.github.yezhihao.netmc.Server;
import io.github.yezhihao.netmc.codec.Delimiter;
import io.github.yezhihao.netmc.codec.LengthField;
import io.github.yezhihao.netmc.core.HandlerMapping;
import io.github.yezhihao.netmc.core.SpringHandlerMapping;
import io.github.yezhihao.netmc.session.SessionListener;
import io.github.yezhihao.netmc.session.SessionManager;
import io.github.yezhihao.protostar.SchemaManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.yzh.commons.spring.SSEService;
import org.yzh.protocol.codec.*;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTMessagePushAdapter;
import org.yzh.web.endpoint.JTMultiPacketListener;
import org.yzh.web.model.enums.SessionKey;

@Order(Integer.MIN_VALUE)
@Configuration
@ConditionalOnProperty(value = "jt-server.jt808.enabled", havingValue = "true", matchIfMissing = true)
public class JTConfig {

    @ConditionalOnProperty(value = "jt-server.jt808.tcp-port")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jt808TCPServer(JTMessageAdapter messageAdapter,
                                 HandlerMapping handlerMapping,
                                 JTHandlerInterceptor handlerInterceptor,
                                 SessionManager sessionManager,
                                 JTProperties jtProperties) {
        return NettyConfig.custom()
                .setIdleStateTime(jtProperties.getIdleTimeout(), 0, 0)
                .setPort(jtProperties.getTcpPort())
                //标识位[2] + 消息头[21] + 消息体[1023 * 2(转义预留)]  + 校验码[1] + 标识位[2]
                .setMaxFrameLength(2 + 21 + 1023 * 2 + 1 + 2)
                .setDelimiters(new Delimiter(new byte[]{0x7e}, false))
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setSessionManager(sessionManager)
                .setName("808-TCP")
                .build();
    }

    @ConditionalOnProperty(value = "jt-server.jt808.udp-port")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jt808UDPServer(JTMessageAdapter messageAdapter,
                                 HandlerMapping handlerMapping,
                                 JTHandlerInterceptor handlerInterceptor,
                                 SessionManager sessionManager,
                                 JTProperties jtProperties) {
        return NettyConfig.custom()
                .setIdleStateTime(jtProperties.getIdleTimeout(), 0, 0)
                .setPort(jtProperties.getUdpPort())
                .setDelimiters(new Delimiter(new byte[]{0x7e}, false))
                .setDecoder(messageAdapter)
                .setEncoder(messageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setSessionManager(sessionManager)
                .setName("808-UDP")
                .setEnableUDP(true)
                .build();
    }

    @ConditionalOnProperty(value = "jt-server.jt808.t9208.enabled", havingValue = "true")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server alarmFileServer(HandlerMapping handlerMapping,
                                  JTHandlerInterceptor handlerInterceptor,
                                  SchemaManager schemaManager,
                                  SSEService sseService,
                                  JTProperties jtProperties) {

        JTMessageEncoder encoder = new JTMessageEncoder(schemaManager);
        JTMessageDecoder decoder = new DataFrameMessageDecoder(schemaManager, new byte[]{0x30, 0x31, 0x63, 0x64});
        JTMessagePushAdapter alarmFileMessageAdapter = new JTMessagePushAdapter(encoder, decoder, sseService);

        return NettyConfig.custom()
                .setPort(jtProperties.getT9208().getPort())
                .setMaxFrameLength(2 + 21 + 1023 * 2 + 1 + 2)
                .setLengthField(new LengthField(new byte[]{0x30, 0x31, 0x63, 0x64}, 1024 * 65, 58, 4))
                .setDelimiters(new Delimiter(new byte[]{0x7e}, false))
                .setDecoder(alarmFileMessageAdapter)
                .setEncoder(alarmFileMessageAdapter)
                .setHandlerMapping(handlerMapping)
                .setHandlerInterceptor(handlerInterceptor)
                .setName("AlarmFile")
                .build();
    }

    @Bean
    public JTMessageAdapter jtMessageAdapter(SchemaManager schemaManager, SSEService sseService) {
        JTMessageEncoder messageEncoder = new JTMessageEncoder(schemaManager);
        JTMessageDecoder messageDecoder = new MultiPacketDecoder(schemaManager, new JTMultiPacketListener(10));
        return new JTMessagePushAdapter(messageEncoder, messageDecoder, sseService);
    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new SpringHandlerMapping();
    }

    @Bean
    public SessionManager sessionManager(SessionListener sessionListener) {
        return new SessionManager(SessionKey.class, sessionListener);
    }

    @Bean
    public SchemaManager schemaManager(JTProperties jtProperties) {
        return new SchemaManager(jtProperties.getMessagePackage());
    }

    @Bean
    public MultiPacketDecoder multiPacketDecoder(SchemaManager schemaManager) {
        return new MultiPacketDecoder(schemaManager);
    }
}