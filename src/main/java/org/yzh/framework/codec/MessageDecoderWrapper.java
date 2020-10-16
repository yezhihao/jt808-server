package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.session.Session;

import java.util.List;

/**
 * 基础消息解码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageDecoderWrapper extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(MessageDecoderWrapper.class.getSimpleName());

    private MessageDecoder decoder;

    public MessageDecoderWrapper(MessageDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
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
            out.add(message);
        buf.skipBytes(buf.readableBytes());
    }
}