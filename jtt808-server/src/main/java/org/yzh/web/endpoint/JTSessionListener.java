package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.model.Message;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionListener;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;

import java.util.function.BiConsumer;

public class JTSessionListener implements SessionListener {

    private static final BiConsumer<Session, Message> requestInterceptor = (session, message) -> {
        JTMessage request = (JTMessage) message;
        request.setClientId(session.getClientId());
        request.setSerialNo(session.nextSerialNo());

        if (request.getMessageId() == 0) {
            request.setMessageId(request.reflectMessageId());
        }

        DeviceInfo device = SessionKey.getDeviceInfo(session);
        if (device != null) {
            int protocolVersion = device.getProtocolVersion();
            if (protocolVersion > 0) {
                request.setVersion(true);
                request.setProtocolVersion(protocolVersion);
            }
        }
    };

    @Override
    public void sessionCreated(Session session) {
        session.requestInterceptor(requestInterceptor);
    }

    @Override
    public void sessionRegistered(Session session) {
    }

    @Override
    public void sessionDestroyed(Session session) {
    }
}