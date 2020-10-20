package org.yzh.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.model.Message;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
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
        log.info(">>>>>>>>>>收到消息:{}", request);
        Channel channel = ctx.channel();

        try {
            Handler handler = handlerMapping.getHandler(request.getMessageType());

            Message messageResponse = handler.invoke(request);


            if (messageResponse != null) {
                channel.writeAndFlush(messageResponse);
                log.info("<<<<<<<<<<返回消息:{}", request);
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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("<<<<<断开连接");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error("<<<<<发生异常", e);
        super.exceptionCaught(ctx, e);
    }
}