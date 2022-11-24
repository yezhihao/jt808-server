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
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WebLogAdapter extends JTMessageAdapter {

    protected static final Logger log = LoggerFactory.getLogger(WebLogAdapter.class.getSimpleName());

    public static final HashMap<String, Set<FluxSink<Object>>> clientIds = new HashMap<>();
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
        Set<FluxSink<Object>> emitters = clientIds.get(message.getClientId());
        if (emitters != null) {
            ServerSentEvent<Object> event = ServerSentEvent.builder().event(message.getClientId())
                    .data(message + "hex:" + ByteBufUtil.hexDump(output, 0, output.writerIndex())).build();
            for (FluxSink<Object> emitter : emitters) {
                emitter.next(event);
            }
        }
        if ((!ignoreMsgs.contains(message.getMessageId())) && (emitters != null || clientIds.isEmpty()))
            super.encodeLog(session, message, output);
    }

    @Override
    public void decodeLog(Session session, JTMessage message, ByteBuf input) {
        if (message != null) {
            Set<FluxSink<Object>> emitters = clientIds.get(message.getClientId());
            if (emitters != null) {
                ServerSentEvent<Object> event = ServerSentEvent.builder().event(message.getClientId())
                        .data(message + "hex:" + ByteBufUtil.hexDump(input, 0, input.writerIndex())).build();
                for (FluxSink<Object> emitter : emitters) {
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

    public static void clearClient() {
        synchronized (clientIds) {
            clientIds.clear();
        }
    }

    public static void addClient(String clientId, FluxSink<Object> emitter) {
        synchronized (clientIds) {
            clientIds.computeIfAbsent(clientId, k -> new HashSet<>()).add(emitter);
        }
    }

    public static void removeClient(String clientId, FluxSink<Object> emitter) {
        synchronized (clientIds) {
            Set<FluxSink<Object>> emitters = clientIds.get(clientId);
            if (emitters != null) {
                emitters.remove(emitter);
                if (emitters.isEmpty()) {
                    clientIds.remove(clientId);
                }
            }
        }
    }
}