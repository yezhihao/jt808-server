package org.yzh.framework;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.codec.MessageDecoder;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.mapping.HandlerMapper;

import java.util.concurrent.TimeUnit;

public class TCPServer {

    private static final Logger log = LoggerFactory.getLogger(TCPServer.class);
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private int port;
    private byte delimiter;
    private ChannelInboundHandlerAdapter inboundHandler;

    public TCPServer() {
    }

    public TCPServer(int port, byte delimiter, HandlerMapper handlerMapper, MessageDecoder messageDecoder, MessageEncoder messageEncoder) {
        this();
        this.port = port;
        this.delimiter = delimiter;
        this.inboundHandler = new TCPServerHandler(delimiter, messageDecoder, messageEncoder, handlerMapper);
    }

    public TCPServer(int port, byte delimiter, HandlerMapper handlerMapper, MessageDecoder messageDecoder, MessageEncoder messageEncoder, org.yzh.framework.log.Logger logger) {
        this();
        this.port = port;
        this.delimiter = delimiter;
        this.inboundHandler = new TCPServerHandler(delimiter, messageDecoder, messageEncoder, handlerMapper, logger);
    }

    private void bind() throws Exception {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(30, 0, 0, TimeUnit.MINUTES));
                // 1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(new byte[]{delimiter}), Unpooled.wrappedBuffer(new byte[]{delimiter, delimiter})));
                ch.pipeline().addLast(inboundHandler);
            }
        });
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        this.log.info("TCP服务启动完毕,port={}", this.port);
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        channelFuture.channel().closeFuture().sync();
    }

    public synchronized void startServer() {
        if (this.isRunning) {
            throw new IllegalStateException(this.getName() + " is already started .");
        }
        this.isRunning = true;

        new Thread(() -> {
            try {
                this.bind();
            } catch (Exception e) {
                this.log.info("TCP服务启动出错:{}", e.getMessage());
                e.printStackTrace();
            }
        }, this.getName()).start();
    }

    public synchronized void stopServer() {
        if (!this.isRunning) {
            throw new IllegalStateException(this.getName() + " is not yet started .");
        }
        this.isRunning = false;

        try {
            Future<?> future = this.workerGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("workerGroup 无法正常停止:{}", future.cause());
            }

            future = this.bossGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("bossGroup 无法正常停止:{}", future.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.log.info("TCP服务已经停止...");
    }

    private String getName() {
        return "TCP-Server";
    }
}