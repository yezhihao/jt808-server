package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;

/**
 * @author yezhihao
 * home https://gitee.com/yezhihao/jt808-server
 */
@Component
public class MessageManager {

    @Autowired
    private SessionManager sessionManager;

    public boolean notify(JTMessage request) {
        Session session = sessionManager.get(request.getClientId());
        if (session == null)
            return false;

        Header header = request.getHeader();
        header.setSerialNo(session.nextSerialNo());
        if (header.getMessageId() == 0) {
            header.setMessageId(request.reflectMessageId());
        }
        session.notify(request);
        return true;
    }

    public <T> T request(String clientId, JTMessage request, Class<T> responseClass) {
        Header header = request.getHeader();
        if (header == null) {
            request.setHeader(header = new Header());
        }
        header.setMobileNo(clientId);
        return request(request, responseClass, 20000);
    }

    public <T> T request(JTMessage request, Class<T> responseClass) {
        return request(request, responseClass, 20000);
    }

    public <T> T request(JTMessage request, Class<T> responseClass, long timeout) {
        Session session = sessionManager.get(request.getClientId());
        if (session == null)
            return null;

        Header header = request.getHeader();
        header.setSerialNo(session.nextSerialNo());
        if (header.getMessageId() == 0) {
            header.setMessageId(request.reflectMessageId());
        }
        return session.request(request, responseClass, timeout);
    }
}