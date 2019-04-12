package org.yzh.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.TCPServer;
import org.yzh.framework.mapping.HandlerMapper;
import org.yzh.framework.spring.SpringHandlerMapper;

@Configuration
public class NettyConfig {

    @Bean
    public TCPServer TCPServer() {
        TCPServer server = new TCPServer(7611, (byte) 0x7e, handlerMapper());
        server.startServer();
        return server;
    }

    @Bean
    public HandlerMapper handlerMapper() {
        return new SpringHandlerMapper("org.yzh.web.endpoint");
    }
}