package org.yzh.framework.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public enum MessageManager {

    Instance;

    public static MessageManager getInstance() {
        return Instance;
    }

    private static final Logger log = LoggerFactory.getLogger(MessageManager.class.getSimpleName());

    private Map<String, SynchronousQueue> topicSubscribers = new ConcurrentHashMap<>();

    private SessionManager sessionManager = SessionManager.getInstance();

    /**
     * 发送通知类消息，不接收响应
     */
    public boolean notify(AbstractMessage<? extends AbstractHeader> message) {
        AbstractHeader header = message.getHeader();
        String terminalId = header.getTerminalId();

        Session session = sessionManager.getByTerminalId(terminalId);
        if (session == null)
            return false;

        header.setSerialNo(session.nextSerialNo());
        session.getChannel().writeAndFlush(message);
        return true;
    }

    /**
     * 发送同步消息，接收响应
     * 默认超时时间20秒
     */
    public <T extends AbstractMessage> T request(AbstractMessage<? extends AbstractHeader> request, Class<T> clazz) {
        return request(request, clazz, 20000);
    }

    public <T extends AbstractMessage> T request(AbstractMessage<? extends AbstractHeader> request, Class<T> clazz, long timeout) {
        AbstractHeader header = request.getHeader();
        String terminalId = header.getTerminalId();

        Session session = sessionManager.getByTerminalId(terminalId);
        if (session == null)
            return null;

        header.setSerialNo(session.nextSerialNo());

        String key = getKey(header, clazz);
        SynchronousQueue synchronousQueue = this.subscribe(key);
        if (synchronousQueue == null)
            return null;

        try {
            session.getChannel().writeAndFlush(request);
            return (T) synchronousQueue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.warn("", e);
        } finally {
            this.unsubscribe(key);
        }
        return null;
    }

    /**
     * 消息响应
     */
    public boolean response(AbstractMessage message) {
        SynchronousQueue queue = topicSubscribers.get(getKey(message.getHeader(), message.getClass()));
        if (queue != null)
            return queue.offer(message);
        return false;
    }

    private String getKey(AbstractHeader header, Class clazz) {
        return header.getTerminalId() + "/" + clazz.getName();
    }

    private SynchronousQueue subscribe(String key) {
        SynchronousQueue queue = null;
        if (!topicSubscribers.containsKey(key))
            topicSubscribers.put(key, queue = new SynchronousQueue());
        return queue;
    }

    private void unsubscribe(String key) {
        topicSubscribers.remove(key);
    }
}
