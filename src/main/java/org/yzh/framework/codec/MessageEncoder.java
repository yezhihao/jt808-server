package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * 基础消息编码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageEncoder {

    private static final Logger log = LoggerFactory.getLogger(MessageEncoder.class.getSimpleName());

    public MessageEncoder(String basePackage) {
        MessageHelper.initial(basePackage);
    }

    /** 转码 */
    public abstract ByteBuf escape(ByteBuf buf);

    /** 签名 */
    public abstract ByteBuf sign(ByteBuf buf);

    public ByteBuf encode(AbstractMessage message) {
        AbstractHeader header = message.getHeader();
        int version = header.getVersionNo();

        BeanMetadata bodyMetadata = MessageHelper.getBeanMetadata(message.getClass(), version);
        ByteBuf bodyBuf;
        if (bodyMetadata != null) {
            bodyBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(bodyMetadata.getLength(), 2048);
            bodyMetadata.encode(bodyBuf, message);
        } else {
            bodyBuf = Unpooled.EMPTY_BUFFER;
            log.info("未找到对应的BeanMetadata[{}]", message.getClass());
        }

        int bodyLen = bodyBuf.readableBytes();
        if (bodyLen > 1023)
            throw new RuntimeException("消息体不能大于1023kb," + bodyLen + "Kb");
        header.setBodyLength(bodyLen);

        BeanMetadata headMetadata = MessageHelper.getBeanMetadata(header.getClass(), version);
        ByteBuf headerBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(headMetadata.getLength(), 2048);
        headMetadata.encode(headerBuf, header);
        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        allBuf = sign(allBuf);
        allBuf = escape(allBuf);
        return allBuf;
    }

}