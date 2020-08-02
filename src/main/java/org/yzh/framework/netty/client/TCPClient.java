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
import io.netty.util.concurrent.Future;
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
    private volatile boolean isRunning = false;

    private EventLoopGroup workerGroup = null;

    private ClientConfig config;

    private NioSocketChannel channel;

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
                            channel = ch;
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(config.ip, config.port).sync();
            channelFuture.channel().closeFuture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeObject(Object message) {
        channel.writeAndFlush(message);
    }


    public synchronized TCPClient start() {
        if (this.isRunning) {
            log.warn("===TCP Client已经启动, port={}===", config.port);
            return this;
        }
        this.isRunning = true;
        startInternal();
        log.info("===TCP Client启动成功, port={}===", config.port);
        return this;
    }

    public synchronized void stop() {
        if (!this.isRunning) {
            log.warn("===TCP Client已经停止, port={}===", config.port);
        }
        this.isRunning = false;
        try {
            Future future = this.workerGroup.shutdownGracefully().await();
            if (!future.isSuccess())
                log.error("workerGroup 无法正常停止", future.cause());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("===TCP Client已经停止, port={}===", config.port);
    }
}