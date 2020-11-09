package org.yzh.protocol.codec;

import io.github.yezhihao.protostar.ProtostarUtil;
import io.github.yezhihao.protostar.Schema;
import io.netty.buffer.*;
import io.netty.util.ByteProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JTUtils;

import java.util.LinkedList;
import java.util.Map;

/**
 * JT协议编码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageEncoder {

    private static final Logger log = LoggerFactory.getLogger(JTMessageEncoder.class.getSimpleName());

    private static final ByteBufAllocator ALLOC = PooledByteBufAllocator.DEFAULT;

    private Map<Integer, Schema<Header>> headerSchemaMap;

    public JTMessageEncoder(String basePackage) {
        ProtostarUtil.initial(basePackage);
        this.headerSchemaMap = ProtostarUtil.getSchema(Header.class);
    }

    public ByteBuf encode(JTMessage message) {
        Header header = message.getHeader();
        int version = header.getVersionNo();
        int headLength = JTUtils.headerLength(version, false);
        int bodyLength = 0;

        Schema bodySchema = ProtostarUtil.getSchema(message.getClass(), version);
        ByteBuf allBuf;
        if (bodySchema != null) {
            allBuf = ALLOC.buffer(headLength + bodySchema.length());
            allBuf.writerIndex(headLength);
            bodySchema.writeTo(allBuf, message);
            bodyLength = allBuf.writerIndex() - headLength;
        } else {
            allBuf = ALLOC.buffer(headLength, 21);
            log.debug("未找到对应的Schema[{}]", message.getClass());
        }

        Schema headerSchema = headerSchemaMap.get(version);

        if (bodyLength <= 1023) {
            header.setBodyLength(bodyLength);

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

        } else {

            headLength = JTUtils.headerLength(version, true);

            ByteBuf[] slices = slices(allBuf, headLength, 1023);
            int total = slices.length;

            CompositeByteBuf _allBuf = new CompositeByteBuf(UnpooledByteBufAllocator.DEFAULT, false, total);
            allBuf = _allBuf;

            header.setSubpackage(true);
            header.setPackageTotal(total);

            for (int i = 0; i < total; i++) {
                ByteBuf slice = slices[i];

                header.setPackageNo(i + 1);
                header.setBodyLength(slice.readableBytes());

                ByteBuf headBuf = ALLOC.buffer(headLength, headLength);
                headerSchema.writeTo(headBuf, header);
                ByteBuf msgBuf = new CompositeByteBuf(UnpooledByteBufAllocator.DEFAULT, false, 2)
                        .addComponent(true, 0, headBuf)
                        .addComponent(true, 1, slice);
                msgBuf = sign(msgBuf);
                msgBuf = escape(msgBuf);
                _allBuf.addComponent(true, i, msgBuf);
            }
        }
        return allBuf;
    }

    public static ByteBuf[] slices(ByteBuf output, int start, int unitSize) {
        int totalSize = output.writerIndex() - start;
        int sliceTotal = (totalSize - 1) / unitSize + 1;

        ByteBuf[] slices = new ByteBuf[sliceTotal];
        output.skipBytes(start);
        for (int i = 0; i < slices.length; i++) {
            if (!output.isReadable(unitSize))
                unitSize = output.readableBytes();
            slices[i] = output.readRetainedSlice(unitSize);
        }
        return slices;
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

        LinkedList<ByteBuf> bufList = new LinkedList();
        int mark, len;
        while ((mark = source.forEachByte(low, high - low, searcher)) > 0) {

            len = mark + 1 - low;
            ByteBuf[] slice = slice(source, low, len);
            bufList.add(slice[0]);
            bufList.add(slice[1]);
            low += len;
        }

        if (bufList.size() > 0) {
            bufList.add(source.slice(low, high - low));
        } else {
            bufList.add(source);
        }

        ByteBuf delimiter = Unpooled.buffer(1, 1).writeByte(0x7e).retain();
        bufList.addFirst(delimiter);
        bufList.addLast(delimiter);

        CompositeByteBuf byteBufs = Unpooled.compositeBuffer(bufList.size());
        byteBufs.addComponents(true, bufList);
        return byteBufs;
    }

    /** 截断转义前报文，并转义 */
    protected static ByteBuf[] slice(ByteBuf byteBuf, int index, int length) {
        byte first = byteBuf.getByte(index + length - 1);

        ByteBuf[] bufs = new ByteBuf[2];
        bufs[0] = byteBuf.retainedSlice(index, length);

        if (first == 0x7d)
            bufs[1] = Unpooled.buffer(1, 1).writeByte(0x01);
        else {
            byteBuf.setByte(index + length - 1, 0x7d);
            bufs[1] = Unpooled.buffer(1, 1).writeByte(0x02);
        }

        return bufs;
    }
}