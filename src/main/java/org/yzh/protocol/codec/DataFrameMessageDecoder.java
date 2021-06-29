package org.yzh.protocol.codec;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.util.ByteBufUtils;
import io.github.yezhihao.protostar.ProtostarUtil;
import io.github.yezhihao.protostar.schema.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.jsatl12.DataPacket;
import org.yzh.web.endpoint.JTHandlerInterceptor;

/**
 * 数据帧解码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DataFrameMessageDecoder extends JTMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(DataFrameMessageDecoder.class.getSimpleName());

    private RuntimeSchema<DataPacket> dataFrameSchema;
    private byte[] dataFramePrefix;

    public DataFrameMessageDecoder(String basePackage, byte[] dataFramePrefix) {
        super(basePackage);
        this.dataFramePrefix = dataFramePrefix;
        this.dataFrameSchema = ProtostarUtil.getRuntimeSchema(DataPacket.class, 0);
    }

    @Override
    public JTMessage decode(ByteBuf input, Session session) {
        if (ByteBufUtils.startsWith(input, dataFramePrefix)) {
            DataPacket message = new DataPacket();
            message.setSession(session);
            message.setPayload(input);
            dataFrameSchema.mergeFrom(input, message);

            if (log.isInfoEnabled() && JTHandlerInterceptor.filter(message))
                log.info("<<<<<session={},payload={}", session, ByteBufUtil.hexDump(input.readerIndex(0)));
            return message;
        }
        return super.decode(input, session);
    }
}