package org.yzh.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.ByteBufUtils;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.jsatl12.DataPacket;

/**
 * 数据帧解码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DataFrameMessageDecoder extends JTMessageDecoder {

    private Schema<? extends JTMessage> dataFrameSchema;
    private byte[] dataFramePrefix;

    public DataFrameMessageDecoder(String basePackage, byte[] dataFramePrefix) {
        super(basePackage);
        this.dataFramePrefix = dataFramePrefix;
        this.dataFrameSchema = MessageHelper.getSchema(DataPacket.class, 0);
    }

    @Override
    public JTMessage decode(ByteBuf buf, Session session) {
        if (ByteBufUtils.startsWith(buf, dataFramePrefix))
            return dataFrameSchema.readFrom(buf);
        return super.decode(buf, session);
    }
}