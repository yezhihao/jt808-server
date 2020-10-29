package org.yzh.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.Schema;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JTUtils;

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

    private Map<Integer, Schema<Header>> headerSchemaMap;

    public JTMessageEncoder(String basePackage) {
        MessageHelper.initial(basePackage);
        this.headerSchemaMap = MessageHelper.getSchema(Header.class);
    }

    public ByteBuf encode(JTMessage message) {
        Header header = message.getHeader();
        int version = header.getVersionNo();
        int headLength = JTUtils.headerLength(version, false);
        int bodyLength = 0;

        Schema bodySchema = MessageHelper.getSchema(message.getClass(), version);
        ByteBuf allBuf;
        if (bodySchema != null) {
            allBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(headLength + bodySchema.length(), 2048);
            bodySchema.writeTo(allBuf, message);
            bodyLength = allBuf.writerIndex() - headLength;
        } else {
            allBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(headLength, 128);
            log.debug("未找到对应的Schema[{}]", message.getClass());
        }

        if (bodyLength > 1023)
            throw new EncoderException("消息体不能大于1023kb," + bodyLength + "kb");
        header.setBodyLength(bodyLength);

        Schema headerSchema = headerSchemaMap.get(version);
        int writerIndex = allBuf.writerIndex();
        if (writerIndex > 0) {
            allBuf.writerIndex(0);
            headerSchema.writeTo(allBuf, header);
            allBuf.writerIndex(writerIndex);
        } else {
            headerSchema.writeTo(allBuf, header);
        }

        allBuf = sign(allBuf);
        allBuf = escape(allBuf);
        return allBuf;
    }

    /** 签名 */
    public static ByteBuf sign(ByteBuf buf) {
        byte checkCode = JTUtils.bcc(buf);
        buf.writeByte(checkCode);
        return buf;
    }

    private static final ByteProcessor searcher = value -> !(value == 0x7d || value == 0x7e);

    /** 转义处理 */
    public static ByteBuf escape(ByteBuf source) {
        int low = source.readerIndex();
        int high = source.writerIndex();

        int mark = source.forEachByte(low, high, searcher);

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

            mark = source.forEachByte(low, high - low, searcher);
        } while (mark > 0);

        bufList.add(source.slice(low, high - low));

        ByteBuf[] bufs = bufList.toArray(new ByteBuf[bufList.size()]);

        return Unpooled.wrappedBuffer(bufs);
    }

    /** 截断转义前报文，并转义 */
    protected static ByteBuf[] slice(ByteBuf byteBuf, int index, int length) {
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