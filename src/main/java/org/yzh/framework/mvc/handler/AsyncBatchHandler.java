package org.yzh.framework.mvc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.web.commons.JsonUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 异步批量处理
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class AsyncBatchHandler extends Handler {

    private static final Logger log = LoggerFactory.getLogger(AsyncBatchHandler.class.getSimpleName());

    private BlockingQueue<AbstractMessage> queue;

    private int capacity;

    private int maxElements;

    private int maxWait;

    public AsyncBatchHandler(Object actionClass, Method actionMethod, String desc, int capacity, int maxElements, int maxWait) {
        super(actionClass, actionMethod, desc);

        Class<?> parameterType = actionMethod.getParameterTypes()[0];
        if (!parameterType.isAssignableFrom(List.class))
            throw new RuntimeException("@AsyncBatch方法的参数不是List类型:" + actionMethod);

        this.capacity = capacity;
        this.maxElements = maxElements;
        this.maxWait = maxWait;
        this.queue = new LinkedBlockingQueue<>(capacity);
        new Thread(() -> {
            while (true) {
                try {
                    startInternal();
                } catch (Exception e) {
                    log.error("批处理线程出错", e);
                }
            }
        }, AsyncBatchHandler.class.getName()).start();
    }

    public void invoke(HandlerInterceptor interceptor, Session session, AbstractMessage messageRequest) throws Exception {
        if (!queue.offer(messageRequest)) {
            log.warn("超出队列处理能力,{}", JsonUtils.toJson(log));
            interceptor.queueOverflow(messageRequest, session);
        }
    }

    public void startInternal() {
        List<AbstractMessage> messageList = new ArrayList<>(maxElements);

        while (true) {
            int i = queue.drainTo(messageList, maxElements);
            if (i > 0) {
                try {
                    targetMethod.invoke(targetObject, messageList);
                } catch (Exception e) {
                    log.warn(targetMethod.getName(), e);
                }
                messageList.clear();
            }

            if (i < capacity)
                try {
                    Thread.sleep(maxWait);
                } catch (InterruptedException e) {
                }
        }
    }
}