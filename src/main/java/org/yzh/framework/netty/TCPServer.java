package org.yzh.framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
public class TCPServer {

    private static final Logger log = LoggerFactory.getLogger(TCPServer.class);
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    private NettyConfig config;

    public TCPServer(NettyConfig config) {
        this.config = config;
    }

    private void startInternal() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 512)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast("frameDecoder", new DelimiterBasedFrameDecoder(config.maxFrameLength,
                                        Unpooled.wrappedBuffer(config.delimiter),
                                        Unpooled.wrappedBuffer(config.delimiter, config.delimiter)))
                                .addLast("decoder", new MessageDecoderWrapper(config.decoder))
                                .addLast("encoder", new MessageEncoderWrapper(config.encoder, config.delimiter))
                                .addLast("adapter", config.adapter);
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(config.port);
        channelFuture.channel().closeFuture();
    }

    public synchronized void start() {
        if (this.isRunning) {
            log.warn("===TCP服务已经启动, port={}===", config.port);
            return;
        }
        this.isRunning = true;
        this.startInternal();
        log.info("===TCP服务启动成功, port={}===", config.port);
    }

    public synchronized void stop() {
        if (!this.isRunning) {
            log.warn("===TCP服务已经停止, port={}===", config.port);
        }
        this.isRunning = false;
        try {
            Future future = this.workerGroup.shutdownGracefully().await();
            if (!future.isSuccess())
                log.error("workerGroup 无法正常停止", future.cause());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Future future = this.bossGroup.shutdownGracefully().await();
            if (!future.isSuccess())
                log.error("bossGroup 无法正常停止", future.cause());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("===TCP服务已经停止, port={}===", config.port);
    }
}