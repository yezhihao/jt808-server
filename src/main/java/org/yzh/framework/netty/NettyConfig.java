package org.yzh.framework.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.codec.MultiPacketListener;
import org.yzh.framework.codec.MultiPacketManager;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class NettyConfig {

    protected final int port;
    protected final int maxFrameLength;
    protected final byte[] delimiter;
    protected final MessageDecoder decoder;
    protected final MessageEncoder encoder;
    protected final ChannelInboundHandlerAdapter adapter;
    protected final HandlerMapping handlerMapping;
    protected final HandlerInterceptor handlerInterceptor;
    protected final MultiPacketListener multiPacketListener;

    private NettyConfig(int port,
                        int maxFrameLength,
                        byte[] delimiter,
                        MessageDecoder decoder,
                        MessageEncoder encoder,
                        HandlerMapping handlerMapping,
                        HandlerInterceptor handlerInterceptor,
                        MultiPacketListener multiPacketListener
    ) {
        this.port = port;
        this.maxFrameLength = maxFrameLength;
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapping = handlerMapping;
        this.handlerInterceptor = handlerInterceptor;
        this.adapter = new TCPServerHandler(this.handlerMapping, this.handlerInterceptor);
        this.multiPacketListener = multiPacketListener;
        MultiPacketManager.getInstance().addListener(multiPacketListener);
    }

    public static NettyConfig.Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private int port;
        private int maxFrameLength;
        private byte[] delimiters;
        private MessageDecoder decoder;
        private MessageEncoder encoder;
        private HandlerMapping handlerMapping;
        private HandlerInterceptor handlerInterceptor;
        private MultiPacketListener multiPacketListener;

        public Builder() {
        }

        public Builder setPort(int port) {
            this.port = port;
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

        public Builder setHandlerInterceptor(HandlerInterceptor handlerInterceptor) {
            this.handlerInterceptor = handlerInterceptor;
            return this;
        }

        public Builder setMultiPacketListener(MultiPacketListener multiPacketListener) {
            this.multiPacketListener = multiPacketListener;
            return this;
        }

        public NettyConfig build() {
            return new NettyConfig(
                    this.port,
                    this.maxFrameLength,
                    this.delimiters,
                    this.decoder,
                    this.encoder,
                    this.handlerMapping,
                    this.handlerInterceptor,
                    this.multiPacketListener
            );
        }
    }
}