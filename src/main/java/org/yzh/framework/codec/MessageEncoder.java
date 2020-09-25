package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;

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

        ByteBuf bodyBuf = encode(message, version);

        int bodyLen = bodyBuf.readableBytes();
        if (bodyLen > 1023)
            throw new RuntimeException("消息体不能大于1023kb," + bodyLen + "Kb");
        header.setBodyLength(bodyLen);

        ByteBuf headerBuf = encode(header, version);
        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        allBuf = sign(allBuf);
        allBuf = escape(allBuf);
        return allBuf;
    }

    private ByteBuf encode(Object obj, int version) {
        BeanMetadata beanMetadata = MessageHelper.getBeanMetadata(obj.getClass(), version);
        if (beanMetadata == null) {
            log.info("未找到BeanMetadata[{}]", obj.getClass());
            return Unpooled.EMPTY_BUFFER;
        }

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(beanMetadata.length, 2048);
        try {
            for (FieldMetadata fieldMetadata : beanMetadata.fieldMetadataList) {
                Object value = fieldMetadata.readMethod.invoke(obj);
                if (value != null)
                    write(buf, fieldMetadata, value, version);
            }
        } catch (Exception e) {
            log.error("获取对象值失败", e);
        }
        return buf;
    }

    private ByteBuf encode(List<Object> list, int version) {
        int size = list.size();
        if (size == 0)
            return Unpooled.EMPTY_BUFFER;
        Object first = list.get(0);
        Class<?> clazz = first.getClass();

        BeanMetadata beanMetadata = MessageHelper.getBeanMetadata(clazz, version);
        if (beanMetadata == null) {
            log.warn(clazz.getName() + "未找到 beanMetadata");
            return Unpooled.EMPTY_BUFFER;
        }

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(beanMetadata.length * size);
        try {
            for (Object obj : list) {
                for (FieldMetadata fieldMetadata : beanMetadata.fieldMetadataList) {
                    Object value = fieldMetadata.readMethod.invoke(obj);
                    if (value != null)
                        write(buf, fieldMetadata, value, version);
                }
            }
        } catch (Exception e) {
            log.error("获取对象值失败", e);
        }
        return buf;
    }

    public void write(ByteBuf buf, FieldMetadata fieldMetadata, Object value, int version) {
        int length = fieldMetadata.length;

        switch (fieldMetadata.dataType) {
            case BYTE:
                buf.writeByte((int) value);
                break;
            case WORD:
                buf.writeShort((int) value);
                break;
            case DWORD:
                if (fieldMetadata.isLong)
                    buf.writeInt(((Long) value).intValue());
                else
                    buf.writeInt((int) value);
                break;
            case BYTES:
                if (fieldMetadata.isString) {
                    byte[] bytes = ((String) value).getBytes(fieldMetadata.charset);
                    int srcLen = bytes.length;
                    if (length > 0) {
                        bytes = Bcd.checkRepair(bytes, length);
                        if (srcLen > bytes.length)
                            log.warn("数据长度超出限制[{}]原始长度{},目标长度{},[{}]", value, srcLen, bytes.length);
                    }
                    buf.writeBytes(bytes);
                } else if (fieldMetadata.isByteBuffer) {
                    ByteBuffer byteBuffer = (ByteBuffer) value;
                    if (length > 0)
                        byteBuffer.position(byteBuffer.limit() - length);
                    buf.writeBytes(byteBuffer);

                } else {
                    if (length < 0) buf.writeBytes((byte[]) value);
                    else buf.writeBytes((byte[]) value, 0, length);
                }
                break;
            case BCD8421:
                if (fieldMetadata.isDateTime)
                    buf.writeBytes(Bcd.fromDateTime((LocalDateTime) value));
                else
                    buf.writeBytes(Bcd.fromStr(Bcd.leftPad((String) value, length * 2, '0')));
                break;
            case STRING:
                byte[] bytes = ((String) value).getBytes(fieldMetadata.charset);
                int srcLen = bytes.length;
                if (length > 0) {
                    bytes = Bcd.checkRepair(bytes, length);
                    if (srcLen > bytes.length)
                        log.warn("数据长度超出限制[{}],数据长度{},目标长度{}", value, srcLen, bytes.length);
                }
                buf.writeBytes(bytes);
                break;
            case OBJ:
                buf.writeBytes(encode(value, version));
                break;
            case LIST:
                if (value != null)
                    buf.writeBytes(encode((List) value, version));
                break;
        }
    }
}