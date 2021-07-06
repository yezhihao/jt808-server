package org.yzh.protocol.codec;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.ProtostarUtil;
import io.github.yezhihao.protostar.schema.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JTUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JT协议解码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageDecoder {

    private Map<Integer, RuntimeSchema<JTMessage>> headerSchemaMap;

    public JTMessageDecoder(String basePackage) {
        ProtostarUtil.initial(basePackage);
        this.headerSchemaMap = ProtostarUtil.getRuntimeSchema(JTMessage.class);
    }

    public JTMessage decode(ByteBuf input) {
        return decode(input, null);
    }

    public JTMessage decode(ByteBuf input, Session session) {
        ByteBuf buf = unescape(input);

        boolean verified = verify(buf);
        int messageId = buf.getUnsignedShort(0);
        int properties = buf.getUnsignedShort(2);

        int version = 0;//缺省值为2013版本
        if (Bit.get(properties, 14))//识别2019及后续版本
            version = (int) buf.getUnsignedByte(4);

        boolean isSubpackage = Bit.get(properties, 13);
        int headLen = JTUtils.headerLength(version, isSubpackage);

        RuntimeSchema<JTMessage> headSchema = headerSchemaMap.get(version);
        RuntimeSchema<JTMessage> bodySchema = ProtostarUtil.getRuntimeSchema(messageId, version);

        JTMessage message;
        if (bodySchema == null)
            message = new JTMessage();
        else
            message = bodySchema.newInstance();
        message.setVerified(verified);
        message.setSession(session);
        message.setPayload(input);

        headSchema.mergeFrom(buf.slice(0, headLen), message);

        int realVersion = message.getVersionNo();
        if (realVersion != version)
            bodySchema = ProtostarUtil.getRuntimeSchema(messageId, realVersion);

        if (bodySchema != null) {
            int bodyLen = message.getBodyLength();

            if (isSubpackage) {

                byte[] bytes = new byte[bodyLen];
                buf.getBytes(headLen, bytes);

                byte[][] packages = addAndGet(message, bytes);
                if (packages == null)
                    return message;

                ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
                bodySchema.mergeFrom(bodyBuf, message);

            } else {
                bodySchema.mergeFrom(buf.slice(headLen, bodyLen), message);
            }
        }
        return message;
    }

    protected byte[][] addAndGet(JTMessage message, byte[] bytes) {
        return null;
    }

    /** 校验 */
    public static boolean verify(ByteBuf buf) {
        byte checkCode = buf.getByte(buf.readableBytes() - 1);
        buf = buf.slice(0, buf.readableBytes() - 1);
        byte calculatedCheckCode = JTUtils.bcc(buf);

        return checkCode == calculatedCheckCode;
    }

    /** 反转义 */
    public static ByteBuf unescape(ByteBuf source) {
        int low = source.readerIndex();
        int high = source.writerIndex();
        int last = high - 1;

        if (source.getByte(0) == 0x7e)
            low = low + 1;

        if (source.getByte(last) == 0x7e)
            high = last;

        int mark = source.indexOf(low, high, (byte) 0x7d);
        if (mark == -1) {
            if (low > 0 || high == last)
                return source.slice(low, high - low);
            return source;
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

        return new CompositeByteBuf(UnpooledByteBufAllocator.DEFAULT, false, bufList.size(), bufList);
    }

    /** 截取转义前报文，并还原转义位 */
    protected static ByteBuf slice(ByteBuf byteBuf, int index, int length) {
        byte second = byteBuf.getByte(index + length - 1);
        if (second == 0x02) {
            byteBuf.setByte(index + length - 2, 0x7e);
        }
        return byteBuf.slice(index, length - 1);
    }
}