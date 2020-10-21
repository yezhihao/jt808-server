package org.yzh.protocol.codec;

import io.netty.buffer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bin;
import org.yzh.framework.commons.transform.ByteBufUtils;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JT协议解码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(JTMessageDecoder.class.getSimpleName());

    private Map<Integer, Schema<Header>> headerSchemaMap;

    public JTMessageDecoder(String basePackage) {
        MessageHelper.initial(basePackage);
        this.headerSchemaMap = MessageHelper.getSchema(Header.class);
    }

    public JTMessage decode(ByteBuf buf) {
        return decode(buf, null);
    }

    public JTMessage decode(ByteBuf buf, Session session) {
        buf = unescape(buf);

        boolean verified = verify(buf);
        if (!verified)
            log.error("校验码错误{},{}", session, ByteBufUtil.hexDump(buf));

        int properties = buf.getUnsignedShort(2);

        Integer version = session == null ? null : session.getProtocolVersion();
        boolean confirmedVersion = version != null;
        if (!confirmedVersion) {
            //识别2019及后续版本
            if (Bin.get(properties, 14)) {
                version = (int) buf.getUnsignedByte(4);
                confirmedVersion = true;
                if (session != null)
                    session.setProtocolVersion(version);
            } else {
                //缺省值为2013版本
                version = 0;
            }
        }

        int headLen;
        boolean isSubpackage = Bin.get(properties, 13);
        if (version > 0)
            headLen = isSubpackage ? 21 : 17;
        else
            headLen = isSubpackage ? 16 : 12;

        Schema<? extends Header> headerSchema = headerSchemaMap.get(version);

        Header header = headerSchema.readFrom(buf.slice(0, headLen));
        header.setVerified(verified);

        if (!confirmedVersion && session != null) {
            //通过缓存记录2011版本
            Integer cachedVersion = session.cachedProtocolVersion(header.getClientId());
            if (cachedVersion != null)
                version = cachedVersion;
            session.setProtocolVersion(version);
        }


        JTMessage message;
        Schema<? extends JTMessage> bodySchema = MessageHelper.getSchema(header.getMessageId(), version);
        if (bodySchema != null) {
            int bodyLen = header.getBodyLength();

            if (isSubpackage) {

                byte[] bytes = new byte[bodyLen];
                buf.getBytes(headLen, bytes);

                byte[][] packages = addAndGet(header, bytes);
                if (packages == null)
                    return null;

                ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
                message = bodySchema.readFrom(bodyBuf);

            } else {
                message = bodySchema.readFrom(buf.slice(headLen, bodyLen));
            }
        } else {
            message = new JTMessage();
            log.debug("未找到对应的Schema[{}]", header);
        }

        message.setHeader(header);
        return message;
    }

    protected byte[][] addAndGet(Header header, byte[] bytes) {
        return null;
    }

    /** 校验 */
    protected boolean verify(ByteBuf buf) {
        byte checkCode = buf.getByte(buf.readableBytes() - 1);
        buf = buf.slice(0, buf.readableBytes() - 1);
        byte calculatedCheckCode = ByteBufUtils.bcc(buf);

        return checkCode == calculatedCheckCode;
    }

    /** 反转义 */
    protected ByteBuf unescape(ByteBuf source) {
        int low = source.readerIndex();
        int high = source.writerIndex();

        int mark = source.indexOf(low, high, (byte) 0x7d);
        if (mark == -1)
            return source;

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
    protected ByteBuf slice(ByteBuf byteBuf, int index, int length) {
        byte second = byteBuf.getByte(index + length - 1);
        if (second == 0x02)
            byteBuf.setByte(index + length - 2, 0x7e);

        // 0x01 不做处理 p47
        // if (second == 0x01) {
        // }
        return byteBuf.slice(index, length - 1);
    }
}