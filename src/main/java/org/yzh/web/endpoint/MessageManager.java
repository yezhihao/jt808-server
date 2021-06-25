package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.protocol.basics.JTMessage;

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
            return false;

        request.setClientId(session.getClientId());
        request.setSerialNo(session.nextSerialNo());
        if (request.getMessageId() == 0) {
            request.setMessageId(request.reflectMessageId());
        }
        session.notify(request);
        return true;
    }

    public <T> T request(String sessionId, JTMessage request, Class<T> responseClass) {
        return request(sessionId, request, responseClass, 20000);
    }

    public <T> T request(String sessionId, JTMessage request, Class<T> responseClass, long timeout) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return null;

        request.setClientId(session.getClientId());
        request.setSerialNo(session.nextSerialNo());
        if (request.getMessageId() == 0) {
            request.setMessageId(request.reflectMessageId());
        }
        return session.request(request, responseClass, timeout);
    }
}