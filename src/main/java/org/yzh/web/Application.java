package org.yzh.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.yzh.framework.JTApplication;
import org.yzh.framework.core.JTConfig;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.codec.JT808MessageEncoder;

@EnableWebSocket
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        JTConfig jtConfig = new JTConfig.Builder()
                .setPort(7611)
                .setMaxFrameLength(1024)
                .setDelimiters(new byte[]{0x7e}, new byte[]{0x7e, 0x7e})
                .setDecoder(new JT808MessageDecoder())
                .setEncoder(new JT808MessageEncoder())
                .build();
        JTApplication.run(Application.class, jtConfig);

        SpringApplication.run(Application.class, args);
        log.info("***启动成功***");
    }
}