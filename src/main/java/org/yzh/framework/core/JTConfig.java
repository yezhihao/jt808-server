package org.yzh.framework.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.mvc.DefaultHandlerMapping;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.mvc.TCPServerHandler;
import org.yzh.framework.orm.model.AbstractMessage;

import java.util.List;

public class JTConfig {

    private final int port;
    private final DelimiterBasedFrameDecoder frameDecoder;
    private final ByteToMessageDecoder decoder;
    private final MessageToByteEncoder encoder;
    private final ChannelInboundHandlerAdapter adapter;
    private final HandlerMapping handlerMapping;

    private JTConfig(int port, int maxFrameLength, byte[] delimiter, HandlerMapping handlerMapping, MessageDecoder decoder, MessageEncoder encoder) {
        this.port = port;
        if (handlerMapping == null)
            this.handlerMapping = new DefaultHandlerMapping();
        else
            this.handlerMapping = handlerMapping;

        this.frameDecoder = new DelimiterBasedFrameDecoder(maxFrameLength,
                Unpooled.wrappedBuffer(delimiter),
                Unpooled.wrappedBuffer(delimiter, delimiter));

        this.decoder = new ByteToMessageDecoder() {
            @Override
            protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
                out.add(decoder.decode(buf));
                buf.skipBytes(buf.readableBytes());
            }
        };
        this.encoder = new MessageToByteEncoder<AbstractMessage>() {
            protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) {
                out.writeBytes(delimiter).writeBytes(encoder.encode(msg)).writeBytes(delimiter);
            }
        };

        this.adapter = new TCPServerHandler(this.handlerMapping);
    }

    public int getPort() {
        return port;
    }

    public DelimiterBasedFrameDecoder getFrameDecoder() {
        return frameDecoder;
    }

    public ByteToMessageDecoder getDecoder() {
        return decoder;
    }

    public MessageToByteEncoder getEncoder() {
        return encoder;
    }

    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }

    public ChannelInboundHandlerAdapter getAdapter() {
        return adapter;
    }

    public static JTConfig.Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private int port;
        private int maxFrameLength;
        private byte[] delimiters;
        private HandlerMapping handlerMapping;
        private MessageDecoder decoder;
        private MessageEncoder encoder;

        public Builder() {
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setDecoder(MessageDecoder decoder) {
            this.decoder = decoder;
            return this;
        }

        public Builder setEncoder(MessageEncoder encoder) {
            this.encoder = encoder;
            return this;
        }

        public Builder setHandlerMapping(HandlerMapping handlerMapping) {
            this.handlerMapping = handlerMapping;
            return this;
        }

        public Builder setMaxFrameLength(int maxFrameLength) {
            this.maxFrameLength = maxFrameLength;
            return this;
        }

        public Builder setDelimiters(byte[] delimiters) {
            this.delimiters = delimiters;
            return this;
        }

        public JTConfig build() {
            return new JTConfig(
                    this.port,
                    this.maxFrameLength,
                    this.delimiters,
                    this.handlerMapping,
                    this.decoder,
                    this.encoder
            );
        }
    }
}