package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.commons.model.APICodes;
import org.yzh.commons.model.APIException;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;

/**
 * @author yezhihao
 * home https://gitee.com/yezhihao/jt808-server
 */
@Component
public class MessageManager {

    @Autowired
    private SessionManager sessionManager;

    public boolean notify(String sessionId, JTMessage request) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            throw new APIException(APICodes.OfflineClient);

        fillHeader(request, session);
        session.notify(request);
        return true;
    }

    public <T> T request(String sessionId, JTMessage request, Class<T> responseClass) {
        return request(sessionId, request, responseClass, 20000);
    }

    public <T> T request(String sessionId, JTMessage request, Class<T> responseClass, long timeout) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            throw new APIException(APICodes.OfflineClient);

        fillHeader(request, session);
        return session.request(request, responseClass, timeout);
    }

    private static void fillHeader(JTMessage request, Session session) {
        request.setClientId(session.getClientId());
        request.setSerialNo(session.nextSerialNo());

        DeviceInfo device = SessionKey.getDeviceInfo(session);
        int protocolVersion = device.getProtocolVersion();
        if (protocolVersion > 0) {
            request.setVersion(true);
            request.setProtocolVersion(protocolVersion);
        }
        if (request.getMessageId() == 0) {
            request.setMessageId(request.reflectMessageId());
        }
    }
}