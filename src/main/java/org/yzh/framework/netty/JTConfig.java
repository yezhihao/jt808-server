package org.yzh.framework.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;

public class JTConfig {

    private final int port;
    private final int maxFrameLength;
    private final byte[] delimiter;
    private final MessageDecoder decoder;
    private final MessageEncoder encoder;
    private final ChannelInboundHandlerAdapter adapter;
    private final HandlerMapping handlerMapping;
    private final HandlerInterceptor handlerInterceptor;

    private JTConfig(int port,
                     int maxFrameLength,
                     byte[] delimiter,
                     MessageDecoder decoder,
                     MessageEncoder encoder,
                     HandlerMapping handlerMapping,
                     HandlerInterceptor handlerInterceptor
    ) {
        this.port = port;
        this.maxFrameLength = maxFrameLength;
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapping = handlerMapping;
        this.handlerInterceptor = handlerInterceptor;
        this.adapter = new TCPServerHandler(this.handlerMapping, this.handlerInterceptor);
    }

    public int getPort() {
        return port;
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public byte[] getDelimiter() {
        return delimiter;
    }

    public MessageDecoder getDecoder() {
        return decoder;
    }

    public MessageEncoder getEncoder() {
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
        private MessageDecoder decoder;
        private MessageEncoder encoder;
        private HandlerMapping handlerMapping;
        private HandlerInterceptor handlerInterceptor;

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

        public JTConfig build() {
            return new JTConfig(
                    this.port,
                    this.maxFrameLength,
                    this.delimiters,
                    this.decoder,
                    this.encoder,
                    this.handlerMapping,
                    this.handlerInterceptor
            );
        }
    }
}