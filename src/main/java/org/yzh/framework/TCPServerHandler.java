package org.yzh.framework;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.yzh.framework.log.Logger;
import org.yzh.framework.mapping.Handler;
import org.yzh.framework.mapping.HandlerMapper;
import org.yzh.framework.message.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;

import java.lang.reflect.Type;

public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private final SessionManager sessionManager = SessionManager.getInstance();

    private Logger logger;

    private HandlerMapper handlerMapper;

    public TCPServerHandler(HandlerMapper handlerMapper) {
        this.handlerMapper = handlerMapper;
        this.logger = new Logger();
    }

    public TCPServerHandler(HandlerMapper handlerMapper, Logger logger) {
        this.handlerMapper = handlerMapper;
        this.logger = logger;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            AbstractMessage messageRequest = (AbstractMessage) msg;
            Channel channel = ctx.channel();

            Handler handler = handlerMapper.getHandler(messageRequest.getType());

            Type[] types = handler.getTargetParameterTypes();
            Session session = sessionManager.getBySessionId(Session.buildId(channel));

            AbstractMessage messageResponse;
            if (types.length == 1) {
                messageResponse = handler.invoke(messageRequest);
            } else {
                messageResponse = handler.invoke(messageRequest, session);
            }

            if (messageResponse != null) {
                ChannelFuture future = channel.writeAndFlush(messageResponse).sync();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        logger.logEvent("终端连接", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String sessionId = Session.buildId(ctx.channel());
        Session session = sessionManager.removeBySessionId(sessionId);
        logger.logEvent("断开连接", session);
        ctx.channel().close();
        // ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        String sessionId = Session.buildId(ctx.channel());
        Session session = sessionManager.getBySessionId(sessionId);
        logger.logEvent("发生异常", session);
        e.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                logger.logEvent("服务器主动断开连接", session);
                ctx.close();
            }
        }
    }
}