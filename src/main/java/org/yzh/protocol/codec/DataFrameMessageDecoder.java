package org.yzh.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.ByteBufUtils;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * 数据帧解码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DataFrameMessageDecoder extends JTMessageDecoder {

    private Class<? extends AbstractMessage> dataFrameClass;
    private byte[] dataFramePrefix;

    public DataFrameMessageDecoder(String basePackage, Class<? extends AbstractMessage> dataFrameClass, byte[] dataFramePrefix) {
        super(basePackage);
        this.dataFrameClass = dataFrameClass;
        this.dataFramePrefix = dataFramePrefix;
    }

    public AbstractMessage decode(ByteBuf buf, int version) {
        if (ByteBufUtils.startsWith(buf, dataFramePrefix))
            return decode(buf, dataFrameClass, 0);
        return super.decode(buf, 0);
    }
}