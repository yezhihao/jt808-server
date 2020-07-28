package org.yzh.framework.mvc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

import java.lang.reflect.Method;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步处理
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class AsyncHandler extends Handler {

    private static final Logger log = LoggerFactory.getLogger(AsyncHandler.class.getSimpleName());

    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(2, 8, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2000));

    public AsyncHandler(Object actionClass, Method actionMethod, String desc) {
        super(actionClass, actionMethod, desc);
    }

    @Override
    public void invoke(HandlerInterceptor interceptor, AbstractMessage request, Session session) {
        try {
            POOL_EXECUTOR.execute(() -> {
                try {
                    super.invoke(interceptor, request, session);
                } catch (Exception e) {
                    log.error("异步处理出错" + targetMethod, e);
                    interceptor.afterThrow(request, session, e);
                }
            });
        } catch (Exception e) {
            log.error("线程池溢出" + targetMethod, e);
            interceptor.queueOverflow(request, session);
        }
    }
}