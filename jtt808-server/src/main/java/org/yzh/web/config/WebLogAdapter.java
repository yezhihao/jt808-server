package org.yzh.web.config;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.SchemaManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.commons.JT808;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebLogAdapter extends JTMessageAdapter {

    protected static final Logger log = LoggerFactory.getLogger(WebLogAdapter.class);

    public static final Map<String, Map<String, FluxSink<Object>>> clientIds = new HashMap<>();
    public static final Map<String, Map<String, FluxSink<Object>>> userIds = new HashMap<>();
    public static final HashSet<Integer> ignoreMsgs = new HashSet<>();

    static {
        ignoreMsgs.add(JT808.定位数据批量上传);
    }

    public WebLogAdapter(SchemaManager schemaManager) {
        super(schemaManager);
    }

    public WebLogAdapter(JTMessageEncoder messageEncoder, JTMessageDecoder messageDecoder) {
        super(messageEncoder, messageDecoder);
    }

    @Override
    public void encodeLog(Session session, JTMessage message, ByteBuf output) {
        Map<String, FluxSink<Object>> emitters = clientIds.get(message.getClientId());
        if (emitters != null) {
            ServerSentEvent<Object> event = ServerSentEvent.builder().event(message.getClientId())
                    .data(message + "hex:" + ByteBufUtil.hexDump(output, 0, output.writerIndex())).build();
            for (FluxSink<Object> emitter : emitters.values()) {
                emitter.next(event);
            }
        }
        if ((!ignoreMsgs.contains(message.getMessageId())) && (emitters != null || clientIds.isEmpty()))
            super.encodeLog(session, message, output);
    }

    @Override
    public void decodeLog(Session session, JTMessage message, ByteBuf input) {
        if (message != null) {
            Map<String, FluxSink<Object>> emitters = clientIds.get(message.getClientId());
            if (emitters != null) {
                ServerSentEvent<Object> event = ServerSentEvent.builder().event(message.getClientId())
                        .data(message + "hex:" + ByteBufUtil.hexDump(input, 0, input.writerIndex())).build();
                for (FluxSink<Object> emitter : emitters.values()) {
                    emitter.next(event);
                }
            }
            if (!ignoreMsgs.contains(message.getMessageId()) && (emitters != null || clientIds.isEmpty()))
                super.decodeLog(session, message, input);

            if (!message.isVerified())
                log.error("<<<<<校验码错误session={},payload={}", session, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
        }
    }

    public static void clearMessage() {
        synchronized (ignoreMsgs) {
            ignoreMsgs.clear();
        }
    }

    public static void addMessage(int messageId) {
        if (!ignoreMsgs.contains(messageId)) {
            synchronized (ignoreMsgs) {
                ignoreMsgs.add(messageId);
            }
        }
    }

    public static void removeMessage(int messageId) {
        if (ignoreMsgs.contains(messageId)) {
            synchronized (ignoreMsgs) {
                ignoreMsgs.remove(messageId);
            }
        }
    }

    public static Flux<Object> addClient(String userId, String clientId) {
        synchronized (userIds) {
            Map<String, FluxSink<Object>> clients = userIds.computeIfAbsent(userId, s -> new ConcurrentHashMap<>());
            if (clients.isEmpty()) {
                return Flux.create(emitter -> {
                    clients.put(clientId, emitter);
                    clientIds.computeIfAbsent(clientId, k -> new ConcurrentHashMap<>()).put(userId, emitter);

                }).doFinally(signalType -> clients.keySet().forEach(cid -> removeClient(userId, cid)));
            } else {
                for (FluxSink emitter : clients.values()) {
                    clients.put(clientId, emitter);
                    clientIds.computeIfAbsent(clientId, k -> new ConcurrentHashMap<>()).put(userId, emitter);
                    break;
                }
            }
        }
        return null;
    }

    public static void removeClient(String userId, String clientId) {
        synchronized (userIds) {
            Map<String, FluxSink<Object>> clients = userIds.get(userId);
            Map<String, FluxSink<Object>> users = clientIds.get(clientId);
            if (clients == null || users == null)
                return;
            clients.remove(clientId);
            if (clients.isEmpty()) userIds.remove(userId);
            users.remove(userId);
            if (users.isEmpty()) clientIds.remove(clientId);
        }
    }
}