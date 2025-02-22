package org.yzh.commons.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.yzh.commons.util.Exceptions;
import org.yzh.commons.util.StrUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@ConditionalOnClass(name = "reactor.core.publisher.Flux")
public class SSEService implements ApplicationRunner {

    public final ConcurrentHashMap<String, Map<String, FluxSink<Object>>> EVENTS = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, Map<String, FluxSink<Object>>> USERS = new ConcurrentHashMap<>();

    public void send(String event, Object message) {
        Map<String, FluxSink<Object>> emitters = EVENTS.get(event);
        if (emitters != null) {
            ServerSentEvent<Object> sse = ServerSentEvent.builder().event(event).data(message).build();
            for (FluxSink<Object> emitter : emitters.values()) {
                emitter.next(sse);
            }
        }
    }

    public void send(String user, String event, Object message) {
        Map<String, FluxSink<Object>> emitters = USERS.get(user);
        if (emitters != null) {
            ServerSentEvent<Object> sse = ServerSentEvent.builder().event(event).data(message).build();
            for (FluxSink<Object> emitter : emitters.values()) {
                emitter.next(sse);
                break;
            }
        }
    }

    public void broadcast(String event, Object message) {
        ServerSentEvent<Object> sse = ServerSentEvent.builder().event(event).data(message).build();
        for (Map<String, FluxSink<Object>> user : USERS.values()) {
            for (FluxSink<Object> emitter : user.values()) {
                emitter.next(sse);
                break;
            }
        }
    }

    public void delEvent(String user, String event) {
        if (user == null || event == null)
            return;
        Map<String, FluxSink<Object>> events = USERS.get(user);
        if (events != null) {
            events.remove(event);
            if (events.isEmpty())
                USERS.remove(user);
        }
        Map<String, FluxSink<Object>> users = EVENTS.get(event);
        if (users != null) {
            users.remove(user);
            if (users.isEmpty())
                EVENTS.remove(event);
        }
    }

    public boolean addEvent(String user, String event) {
        if (user == null || event == null)
            return false;
        Map<String, FluxSink<Object>> result = USERS.computeIfPresent(user, (s, events) -> {
            for (FluxSink<Object> emitter : events.values()) {
                events.put(event, emitter);
                EVENTS.computeIfAbsent(event, k -> new ConcurrentHashMap<>()).put(user, emitter);
                break;
            }
            return events;
        });
        return result != null;
    }

    public Flux<Object> connect() {
        String userId = StrUtils.simpleUUID();
        ConcurrentHashMap<String, FluxSink<Object>> events = new ConcurrentHashMap<>();
        if (USERS.putIfAbsent(userId, events) == null) {
            return Flux.create(emitter -> {
                        emitter.onDispose(() -> events.keySet().forEach(t -> delEvent(userId, t)));
                        events.put("0", emitter);
                        EVENTS.computeIfAbsent("0", k -> new ConcurrentHashMap<>()).put(userId, emitter);
                        emitter.next(ServerSentEvent.builder().event("start").data(userId).build());
                    }).doFinally(s -> Exceptions.sneaky(() -> events.keySet().forEach(t -> delEvent(userId, t))))
                    .timeout(Duration.ofSeconds(60));
        }
        return Flux.just("repeated");
    }

    @Override
    public void run(ApplicationArguments args) {
        Thread thread = new Thread(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(Duration.ofSeconds(30).toMillis());
                    broadcast("heartbeat", null);
                } catch (Throwable ignored) {
                }
            }
        });
        thread.setName(Thread.currentThread().getName() + "-SSEService");
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }
}
