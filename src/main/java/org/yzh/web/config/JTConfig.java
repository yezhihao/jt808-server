package org.yzh.web.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.SpringHandlerMapping;
import org.yzh.framework.netty.NettyConfig;
import org.yzh.framework.netty.TCPServer;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTMultiPacketListener;

@Configuration
public class JTConfig implements InitializingBean, DisposableBean {

    @Autowired
    private TCPServer tcpServer;

    @Bean
    public TCPServer tcpServer() {
        NettyConfig jtConfig = NettyConfig.custom()
                .setPort(7611)
                .setMaxFrameLength(17 + 1023)
                .setDelimiters(new byte[]{0x7e})
                .setDecoder(messageDecoder())
                .setEncoder(messageEncoder())
                .setHandlerMapping(handlerMapping())
                .setHandlerInterceptor(handlerInterceptor())
                .setMultiPacketListener(multiPacketListener())
                .build();
        return new TCPServer(jtConfig);
    }

    @Bean
    public MessageDecoder messageDecoder() {
        return new JTMessageDecoder("org.yzh.protocol");
    }

    @Bean
    public MessageEncoder messageEncoder() {
        return new JTMessageEncoder("org.yzh.protocol");
    }

    @Bean
    public JTHandlerInterceptor handlerInterceptor() {
        return new JTHandlerInterceptor();
    }

    @Bean
    public JTMultiPacketListener multiPacketListener() {
        return new JTMultiPacketListener(10);
    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new SpringHandlerMapping();
    }

    @Override
    public void afterPropertiesSet() {
        tcpServer.start();
    }

    @Override
    public void destroy() {
        tcpServer.stop();
    }
}