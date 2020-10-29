package org.yzh.framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.codec.DelimiterBasedFrameDecoder;
import org.yzh.framework.codec.LengthFieldAndDelimiterFrameDecoder;
import org.yzh.framework.codec.MessageDecoderWrapper;
import org.yzh.framework.codec.MessageEncoderWrapper;

import java.util.concurrent.TimeUnit;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class TCPServer {

    private static final Logger log = LoggerFactory.getLogger(TCPServer.class);
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    private String name;
    private NettyConfig config;

    public TCPServer(String name, NettyConfig config) {
        this.name = name;
        this.config = config;
    }

    private void startInternal() {
        try {
            this.bossGroup = new NioEventLoopGroup(1);
            this.workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.option(NioChannelOption.SO_BACKLOG, 1024)
                    .option(NioChannelOption.SO_REUSEADDR, true)
                    .childOption(NioChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {

                        private MessageEncoderWrapper messageEncoderWrapper = new MessageEncoderWrapper(config.encoder, config.delimiter[config.delimiter.length - 1].getValue());
                        private MessageDecoderWrapper messageDecoderWrapper = new MessageDecoderWrapper(config.decoder);

                        @Override
                        public void initChannel(NioSocketChannel channel) {
                            channel.pipeline()
                                    .addLast(new IdleStateHandler(4, 0, 0, TimeUnit.MINUTES))
                                    .addLast("frameDecoder", frameDecoder())
                                    .addLast("decoder", messageDecoderWrapper)
                                    .addLast("encoder", messageEncoderWrapper)
                                    .addLast("adapter", config.adapter);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(config.port).sync();
            log.warn("==={}启动成功, port={}===", name, config.port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.warn("==={}出现异常, port={}===", e);
        } finally {
            stop();
        }
    }

    public ByteToMessageDecoder frameDecoder() {
        if (config.lengthField == null)
            return new DelimiterBasedFrameDecoder(config.maxFrameLength, config.delimiter);
        return new LengthFieldAndDelimiterFrameDecoder(config.maxFrameLength, config.lengthField, config.delimiter);
    }

    public synchronized void start() {
        if (this.isRunning) {
            log.warn("==={}已经启动, port={}===", name, config.port);
            return;
        }
        this.isRunning = true;
        new Thread(() -> startInternal()).start();
    }

    public synchronized void stop() {
        if (!this.isRunning) {
            log.warn("==={}已经停止, port={}===", name, config.port);
        }
        this.isRunning = false;

        Future future = this.bossGroup.shutdownGracefully();
        if (!future.isSuccess())
            log.warn("bossGroup 无法正常停止", future.cause());

        future = this.workerGroup.shutdownGracefully();
        if (!future.isSuccess())
            log.warn("workerGroup 无法正常停止", future.cause());

        log.warn("==={}已经停止, port={}===", name, config.port);
    }
}