package org.yzh.web.component;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.yzh.web.config.SessionKey;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private static final Map<Integer, WebSocketSession> webSocketMap = new ConcurrentHashMap();

    public void closeSession(Integer id) {
        WebSocketSession socketSession = webSocketMap.get(id);
        if (socketSession != null) {
            try {
                socketSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消息广播
     */
    public void broadcast(String message) {
        for (WebSocketSession socketSession : webSocketMap.values()) {
            send(socketSession, message);
        }
    }

    public void send(WebSocketSession socketSession, String message) {
        try {
            socketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer id = (Integer) session.getAttributes().get(SessionKey.USER_ID);
        webSocketMap.put(id, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (message.getPayloadLength() < 1)
            return;
        for (WebSocketSession socketSession : webSocketMap.values())
            socketSession.sendMessage(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable error) {
        error.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        Integer id = (Integer) session.getAttributes().get(SessionKey.USER_ID);
        webSocketMap.remove(id);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}