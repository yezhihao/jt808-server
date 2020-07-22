package org.yzh.framework.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

public class JTConfig {

    private final int port;
    private final ByteToMessageDecoder decoder;
    private final MessageToByteEncoder encoder;
    private final DelimiterBasedFrameDecoder delimiterBasedFrameDecoder;

    private JTConfig(
            int port,
            int maxFrameLength,
            byte[][] delimiters,
            ByteToMessageDecoder decoder,
            MessageToByteEncoder encoder
    ) {
        this.port = port;
        this.decoder = decoder;
        this.encoder = encoder;

        ByteBuf[] byteBufs = new ByteBuf[delimiters.length];
        for (int i = 0; i < delimiters.length; i++) {
            byte[] delimiter = delimiters[i];
            byteBufs[i] = Unpooled.wrappedBuffer(delimiter);
        }
        this.delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(maxFrameLength, byteBufs);
    }

    public int getPort() {
        return port;
    }

    public ByteToMessageDecoder getDecoder() {
        return decoder;
    }

    public MessageToByteEncoder getEncoder() {
        return encoder;
    }

    public DelimiterBasedFrameDecoder getDelimiterBasedFrameDecoder() {
        return delimiterBasedFrameDecoder;
    }

    public static JTConfig.Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private int port;
        private int maxFrameLength;
        private byte[][] delimiters;
        private ByteToMessageDecoder decoder;
        private MessageToByteEncoder encoder;

        public Builder() {
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setDecoder(ByteToMessageDecoder decoder) {
            this.decoder = decoder;
            return this;
        }

        public Builder setEncoder(MessageToByteEncoder encoder) {
            this.encoder = encoder;
            return this;
        }

        public Builder setMaxFrameLength(int maxFrameLength) {
            this.maxFrameLength = maxFrameLength;
            return this;
        }

        public Builder setDelimiters(byte[]... delimiters) {
            this.delimiters = delimiters;
            return this;
        }

        public JTConfig build() {
            return new JTConfig(
                    this.port,
                    this.maxFrameLength,
                    this.delimiters,
                    this.decoder,
                    this.encoder
            );
        }
    }
}