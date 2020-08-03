package org.yzh.framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
        try {
            this.bossGroup = new NioEventLoopGroup(1);
            this.workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.option(NioChannelOption.SO_BACKLOG, 1024)
                    .option(NioChannelOption.SO_REUSEADDR, true)
                    .childOption(NioChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(NioSocketChannel channel) {
                            channel.pipeline()
                                    .addLast("frameDecoder", new DelimiterBasedFrameDecoder(config.maxFrameLength,
                                            Unpooled.wrappedBuffer(config.delimiter),
                                            Unpooled.wrappedBuffer(config.delimiter, config.delimiter)))
                                    .addLast("decoder", new MessageDecoderWrapper(config.decoder))
                                    .addLast("encoder", new MessageEncoderWrapper(config.encoder, config.delimiter))
                                    .addLast("adapter", config.adapter);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(config.port).sync();
            log.warn("===TCP Server启动成功, port={}===", config.port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.warn("===TCP Server出现异常, port={}===", e);
        } finally {
            stop();
        }
    }

    public synchronized void start() {
        if (this.isRunning) {
            log.warn("===TCP Server已经启动, port={}===", config.port);
            return;
        }
        this.isRunning = true;
        new Thread(() -> startInternal()).start();
    }

    public synchronized void stop() {
        if (!this.isRunning) {
            log.warn("===TCP Server已经停止, port={}===", config.port);
        }
        this.isRunning = false;

        Future future = this.bossGroup.shutdownGracefully();
        if (!future.isSuccess())
            log.warn("bossGroup 无法正常停止", future.cause());

        future = this.workerGroup.shutdownGracefully();
        if (!future.isSuccess())
            log.warn("workerGroup 无法正常停止", future.cause());

        log.warn("===TCP Server已经停止, port={}===", config.port);
    }
}