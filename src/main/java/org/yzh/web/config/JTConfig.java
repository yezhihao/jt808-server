package org.yzh.web.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.SpringHandlerMapping;
import org.yzh.framework.netty.NettyConfig;
import org.yzh.framework.netty.TCPServer;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.endpoint.JTHandlerInterceptor;

@Configuration
public class JTConfig implements InitializingBean, DisposableBean {

    @Autowired
    private TCPServer tcpServer;

    @Bean
    public TCPServer tcpServer() {
        NettyConfig jtConfig = new NettyConfig.Builder()
                .setPort(7611)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[]{0x7e})
                .setDecoder(new JTMessageDecoder("org.yzh.protocol"))
                .setEncoder(new JTMessageEncoder("org.yzh.protocol"))
                .setHandlerMapping(handlerMapping())
                .setHandlerInterceptor(handlerInterceptor())
                .build();
        return new TCPServer(jtConfig);
    }

    @Bean
    public JTHandlerInterceptor handlerInterceptor() {
        return new JTHandlerInterceptor();
    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new SpringHandlerMapping("org.yzh.web.endpoint");
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