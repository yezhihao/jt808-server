package org.yzh.framework;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.log.Logger;
import org.yzh.framework.mapping.Handler;
import org.yzh.framework.mapping.HandlerMapper;
import org.yzh.framework.message.AbstractHeader;
import org.yzh.framework.message.PackageData;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.web.jt808.dto.basics.Header;

@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private final SessionManager sessionManager = SessionManager.getInstance();

    private Logger logger;

    private HandlerMapper handlerMapper;

    private MessageDecoder decoder;

    private MessageEncoder encoder;

    private byte delimiter;

    public TCPServerHandler(byte delimiter, MessageDecoder decoder, MessageEncoder encoder, HandlerMapper handlerMapper) {
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapper = handlerMapper;
        this.logger = new Logger();
    }

    public TCPServerHandler(byte delimiter, MessageDecoder decoder, MessageEncoder encoder, HandlerMapper handlerMapper, Logger logger) {
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapper = handlerMapper;
        this.logger = logger;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf headerBodyBuf = (ByteBuf) msg;
            if (headerBodyBuf.readableBytes() <= 0)
                return;

            Channel channel = ctx.channel();

            int type = decoder.getType(headerBodyBuf);
            Handler handler = handlerMapper.getHandler(type);

            if (handler == null) {
                logger.logMessage("i 未知消息", null, ByteBufUtil.hexDump(headerBodyBuf));
                return;
            }

            Class<?>[] types = handler.getTargetParameterTypes();
            Class<? extends PackageData> targetClass = (Class<? extends PackageData>) types[0];

            PackageData packageData = decoder.decode(headerBodyBuf, Header.class, targetClass);

            headerBodyBuf.resetReaderIndex();
            logger.logMessage("i " + handler.toString(), packageData, ByteBufUtil.hexDump(headerBodyBuf));

            PackageData<? extends AbstractHeader> result;
            if (types.length == 1) {
                result = handler.invoke(packageData);
            } else {
                result = handler.invoke(packageData, sessionManager.getBySessionId(Session.buildId(channel)));
            }

            if (result == null)
                return;
            ByteBuf resultBuf = encoder.encode(result);
            logger.logMessage("o " + handler.toString(), result, ByteBufUtil.hexDump(resultBuf));
            ByteBuf allResultBuf = Unpooled.wrappedBuffer(Unpooled.wrappedBuffer(new byte[]{delimiter}), resultBuf, Unpooled.wrappedBuffer(new byte[]{delimiter}));
            ChannelFuture future = channel.writeAndFlush(allResultBuf).sync();
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
        Session session = sessionManager.removeBySessionId(sessionId);
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