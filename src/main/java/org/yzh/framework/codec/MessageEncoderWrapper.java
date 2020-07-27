package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * 基础消息编码
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class MessageEncoderWrapper extends MessageToByteEncoder<AbstractMessage> {

    private MessageEncoder encoder;

    private byte[] delimiter;

    public MessageEncoderWrapper(MessageEncoder encoder, byte[] delimiter) {
        this.encoder = encoder;
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) {
        out.writeBytes(delimiter).writeBytes(encoder.encode(msg)).writeBytes(delimiter);
    }
}