package org.yzh.client.netty;

import io.github.yezhihao.netmc.core.model.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ChannelHandler.Sharable
public class TCPClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(TCPClientHandler.class.getSimpleName());

    private HandlerMapping handlerMapping;

    public TCPClientHandler(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof Message))
            return;

        Message request = (Message) msg;
        Channel channel = ctx.channel();

        try {
            Handler handler = handlerMapping.getHandler(request.getMessageId());
            Message response = handler.invoke(request);

            if (response != null) {
                channel.writeAndFlush(response);
            }
        } catch (Exception e) {
            log.warn(String.valueOf(request), e);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(">>>>>连接到服务端{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.warn("<<<<<断开连接{}", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        log.error("<<<<<发生异常", e);
    }
}