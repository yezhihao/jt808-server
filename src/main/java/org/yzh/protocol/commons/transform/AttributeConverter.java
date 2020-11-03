package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.IdStrategy;
import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.converter.MapConverter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 位置附加信息转换器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class AttributeConverter extends MapConverter<Integer, Object> {

    private static final Logger log = LoggerFactory.getLogger(AttributeConverter.class);

    private static final IdStrategy INSTANCE = AttributeType.INSTANCE;

    @Override
    public Object convert(Integer key, ByteBuf input) {
        if (!input.isReadable())
            return null;
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

    @Override
    protected Integer readKey(ByteBuf input) {
        return (int) input.readUnsignedByte();
    }

    @Override
    protected void writeKey(ByteBuf output, Integer key) {
        output.writeByte(key);
    }

    @Override
    protected int valueSize() {
        return 1;
    }
}