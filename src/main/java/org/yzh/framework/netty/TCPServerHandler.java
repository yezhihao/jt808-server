package org.yzh.framework.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPServerHandler.class.getSimpleName());

    private final SessionManager sessionManager = SessionManager.getInstance();

    private HandlerMapping handlerMapping;

    private HandlerInterceptor interceptor;

    public TCPServerHandler(HandlerMapping handlerMapping, HandlerInterceptor interceptor) {
        this.handlerMapping = handlerMapping;
        this.interceptor = interceptor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof AbstractMessage))
            return;
        AbstractMessage request = (AbstractMessage) msg;
        Channel channel = ctx.channel();
        Session session = sessionManager.getBySessionId(Session.buildId(channel));

        try {
            AbstractHeader header = request.getHeader();
            Handler handler = handlerMapping.getHandler(header.getMessageId());

            if (handler == null) {
                interceptor.notFoundHandle(request, session);
                return;
            }

            handler.invoke(interceptor, request, session);

        } catch (Exception e) {
            log.warn(String.valueOf(request), e);
            interceptor.afterThrow(request, session, e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        log.info("终端连接", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String sessionId = Session.buildId(ctx.channel());
        Session session = sessionManager.removeBySessionId(sessionId);
        log.info("断开连接", session);
        ctx.channel().close();
        // ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        String sessionId = Session.buildId(ctx.channel());
        Session session = sessionManager.getBySessionId(sessionId);
        log.info("发生异常", session);
        e.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                log.info("服务器主动断开连接", session);
                ctx.close();
            }
        }
    }
}