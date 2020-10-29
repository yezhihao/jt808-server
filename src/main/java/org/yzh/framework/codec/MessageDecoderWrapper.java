package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.session.Session;

/**
 * 基础消息解码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@ChannelHandler.Sharable
public class MessageDecoderWrapper extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MessageDecoderWrapper.class.getSimpleName());

    private MessageDecoder decoder;

    public MessageDecoderWrapper(MessageDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            try {
                if (log.isInfoEnabled()) {
                    String hex;
                    if (buf.readableBytes() < 1048)
                        hex = ByteBufUtil.hexDump(buf);
                    else
                        hex = ByteBufUtil.hexDump(buf.slice(0, 32)) + "..." + ByteBufUtil.hexDump(buf.slice(buf.readableBytes() - 32, 32));
                    log.info(">>>>>原始报文[ip={}],hex={}", ctx.channel().remoteAddress(), hex);
                }
                Object message = decoder.decode(buf, ctx.channel().attr(Session.KEY).get());
                if (message != null)
                    ctx.fireChannelRead(message);
                buf.skipBytes(buf.readableBytes());
            } catch (Exception e) {
                throw new DecoderException(e);
            } finally {
                buf.release();
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}