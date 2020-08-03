package org.yzh.framework.mvc.handler;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.VirtualList;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步批量处理
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class AsyncBatchHandler extends Handler {

    private static final Logger log = LoggerFactory.getLogger(AsyncBatchHandler.class.getSimpleName());

    private ConcurrentLinkedQueue<AbstractMessage> queue;

    private ThreadPoolExecutor executor;

    private int poolSize;

    private int maxElements;

    private int maxWait;

    private int warningLines;

    public AsyncBatchHandler(Object actionClass, Method actionMethod, String desc, int poolSize, int maxElements, int maxWait) {
        super(actionClass, actionMethod, desc);

        Class<?>[] parameterTypes = actionMethod.getParameterTypes();
        if (parameterTypes.length > 1)
            throw new RuntimeException("@AsyncBatch方法仅支持一个List参数:" + actionMethod);
        if (!parameterTypes[0].isAssignableFrom(List.class))
            throw new RuntimeException("@AsyncBatch方法的参数不是List类型:" + actionMethod);

        this.poolSize = poolSize;
        this.maxElements = maxElements;
        this.maxWait = maxWait;
        this.warningLines = maxElements * poolSize * 50;

        this.queue = new ConcurrentLinkedQueue();
        this.executor = new ThreadPoolExecutor(this.poolSize, this.poolSize, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(400),
                new BasicThreadFactory.Builder().namingPattern(actionMethod.getName() + "-pool-%d").build());

        for (int i = 0; i < poolSize; i++) {
            boolean master = i == 0;
            executor.execute(() -> {
                try {
                    startInternal(master);
                } catch (Exception e) {
                    log.error("批处理线程出错", e);
                }
            });
        }
    }

    public void invoke(HandlerInterceptor interceptor, AbstractMessage request, Session session) throws Exception {
        if (queue.offer(request)) {
            interceptor.afterHandle(request, session);
        } else {
            interceptor.queueOverflow(request, session);
        }
    }

    public void startInternal(boolean master) {
        AbstractMessage[] array = new AbstractMessage[maxElements];

        while (true) {
            AbstractMessage temp;
            int i = 0;
            while ((temp = queue.poll()) != null) {
                array[i++] = temp;
                if (i >= maxElements)
                    break;
            }

            if (i > 0) {
                try {
                    long time = System.currentTimeMillis();
                    targetMethod.invoke(targetObject, new VirtualList<>(array, i));
                    time = System.currentTimeMillis() - time;
                    log.warn("批处理耗时:{}ms,共{}条记录", time, i);
                } catch (Exception e) {
                    log.warn(targetMethod.getName(), e);
                }
            }

            if (master) {
                int size = queue.size();
                if (size < maxElements) {
                    try {
                        log.warn("批处理队列等待数据中...size:{}", size);
                        for (int j = 0; j < i; j++)
                            array[j] = null;
                        Thread.sleep(maxWait);
                    } catch (InterruptedException e) {
                    }
                } else if (size > warningLines) {
                    log.warn("批处理队列繁忙, size:{}", size);
                }
            } else {
                if (i < maxElements) {
                    try {
                        for (int j = 0; j < i; j++)
                            array[j] = null;
                        Thread.sleep(maxWait);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }
}