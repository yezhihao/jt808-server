package org.yzh.framework.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.Handler;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;

import java.lang.reflect.Type;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPServerHandler.class.getSimpleName());

    private final SessionManager sessionManager = SessionManager.getInstance();

    private HandlerMapping handlerMapping;

    public TCPServerHandler(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            AbstractMessage messageRequest = (AbstractMessage) msg;
            AbstractHeader header = messageRequest.getHeader();
            Channel channel = ctx.channel();

            Handler handler = handlerMapping.getHandler(header.getMessageId());

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