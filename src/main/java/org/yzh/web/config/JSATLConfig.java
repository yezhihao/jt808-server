package org.yzh.web.config;

import io.github.yezhihao.netmc.NettyConfig;
import io.github.yezhihao.netmc.TCPServer;
import io.github.yezhihao.netmc.codec.Delimiter;
import io.github.yezhihao.netmc.codec.LengthField;
import io.github.yezhihao.netmc.core.HandlerMapping;
import io.github.yezhihao.netmc.core.SpringHandlerMapping;
import io.github.yezhihao.netmc.session.MessageManager;
import io.github.yezhihao.netmc.session.SessionListener;
import io.github.yezhihao.netmc.session.SessionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.protocol.codec.DataFrameMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.component.adapter.JTMessageAdapter;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTSessionListener;

@Configuration
@ConditionalOnProperty(value = "tcp-server.alarm-file.enable", havingValue = "true")
public class JSATLConfig implements InitializingBean, DisposableBean {

    public static byte[] DataFramePrefix = {0x30, 0x31, 0x63, 0x64};

    @Value("${tcp-server.alarm-file.port}")
    private int port;

    @Autowired
    private TCPServer alarmFileServer;

    @Bean
    public TCPServer alarmFileServer() {
        NettyConfig jtConfig = NettyConfig.custom()
                .setPort(port)
                .setMaxFrameLength(2 + 21 + 1023 + 2)
                .setLengthField(new LengthField(DataFramePrefix, 1024 * 65, 58, 4))
                .setDelimiters(new Delimiter(new byte[]{0x7e}))
                .setDecoder(alarmFileMessageAdapter())
                .setEncoder(alarmFileMessageAdapter())
                .setSessionManager(alarmFileSessionManager())
                .setHandlerMapping(alarmFileHandlerMapping())
                .setHandlerInterceptor(alarmFileHandlerInterceptor())
                .build();
        return new TCPServer("报警附件服务", jtConfig);
    }

    @Bean
    public JTMessageAdapter alarmFileMessageAdapter() {
        return new JTMessageAdapter(
                new JTMessageEncoder("org.yzh.protocol"),
                new DataFrameMessageDecoder("org.yzh.protocol", DataFramePrefix)
        );
    }

    @Bean
    public MessageManager alarmFileMessageManager() {
        return new MessageManager(alarmFileSessionManager());
    }

    @Bean
    public SessionManager alarmFileSessionManager() {
        return new SessionManager(alarmFileSessionListener());
    }

    @Bean
    public SessionListener alarmFileSessionListener() {
        return new JTSessionListener();
    }

    @Bean
    public HandlerMapping alarmFileHandlerMapping() {
        return new SpringHandlerMapping();
    }

    @Bean
    public JTHandlerInterceptor alarmFileHandlerInterceptor() {
        return new JTHandlerInterceptor();
    }

    @Override
    public void afterPropertiesSet() {
        alarmFileServer.start();
    }

    @Override
    public void destroy() {
        alarmFileServer.stop();
    }
}