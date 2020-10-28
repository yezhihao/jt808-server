package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 基础消息编码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageEncoderWrapper extends MessageToMessageEncoder {

    private static final Logger log = LoggerFactory.getLogger(MessageEncoderWrapper.class.getSimpleName());

    private MessageEncoder encoder;

    private byte[] delimiter;

    public MessageEncoderWrapper(MessageEncoder encoder, byte[] delimiter) {
        this.encoder = encoder;
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) {
        ByteBuf buf = encoder.encode(msg);
        if (log.isInfoEnabled())
            log.info("<<<<<原始报文[ip={}],hex={}", ctx.channel().remoteAddress(), ByteBufUtil.hexDump(buf));
        buf.writeBytes(delimiter);
        out.add(buf);
    }
}