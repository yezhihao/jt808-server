package org.yzh.framework.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.handler.Handler;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPServerHandler.class.getSimpleName());

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
        Session session = channel.attr(Session.KEY).get();
        session.access();

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
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = new Session(ctx.channel());
        log.info(">>>>>>>>>终端连接{}", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Session session = ctx.channel().attr(Session.KEY).get();
        log.info("<<<<<<<<<断开连接{}", session);
        session.invalidate();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        Session session = ctx.channel().attr(Session.KEY).get();
        log.info("<<<<<<<<<发生异常" + session, e);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();
            if (state == IdleState.READER_IDLE || state == IdleState.WRITER_IDLE) {
                Session session = ctx.channel().attr(Session.KEY).get();
                log.warn("服务器主动断开连接{}", session);
                ctx.close();
            }
        }
    }
}