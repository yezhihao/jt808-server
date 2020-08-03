package org.yzh.framework.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.codec.MessageDecoderWrapper;
import org.yzh.framework.codec.MessageEncoderWrapper;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class TCPClient {

    private static final Logger log = LoggerFactory.getLogger(TCPClient.class);

    private ClientConfig config;

    private EventLoopGroup workerGroup;

    private ChannelFuture channelFuture;

    public TCPClient(ClientConfig config) {
        this.config = config;
    }

    private void startInternal() {
        try {
            this.workerGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(workerGroup);
            bootstrap.option(NioChannelOption.SO_REUSEADDR, true)
                    .option(NioChannelOption.TCP_NODELAY, true)
                    .option(NioChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(NioSocketChannel ch) {
                            ch.pipeline()
                                    .addLast("frameDecoder", new DelimiterBasedFrameDecoder(config.maxFrameLength,
                                            Unpooled.wrappedBuffer(config.delimiter),
                                            Unpooled.wrappedBuffer(config.delimiter, config.delimiter)))
                                    .addLast("decoder", new MessageDecoderWrapper(config.decoder))
                                    .addLast("encoder", new MessageEncoderWrapper(config.encoder, config.delimiter))
                                    .addLast("adapter", config.adapter);
                        }
                    });

            this.channelFuture = bootstrap.connect(config.ip, config.port).sync();
            this.channelFuture.channel().closeFuture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeObject(Object message) {
        channelFuture.channel().writeAndFlush(message);
        log.info("<<<<<<发送消息:{}", message);
    }

    public synchronized TCPClient start() {
        startInternal();
        log.warn("===TCP Client启动成功, port={}===", config.port);
        return this;
    }

    public synchronized void stop() {
        workerGroup.shutdownGracefully();
        log.warn("===TCP Client已经停止, port={}===", config.port);
    }
}