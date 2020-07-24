package org.yzh.framework.session;

import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public enum MessageManager {

    Instance;

    private Map<String, SyncFuture> topicSubscribers = new ConcurrentHashMap<>();

    private SessionManager sessionManager = SessionManager.getInstance();

    public boolean notify(AbstractMessage<? extends AbstractHeader> message) {
        AbstractHeader header = message.getHeader();
        String terminalId = header.getTerminalId();

        Session session = sessionManager.getByMobileNo(terminalId);
        if (session == null)
            return false;

        header.setSerialNo(session.currentFlowId());
        session.getChannel().writeAndFlush(message);
        return true;
    }

    public Object request(AbstractMessage<? extends AbstractHeader> message) {
        return request(message, 20000);
    }

    public Object request(AbstractMessage<? extends AbstractHeader> message, long timeout) {
        AbstractHeader header = message.getHeader();
        String terminalId = header.getTerminalId();

        Session session = sessionManager.getByMobileNo(terminalId);
        if (session == null)
            return null;

        String key = getKey(header);
        SyncFuture future = this.subscribe(key);
        if (future == null)
            return null;

        try {
            header.setSerialNo(session.currentFlowId());
            session.getChannel().writeAndFlush(message);
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            this.unsubscribe(key);
        }
        return null;
    }


    public boolean response(AbstractMessage message) {
        SyncFuture future = topicSubscribers.get(getKey(message.getHeader()));
        if (future != null)
            return future.set(message);
        return false;
    }

    private String getKey(AbstractHeader header) {
        return header.getTerminalId() + "/" + header.getSerialNo();
    }

    private SyncFuture subscribe(String key) {
        SyncFuture future = null;
        if (!topicSubscribers.containsKey(key))
            topicSubscribers.put(key, future = new SyncFuture());
        return future;
    }

    private void unsubscribe(String key) {
        topicSubscribers.remove(key);
    }
}
