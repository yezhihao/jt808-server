package org.yzh.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.yzh.framework.JTApplication;
import org.yzh.framework.core.JTConfig;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.SpringHandlerMapping;
import org.yzh.web.jt.codec.JTMessageDecoder;
import org.yzh.web.jt.codec.JTMessageEncoder;

@EnableWebSocket
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        log.info("***Spring 启动成功***");
    }

    @Bean
    public ApplicationRunner runner() {
        return arguments -> {
            JTConfig jtConfig = new JTConfig.Builder()
                    .setPort(7611)
                    .setMaxFrameLength(1024)
                    .setDelimiters(new byte[]{0x7e})
                    .setHandlerMapping(handlerMapping())
                    .setDecoder(new JTMessageDecoder())
                    .setEncoder(new JTMessageEncoder())
                    .build();
            JTApplication.run(Application.class, jtConfig);
            System.out.println("***Netty 启动成功***");
        };
    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new SpringHandlerMapping();
    }
}