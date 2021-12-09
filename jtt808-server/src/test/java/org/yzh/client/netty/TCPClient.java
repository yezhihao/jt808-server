package org.yzh.client.netty;

import io.github.yezhihao.netmc.core.model.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class TCPClient {

    private static final Logger log = LoggerFactory.getLogger(TCPClient.class.getSimpleName());

    private EventLoopGroup workerGroup;

    private Channel channel;

    private ClientConfig config;
    private String id;

    public TCPClient(String id, ClientConfig config) {
        this.id = id;
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
                                    .addLast("decoder", new ByteToMessageDecoder() {
                                        @Override
                                        protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
                                            log.info("<<<<<{}", ByteBufUtil.hexDump(buf));
                                            Object msg = config.decoder.decode(buf, null);
                                            log.info("<<<<<<<<<<{}", msg);
                                            if (msg != null)
                                                out.add(msg);
                                            buf.skipBytes(buf.readableBytes());
                                        }
                                    })
                                    .addLast("encoder", new MessageToByteEncoder<Message>() {
                                        @Override
                                        protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
                                            log.info(">>>>>>>>>>{}", msg);
                                            ByteBuf buf = config.encoder.encode(msg, null);
                                            log.info(">>>>>{}", ByteBufUtil.hexDump(buf));
                                            out.writeBytes(config.delimiter).writeBytes(buf).writeBytes(config.delimiter);
                                            buf.release();
                                        }
                                    })
                                    .addLast("adapter", config.adapter);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(config.ip, config.port).sync();
            this.channel = channelFuture.channel();
            this.channel.closeFuture();
        } catch (Exception e) {
            log.error("===TCP Client异常关闭", e);
        }
    }

    public void writeObject(Object message) {
        channel.writeAndFlush(message);
    }

    public synchronized TCPClient start() {
        startInternal();
        log.warn("===TCP Client启动成功, id={}===", id);
        return this;
    }

    public synchronized void stop() {
        workerGroup.shutdownGracefully();
        log.warn("===TCP Client已经停止, id={}===", id);
    }
}