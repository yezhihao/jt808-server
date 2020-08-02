package org.yzh.framework.netty.client;

import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;

public class ClientConfig {

    protected final String ip;
    protected final int port;
    protected final int maxFrameLength;
    protected final byte[] delimiter;
    protected final MessageDecoder decoder;
    protected final MessageEncoder encoder;
    protected final ChannelInboundHandlerAdapter adapter;
    protected final HandlerMapping handlerMapping;

    private ClientConfig(String ip,
                         int port,
                         int maxFrameLength,
                         byte[] delimiter,
                         MessageDecoder decoder,
                         MessageEncoder encoder,
                         HandlerMapping handlerMapping
    ) {
        this.ip = ip;
        this.port = port;
        this.maxFrameLength = maxFrameLength;
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapping = handlerMapping;
        this.adapter = new TCPClientHandler(this.handlerMapping);
    }

    public static ClientConfig.Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private String ip;
        private int port;
        private int maxFrameLength;
        private byte[] delimiters;
        private MessageDecoder decoder;
        private MessageEncoder encoder;
        private HandlerMapping handlerMapping;

        public Builder() {
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
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

        public ClientConfig build() {
            return new ClientConfig(
                    this.ip,
                    this.port,
                    this.maxFrameLength,
                    this.delimiters,
                    this.decoder,
                    this.encoder,
                    this.handlerMapping
            );
        }
    }
}