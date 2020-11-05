package org.yzh.web.config;

import io.github.yezhihao.netmc.NettyConfig;
import io.github.yezhihao.netmc.TCPServer;
import io.github.yezhihao.netmc.codec.Delimiter;
import io.github.yezhihao.netmc.core.HandlerInterceptor;
import io.github.yezhihao.netmc.core.HandlerMapping;
import io.github.yezhihao.netmc.core.SpringHandlerMapping;
import io.github.yezhihao.netmc.session.MessageManager;
import io.github.yezhihao.netmc.session.SessionListener;
import io.github.yezhihao.netmc.session.SessionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.codec.MultiPacketDecoder;
import org.yzh.web.component.adapter.JTMessageAdapter;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTMultiPacketListener;
import org.yzh.web.endpoint.JTSessionListener;

@Configuration
@ConditionalOnProperty(value = "tcp-server.jt808.enable", havingValue = "true")
public class JTConfig {

    @Order(Integer.MIN_VALUE)
    @Component
    public class Starter implements InitializingBean, DisposableBean {

        private TCPServer jt808Server;

        public Starter(@Value("${tcp-server.jt808.port}") int port,
                       JTMessageAdapter messageAdapter,
                       SessionManager sessionManager,
                       HandlerMapping handlerMapping,
                       HandlerInterceptor handlerInterceptor) {
            NettyConfig jtConfig = NettyConfig.custom()
                    .setPort(port)
                    .setMaxFrameLength(2 + 21 + 1023 + 2)
                    .setDelimiters(new Delimiter(new byte[]{0x7e}))
                    .setDecoder(messageAdapter)
                    .setEncoder(messageAdapter)
                    .setSessionManager(sessionManager)
                    .setHandlerMapping(handlerMapping)
                    .setHandlerInterceptor(handlerInterceptor)
                    .build();
            this.jt808Server = new TCPServer("808服务", jtConfig);
        }

        @Override
        public void afterPropertiesSet() {
            jt808Server.start();
        }

        @Override
        public void destroy() {
            jt808Server.stop();
        }
    }

    @Bean
    public JTMessageAdapter messageAdapter() {
        return new JTMessageAdapter(
                new JTMessageEncoder("org.yzh.protocol"),
                new MultiPacketDecoder("org.yzh.protocol", multiPacketListener())
        );
    }

    @Bean
    public JTMultiPacketListener multiPacketListener() {
        return new JTMultiPacketListener(10);
    }

    @Bean
    public MessageManager messageManager() {
        return new MessageManager(sessionManager());
    }

    @Bean
    public SessionManager sessionManager() {
        return new SessionManager(sessionListener());
    }

    @Bean
    public SessionListener sessionListener() {
        return new JTSessionListener();
    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new SpringHandlerMapping();
    }

    @Bean
    public JTHandlerInterceptor handlerInterceptor() {
        return new JTHandlerInterceptor();
    }
}