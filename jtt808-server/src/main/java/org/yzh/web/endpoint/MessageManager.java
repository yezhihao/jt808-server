package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yzh.commons.model.APIException;
import org.yzh.commons.model.APIResult;
import org.yzh.protocol.basics.JTMessage;
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
    private static final Mono OFFLINE_EXCEPTION = Mono.error(new APIException(4000, "离线的客户端（请检查设备是否注册或者鉴权）"));
    private static final Mono OFFLINE_RESULT = Mono.just(new APIResult<>(4000, "离线的客户端（请检查设备是否注册或者鉴权）"));
    private static final Mono SENDFAIL_RESULT = Mono.just(new APIResult<>(4001, "消息发送失败"));
    private static final Mono TIMEOUT_RESULT = Mono.just(new APIResult<>(4002, "消息发送成功,客户端响应超时（至于设备为什么不应答，请联系设备厂商）"));

    private SessionManager sessionManager;

    public MessageManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Mono<Void> notifyR(String sessionId, JTMessage request) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return OFFLINE_EXCEPTION;

        return session.notify(request);
    }

    public Mono<Void> notify(String sessionId, JTMessage request) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return NEVER;

        return session.notify(request);
    }

    public <T> Mono<APIResult<T>> requestR(String sessionId, JTMessage request, Class<T> responseClass) {
        Session session = sessionManager.get(sessionId);
        if (session == null)
            return OFFLINE_RESULT;

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

        return session.request(request, responseClass);
    }
}