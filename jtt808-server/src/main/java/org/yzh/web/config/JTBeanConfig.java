package org.yzh.web.config;

import io.github.yezhihao.netmc.core.HandlerMapping;
import io.github.yezhihao.netmc.core.SpringHandlerMapping;
import io.github.yezhihao.netmc.session.SessionListener;
import io.github.yezhihao.netmc.session.SessionManager;
import io.github.yezhihao.protostar.MultiVersionSchemaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.yzh.component.area.service.AreaService;
import org.yzh.protocol.codec.DataFrameMessageDecoder;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.codec.MultiPacketDecoder;
import org.yzh.web.endpoint.JTHandlerInterceptor;
import org.yzh.web.endpoint.JTMultiPacketListener;
import org.yzh.web.endpoint.JTSessionListener;
import org.yzh.web.mapper.DeviceStatusMapper;
import org.yzh.web.model.enums.SessionKey;

@Configuration
public class JTBeanConfig {

    private final SimpMessagingTemplate messagingTemplate;

    public JTBeanConfig(@Autowired(required = false) SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new SpringHandlerMapping();
    }

    @Bean
    public JTHandlerInterceptor handlerInterceptor() {
        return new JTHandlerInterceptor();
    }

    @Bean
    public SessionListener sessionListener(DeviceStatusMapper deviceStatusMapper, @Autowired(required = false) AreaService areaService) {
        return new JTSessionListener(deviceStatusMapper, areaService);
    }

    @Bean
    public SessionManager sessionManager(SessionListener sessionListener) {
        return new SessionManager(SessionKey.class, sessionListener);
    }

    @Bean
    public MultiVersionSchemaManager schemaManager() {
        return new MultiVersionSchemaManager("org.yzh.protocol", "org.yzh.web.model.protocol");
    }

    @Bean
    public JTMessageAdapter messageAdapter(MultiVersionSchemaManager schemaManager) {
        JTMessageEncoder encoder = new JTMessageEncoder(schemaManager);
        MultiPacketDecoder decoder = new MultiPacketDecoder(schemaManager, new JTMultiPacketListener(10));
        if (messagingTemplate == null)
            return new JTMessageAdapter(encoder, decoder);
        return new WebLogAdapter(encoder, decoder, messagingTemplate);
    }

    @Bean
    public JTMessageAdapter alarmFileMessageAdapter(MultiVersionSchemaManager schemaManager) {
        JTMessageEncoder encoder = new JTMessageEncoder(schemaManager);
        DataFrameMessageDecoder decoder = new DataFrameMessageDecoder(schemaManager, new byte[]{0x30, 0x31, 0x63, 0x64});
        if (messagingTemplate == null)
            return new JTMessageAdapter(encoder, decoder);
        return new WebLogAdapter(encoder, decoder, messagingTemplate);
    }
}