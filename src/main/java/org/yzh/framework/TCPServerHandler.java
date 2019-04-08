package org.yzh.framework;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.mapping.Handler;
import org.yzh.framework.mapping.HandlerMapper;
import org.yzh.framework.message.PackageData;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.web.jt808.dto.basics.Header;

@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TCPServerHandler.class.getSimpleName());

    private final SessionManager sessionManager = SessionManager.getInstance();

    private HandlerMapper handlerMapper;

    private MessageDecoder decoder;

    private MessageEncoder encoder;

    private byte delimiter;

    public TCPServerHandler(byte delimiter, MessageDecoder decoder, MessageEncoder encoder, HandlerMapper handlerMapper) {
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapper = handlerMapper;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf headerBodyBuf = (ByteBuf) msg;
            if (headerBodyBuf.readableBytes() <= 0)
                return;

            logger.info("ip= {}", ctx.channel().remoteAddress());

            int type = decoder.getType(headerBodyBuf);

            Handler handler = handlerMapper.getHandler(type);
            if (handler == null) {
                logger.info("未知消息={}", type);
                return;
            }

            Class<?>[] types = handler.getTargetParameterTypes();
            Class<? extends PackageData> targetClass = (Class<? extends PackageData>) types[0];

            PackageData packageData = decoder.decode(headerBodyBuf, Header.class, targetClass);

            logger.info("{}in,hex:{}\n{}", handler, ByteBufUtil.hexDump(headerBodyBuf), packageData);

            Object result;
            if (types.length == 1) {
                result = handler.invoke(packageData);
            } else {
                result = handler.invoke(packageData, sessionManager.getBySessionId(Session.buildId(ctx.channel())));
            }

            if (result == null)
                return;
            ByteBuf resultBuf = encoder.encode((PackageData) result);
            logger.info("{}out,hex:{}\n{}", handler, ByteBufUtil.hexDump(resultBuf), result);
            ByteBuf allResultBuf = Unpooled.wrappedBuffer(Unpooled.wrappedBuffer(new byte[]{delimiter}), resultBuf, Unpooled.wrappedBuffer(new byte[]{delimiter}));
            ChannelFuture future = ctx.channel().writeAndFlush(allResultBuf).sync();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        logger.error("发生异常:{}", cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        logger.debug("终端连接:{}", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final String sessionId = Session.buildId(ctx.channel());
        Session session = sessionManager.getBySessionId(sessionId);
        this.sessionManager.removeBySessionId(sessionId);
        logger.debug("终端断开连接:{}", session);
        ctx.channel().close();
        // ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                logger.error("服务器主动断开连接:{}", session);
                ctx.close();
            }
        }
    }
}