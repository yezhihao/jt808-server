package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础消息编码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@ChannelHandler.Sharable
public class MessageEncoderWrapper extends ChannelOutboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MessageEncoderWrapper.class.getSimpleName());

    private MessageEncoder encoder;

    public MessageEncoderWrapper(MessageEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ByteBuf buf = null;
        try {
            buf = encoder.encode(msg);
            if (log.isInfoEnabled())
                log.info("<<<<<原始报文[ip={}],hex={}", ctx.channel().remoteAddress(), ByteBufUtil.hexDump(buf));

            if (buf.isReadable()) {
                ctx.write(buf, promise);
            } else {
                buf.release();
                ctx.write(Unpooled.EMPTY_BUFFER, promise);
            }
            buf = null;
        } catch (EncoderException e) {
            throw e;
        } catch (Throwable e) {
            throw new EncoderException(e);
        } finally {
            if (buf != null) {
                buf.release();
            }
        }
    }
}