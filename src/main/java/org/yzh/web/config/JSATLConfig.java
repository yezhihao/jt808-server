package org.yzh.web.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.codec.Delimiter;
import org.yzh.framework.codec.LengthField;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.SpringHandlerMapping;
import org.yzh.framework.netty.NettyConfig;
import org.yzh.framework.netty.TCPServer;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.SessionListener;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.codec.DataFrameMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.jsatl12.DataPacket;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTMultiPacketListener;
import org.yzh.web.endpoint.JTSessionListener;

@Configuration
@ConditionalOnProperty(value = "tpc-server.alarm-file.enable", havingValue = "true")
public class JSATLConfig implements InitializingBean, DisposableBean {

    public static Class<? extends JTMessage> DataFrameClass = DataPacket.class;

    public static byte[] DataFramePrefix = {0x30, 0x31, 0x63, 0x64};

    @Value("${tpc-server.alarm-file.port}")
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
                .setDecoder(alarmFileMessageDecoder())
                .setEncoder(alarmFileMessageEncoder())
                .setSessionManager(alarmFileSessionManager())
                .setHandlerMapping(alarmFileHandlerMapping())
                .setHandlerInterceptor(alarmFileHandlerInterceptor())
                .setMultiPacketListener(alarmFileMultiPacketListener())
                .build();
        return new TCPServer("报警附件服务", jtConfig);
    }

    @Bean
    public MessageDecoder alarmFileMessageDecoder() {
        return new DataFrameMessageDecoder("org.yzh.protocol", DataFrameClass, DataFramePrefix);
    }

    @Bean
    public MessageEncoder alarmFileMessageEncoder() {
        return new JTMessageEncoder("org.yzh.protocol");
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

    @Bean
    public JTMultiPacketListener alarmFileMultiPacketListener() {
        return new JTMultiPacketListener(10);
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