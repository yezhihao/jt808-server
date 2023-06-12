package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.model.Message;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionListener;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.enums.SessionKey;

import java.util.function.BiConsumer;

public class JTSessionListener implements SessionListener {

    /**
     * 下行消息拦截器
     */
    private static final BiConsumer<Session, Message> requestInterceptor = (session, message) -> {
        JTMessage request = (JTMessage) message;
        request.setClientId(session.getClientId());
        request.setSerialNo(session.nextSerialNo());

        if (request.getMessageId() == 0) {
            request.setMessageId(request.reflectMessageId());
        }

        DeviceDO device = SessionKey.getDevice(session);
        if (device != null) {
            int protocolVersion = device.getProtocolVersion();
            if (protocolVersion > 0) {
                request.setVersion(true);
                request.setProtocolVersion(protocolVersion);
            }
        }
    };

    /**
     * 设备连接
     */
    @Override
    public void sessionCreated(Session session) {
        session.requestInterceptor(requestInterceptor);
    }

    /**
     * 设备注册
     */
    @Override
    public void sessionRegistered(Session session) {
    }

    /**
     * 设备离线
     */
    @Override
    public void sessionDestroyed(Session session) {
    }
}