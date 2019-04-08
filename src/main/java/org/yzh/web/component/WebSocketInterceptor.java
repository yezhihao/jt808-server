package org.yzh.web.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.yzh.web.config.SessionKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {

    @Autowired
    private WebSocketMessageHandler webSocketMessageHandler;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = servletRequest.getSession();
            Integer id = (Integer) session.getAttribute(SessionKey.USER_ID);
            if (id != null) {
                webSocketMessageHandler.closeSession(id);
                attributes.put(SessionKey.USER_ID, id);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}