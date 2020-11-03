package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.IdStrategy;
import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.converter.Converter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.KeyValuePair;

public class PassthroughConverter implements Converter<KeyValuePair<Integer, Object>> {

    private static final Logger log = LoggerFactory.getLogger(PassthroughConverter.class);

    private static final IdStrategy INSTANCE = PassthroughType.INSTANCE;

    @Override
    public KeyValuePair<Integer, Object> convert(ByteBuf input) {
        if (!input.isReadable())
            return null;
        int key = input.readUnsignedByte();
        Schema schema = INSTANCE.getSchema(key);
        if (schema != null)
            return KeyValuePair.of(key, INSTANCE.readFrom(key, input));
        byte[] bytes = new byte[input.readableBytes()];
        input.readBytes(bytes);
        log.warn("未识别的透传消息：ID[{}], HEX[{}]", key, ByteBufUtil.hexDump(bytes));
        return KeyValuePair.of(key, bytes);
    }

    @Override
    public void convert(ByteBuf output, KeyValuePair<Integer, Object> keyValuePair) {
        Integer key = keyValuePair.getId();
        Object value = keyValuePair.getValue();
        Schema schema = INSTANCE.getSchema(key);
        if (schema != null) {
            output.writeByte(key);
            schema.writeTo(output, value);
        } else {
            if (value instanceof byte[]) {
                output.writeByte(key);
                output.writeBytes((byte[]) value);
            }
            log.warn("未注册的透传消息：ID[{}], Value[{}]", key, keyValuePair);
        }
    }
}