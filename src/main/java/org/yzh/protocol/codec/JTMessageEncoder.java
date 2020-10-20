package org.yzh.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.ByteBufUtils;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JT协议编码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageEncoder {

    private static final Logger log = LoggerFactory.getLogger(JTMessageEncoder.class.getSimpleName());

    private Map<Integer, BeanMetadata<Header>> headerMetadataMap;

    public JTMessageEncoder(String basePackage) {
        MessageHelper.initial(basePackage);
        this.headerMetadataMap = MessageHelper.getBeanMetadata(Header.class);
    }

    public ByteBuf encode(JTMessage message) {
        Header header = message.getHeader();
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

        BeanMetadata headMetadata = headerMetadataMap.get(version);
        ByteBuf headerBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(headMetadata.getLength(), 2048);
        headMetadata.encode(headerBuf, header);
        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        allBuf = sign(allBuf);
        allBuf = escape(allBuf);
        return allBuf;
    }

    /** 签名 */
    protected ByteBuf sign(ByteBuf buf) {
        byte checkCode = ByteBufUtils.bcc(buf);
        buf.writeByte(checkCode);
        return buf;
    }

    /** 转义处理 */
    protected ByteBuf escape(ByteBuf source) {
        int low = source.readerIndex();
        int high = source.writerIndex();

        int mark = source.forEachByte(low, high, value -> !(value == 0x7d || value == 0x7e));

        if (mark == -1)
            return source;

        List<ByteBuf> bufList = new ArrayList<>(5);

        int len;
        do {

            len = mark + 1 - low;
            ByteBuf[] slice = slice(source, low, len);
            bufList.add(slice[0]);
            bufList.add(slice[1]);
            low += len;

            mark = source.forEachByte(low, high - low, value -> !(value == 0x7d || value == 0x7e));
        } while (mark > 0);

        bufList.add(source.slice(low, high - low));

        ByteBuf[] bufs = bufList.toArray(new ByteBuf[bufList.size()]);

        return Unpooled.wrappedBuffer(bufs);
    }

    /** 截断转义前报文，并转义 */
    protected ByteBuf[] slice(ByteBuf byteBuf, int index, int length) {
        byte first = byteBuf.getByte(index + length - 1);

        ByteBuf[] bufs = new ByteBuf[2];
        bufs[0] = byteBuf.slice(index, length);

        if (first == 0x7d)
            // 0x01 不做处理 p47
            bufs[1] = Unpooled.wrappedBuffer(new byte[]{0x01});
        else {
            byteBuf.setByte(index + length - 1, 0x7d);
            bufs[1] = Unpooled.wrappedBuffer(new byte[]{0x02});
        }

        return bufs;
    }
}