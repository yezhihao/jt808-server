package org.yzh.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.commons.transform.ByteBufUtils;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractMessage;

/**
 * 数据帧解码器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DataFrameMessageDecoder extends JTMessageDecoder {

    private BeanMetadata<? extends AbstractMessage> dataFrameMetadata;
    private byte[] dataFramePrefix;

    public DataFrameMessageDecoder(String basePackage, Class<? extends AbstractMessage> dataFrameClass, byte[] dataFramePrefix) {
        super(basePackage);
        this.dataFrameMetadata = MessageHelper.getBeanMetadata(dataFrameClass, 0);
        this.dataFramePrefix = dataFramePrefix;
    }

    public AbstractMessage decode(ByteBuf buf, int version) {
        if (ByteBufUtils.startsWith(buf, dataFramePrefix))
            return super.decode(buf, dataFrameMetadata);
        return super.decode(buf, 0);
    }
}