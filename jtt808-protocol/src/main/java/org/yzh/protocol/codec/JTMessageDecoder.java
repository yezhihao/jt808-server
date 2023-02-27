package org.yzh.protocol.codec;

import io.github.yezhihao.protostar.SchemaManager;
import io.github.yezhihao.protostar.schema.RuntimeSchema;
import io.github.yezhihao.protostar.util.ArrayMap;
import io.github.yezhihao.protostar.util.Explain;
import io.netty.buffer.*;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JTUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * JT协议解码器
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageDecoder {

    private static final ByteBufAllocator ALLOC = PooledByteBufAllocator.DEFAULT;

    private final SchemaManager schemaManager;

    private final ArrayMap<RuntimeSchema> headerSchemaMap;

    public JTMessageDecoder(String... basePackages) {
        this.schemaManager = new SchemaManager(basePackages);
        this.headerSchemaMap = schemaManager.getRuntimeSchema(JTMessage.class);
    }

    public JTMessageDecoder(SchemaManager schemaManager) {
        this.schemaManager = schemaManager;
        this.headerSchemaMap = schemaManager.getRuntimeSchema(JTMessage.class);
    }

    public JTMessage decode(ByteBuf input) {
        return decode(input, null);
    }

    public JTMessage decode(ByteBuf input, Explain explain) {
        ByteBuf buf = unescape(input);

        boolean verified = verify(buf);
        int messageId = buf.getUnsignedShort(0);
        int properties = buf.getUnsignedShort(2);

        int version = 0;//缺省值为2013版本
        if (Bit.isTrue(properties, 14))//识别2019及后续版本
            version = buf.getUnsignedByte(4);

        boolean isSubpackage = Bit.isTrue(properties, 13);
        int headLen = JTUtils.headerLength(version, isSubpackage);

        RuntimeSchema<JTMessage> headSchema = headerSchemaMap.get(version);
        RuntimeSchema<JTMessage> bodySchema = schemaManager.getRuntimeSchema(messageId, version);

        JTMessage message;
        if (bodySchema == null)
            message = new JTMessage();
        else
            message = bodySchema.newInstance();
        message.setVerified(verified);
        message.setPayload(input);

        int writerIndex = buf.writerIndex();
        buf.writerIndex(headLen);
        headSchema.mergeFrom(buf, message, explain);
        buf.writerIndex(writerIndex - 1);

        int realVersion = message.getProtocolVersion();
        if (realVersion != version)
            bodySchema = schemaManager.getRuntimeSchema(messageId, realVersion);

        if (bodySchema != null) {
            int bodyLen = message.getBodyLength();

            if (isSubpackage) {

                ByteBuf bytes = ALLOC.buffer(bodyLen);
                buf.getBytes(headLen, bytes);

                ByteBuf[] packages = addAndGet(message, bytes);
                if (packages == null)
                    return message;

                ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
                bodySchema.mergeFrom(bodyBuf, message, explain);
                if (message.noBuffer()) {
                    bodyBuf.release();
                }
            } else {
                buf.readerIndex(headLen);
                bodySchema.mergeFrom(buf, message, explain);
            }
        }
        return message;
    }

    protected ByteBuf[] addAndGet(JTMessage message, ByteBuf bytes) {
        return null;
    }

    /** 校验 */
    public static boolean verify(ByteBuf buf) {
        byte checkCode = JTUtils.bcc(buf, -1);
        return checkCode == buf.getByte(buf.writerIndex() - 1);
    }

    /** 反转义 */
    public static ByteBuf unescape(ByteBuf source) {
        int low = source.readerIndex();
        int high = source.writerIndex();

        if (source.getByte(low) == 0x7e)
            low++;

        if (source.getByte(high - 1) == 0x7e)
            high--;

        int mark = source.indexOf(low, high - 1, (byte) 0x7d);
        if (mark == -1) {
            return source.slice(low, high - low);
        }

        List<ByteBuf> bufList = new ArrayList<>(3);

        int len;
        do {

            len = mark + 2 - low;
            bufList.add(slice(source, low, len));
            low += len;

            mark = source.indexOf(low, high, (byte) 0x7d);
        } while (mark > 0);

        bufList.add(source.slice(low, high - low));

        return new CompositeByteBuf(ALLOC, false, bufList.size(), bufList);
    }

    /** 截取转义前报文，并还原转义位 */
    protected static ByteBuf slice(ByteBuf byteBuf, int index, int length) {
        byte second = byteBuf.getByte(index + length - 1);
        if (second == 0x01) {
            return byteBuf.slice(index, length - 1);
        } else if (second == 0x02) {
            byteBuf.setByte(index + length - 2, 0x7e);
            return byteBuf.slice(index, length - 1);
        } else {
            return byteBuf.slice(index, length);
        }
    }
}