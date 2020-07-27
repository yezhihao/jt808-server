package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.yzh.framework.orm.model.AbstractMessage;

import java.util.List;

/**
 * 基础消息解码
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class MessageDecoderWrapper extends ByteToMessageDecoder {

    private MessageDecoder decoder;

    public MessageDecoderWrapper(MessageDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        AbstractMessage message = decoder.decode(buf);
        if (message != null)
            out.add(message);
        buf.skipBytes(buf.readableBytes());
    }
}