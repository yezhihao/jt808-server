package org.yzh.framework.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yzh.framework.codec.*;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.HandlerMapping;
import org.yzh.framework.session.SessionManager;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class NettyConfig {

    protected final int port;
    protected final int maxFrameLength;
    protected final LengthField lengthField;
    protected final Delimiter[] delimiter;
    protected final MessageDecoder decoder;
    protected final MessageEncoder encoder;
    protected final ChannelInboundHandlerAdapter adapter;
    protected final HandlerMapping handlerMapping;
    protected final HandlerInterceptor handlerInterceptor;
    protected final SessionManager sessionManager;
    protected final MultiPacketListener multiPacketListener;

    private NettyConfig(int port,
                        int maxFrameLength,
                        LengthField lengthField,
                        Delimiter[] delimiter,
                        MessageDecoder decoder,
                        MessageEncoder encoder,
                        HandlerMapping handlerMapping,
                        HandlerInterceptor handlerInterceptor,
                        SessionManager sessionManager,
                        MultiPacketListener multiPacketListener
    ) {
        this.port = port;
        this.maxFrameLength = maxFrameLength;
        this.lengthField = lengthField;
        this.delimiter = delimiter;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handlerMapping = handlerMapping;
        this.handlerInterceptor = handlerInterceptor;
        this.sessionManager = sessionManager;
        this.adapter = new TCPServerHandler(this.handlerMapping, this.handlerInterceptor, this.sessionManager);
        this.multiPacketListener = multiPacketListener;
        MultiPacketManager.getInstance().addListener(multiPacketListener);
    }

    public static NettyConfig.Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private int port;
        private int maxFrameLength;
        private LengthField lengthField;
        private Delimiter[] delimiters;
        private MessageDecoder decoder;
        private MessageEncoder encoder;
        private HandlerMapping handlerMapping;
        private HandlerInterceptor handlerInterceptor;
        private SessionManager sessionManager;
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

        public Builder setLengthField(LengthField lengthField) {
            this.lengthField = lengthField;
            return this;
        }

        public Builder setDelimiters(byte[][] delimiters) {
            Delimiter[] t = new Delimiter[delimiters.length];
            for (int i = 0; i < delimiters.length; i++) {
                t[i] = new Delimiter(delimiters[i]);
            }
            this.delimiters = t;
            return this;
        }

        public Builder setDelimiters(Delimiter... delimiters) {
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

        public Builder setSessionManager(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
            return this;
        }

        public NettyConfig build() {
            return new NettyConfig(
                    this.port,
                    this.maxFrameLength,
                    this.lengthField,
                    this.delimiters,
                    this.decoder,
                    this.encoder,
                    this.handlerMapping,
                    this.handlerInterceptor,
                    this.sessionManager,
                    this.multiPacketListener
            );
        }
    }
}