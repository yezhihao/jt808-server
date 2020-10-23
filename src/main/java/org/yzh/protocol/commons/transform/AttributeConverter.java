package org.yzh.protocol.commons.transform;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.IdStrategy;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.converter.Converter;

public class AttributeConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(AttributeConverter.class);

    private static final IdStrategy INSTANCE = AttributeTypes.INSTANCE;

    @Override
    public Object convert(Integer key, ByteBuf input) {
        Schema schema = INSTANCE.getSchema(key);
        if (schema != null)
            return INSTANCE.readFrom(key, input);
        byte[] bytes = new byte[input.readableBytes()];
        input.readBytes(bytes);
        log.warn("未识别的附加信息：ID[{}], HEX[{}]", key, ByteBufUtil.hexDump(bytes));
        return bytes;

    }

    @Override
    public void convert(Integer key, ByteBuf output, Object value) {
        Schema schema = INSTANCE.getSchema(key);
        if (schema != null) {
            schema.writeTo(output, value);
        } else {
            log.warn("未注册的附加信息：ID[{}], Value[{}]", key, value);
        }
    }
}