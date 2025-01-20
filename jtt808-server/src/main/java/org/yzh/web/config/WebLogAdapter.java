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

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebLogAdapter extends JTMessageAdapter {

    protected static final Logger log = LoggerFactory.getLogger(WebLogAdapter.class);

    public static final ConcurrentHashMap<String, Map<String, FluxSink<Object>>> TOPICS = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Map<String, FluxSink<Object>>> USERS = new ConcurrentHashMap<>();
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
        Map<String, FluxSink<Object>> emitters = TOPICS.get(message.getClientId());
        if (emitters != null) {
            ServerSentEvent<Object> event = ServerSentEvent.builder().event(message.getClientId())
                    .data(message + "hex:" + ByteBufUtil.hexDump(output, 0, output.writerIndex())).build();
            for (FluxSink<Object> emitter : emitters.values()) {
                emitter.next(event);
            }
        }
        if ((!ignoreMsgs.contains(message.getMessageId())) && (emitters != null || TOPICS.isEmpty()))
            super.encodeLog(session, message, output);
    }

    @Override
    public void decodeLog(Session session, JTMessage message, ByteBuf input) {
        if (message != null) {
            Map<String, FluxSink<Object>> emitters = TOPICS.get(message.getClientId());
            if (emitters != null) {
                ServerSentEvent<Object> event = ServerSentEvent.builder().event(message.getClientId())
                        .data(message + "hex:" + ByteBufUtil.hexDump(input, 0, input.writerIndex())).build();
                for (FluxSink<Object> emitter : emitters.values()) {
                    emitter.next(event);
                }
            }
            if (!ignoreMsgs.contains(message.getMessageId()) && (emitters != null || TOPICS.isEmpty()))
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

    public static boolean addClient(String user, String topic) {
        Map<String, FluxSink<Object>> result = USERS.computeIfPresent(user, (s, topics) -> {
            for (FluxSink<Object> emitter : topics.values()) {
                topics.put(topic, emitter);
                TOPICS.computeIfAbsent(topic, k -> new ConcurrentHashMap<>()).put(user, emitter);
                break;
            }
            return topics;
        });
        return result != null;
    }

    public static void removeClient(String user, String topic) {
        Map<String, FluxSink<Object>> topics = USERS.get(user);
        if (topics != null) {
            topics.remove(topic);
            if (topics.isEmpty())
                USERS.remove(user);
        }

        Map<String, FluxSink<Object>> users = TOPICS.get(topic);
        if (users == null) {
            users.remove(user);
            if (users.isEmpty())
                TOPICS.remove(topic);
        }
    }

    public static Flux<Object> connect(String user) {
        ConcurrentHashMap<String, FluxSink<Object>> topics = new ConcurrentHashMap<>();
        if (USERS.putIfAbsent(user, topics) == null) {
            return Flux.create(emitter -> {
                topics.put("0", emitter);
                TOPICS.computeIfAbsent("0", k -> new ConcurrentHashMap<>()).put(user, emitter);

            }).doFinally(signalType -> topics.keySet().forEach(t -> removeClient(user, t)));
        }
        return Flux.empty();
    }
}