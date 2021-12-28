package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yzh.commons.model.APIException;
import org.yzh.commons.model.APIResult;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Component
public class MessageManager {

    private static final Logger log = LoggerFactory.getLogger(MessageManager.class);

    private static final Mono<Void> NEVER = Mono.never();
    private static final Mono OFFLINE_EXCEPTION = Mono.error(new APIException(4000, "离线的客户端"));
    private static final Mono OFFLINE_RESULT = Mono.just(new APIResult<>(4000, "离线的客户端"));
    private static final Mono SENDFAIL_RESULT = Mono.just(new APIResult<>(4001, "消息发送失败"));
    private static final Mono TIMEOUT_RESULT = Mono.just(new APIResult<>(4002, "消息发送成功,客户端响应超时"));

    private SessionManager sessionManager;

    public MessageManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Mono<Void> notifyR(String sessionId, JTMessage request) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return OFFLINE_EXCEPTION;

        fillHeader(request, session);
        return session.notify(request);
    }

    public Mono<Void> notify(String sessionId, JTMessage request) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return NEVER;

        fillHeader(request, session);
        return session.notify(request);
    }

    public <T> Mono<APIResult<T>> requestR(String sessionId, JTMessage request, Class<T> responseClass) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return OFFLINE_RESULT;

        fillHeader(request, session);
        return session.request(request, responseClass)
                .map(message -> APIResult.ok(message))
                .timeout(Duration.ofSeconds(10), TIMEOUT_RESULT)
                .onErrorResume(e -> {
                    log.warn("消息发送失败", e);
                    return SENDFAIL_RESULT;
                });
    }

    public <T> Mono<T> request(String sessionId, JTMessage request, Class<T> responseClass, long timeout) {
        return request(sessionId, request, responseClass).timeout(Duration.ofMillis(timeout));
    }

    public <T> Mono<T> request(String sessionId, JTMessage request, Class<T> responseClass) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return OFFLINE_EXCEPTION;

        fillHeader(request, session);
        return session.request(request, responseClass);
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