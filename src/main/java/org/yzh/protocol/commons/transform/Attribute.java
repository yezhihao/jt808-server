package org.yzh.protocol.commons.transform;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;

/**
 * 位置附加信息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class Attribute {

    public abstract int getAttributeId();

    public <T extends Attribute> T formBytes(byte... bytes) {
        BeanMetadata<T> beanMetadata = MessageHelper.getBeanMetadata(this.getClass(), 0);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        return beanMetadata.decode(byteBuf);
    }

    public byte[] toBytes() {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(100);
        BeanMetadata beanMetadata = MessageHelper.getBeanMetadata(this.getClass(), 0);
        beanMetadata.encode(byteBuf, this);
        byte[] bytes = new byte[byteBuf.writerIndex()];
        byteBuf.getBytes(0, bytes);
        return bytes;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}