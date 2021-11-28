package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.PrepareLoadStrategy;
import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.converter.Converter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.KeyValuePair;
import org.yzh.protocol.commons.transform.passthrough.PeripheralStatus;
import org.yzh.protocol.commons.transform.passthrough.PeripheralSystem;

/**
 * 透传消息转换器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class PassthroughConverter extends PrepareLoadStrategy implements Converter<KeyValuePair<Integer, Object>> {

    private static final Logger log = LoggerFactory.getLogger(PassthroughConverter.class);

    @Override
    protected void addSchemas(PrepareLoadStrategy schemaRegistry) {
        schemaRegistry
                .addSchema(PeripheralStatus.ID, PeripheralStatus.Schema.INSTANCE)
                .addSchema(PeripheralSystem.ID, PeripheralSystem.Schema.INSTANCE);
    }

    @Override
    public KeyValuePair<Integer, Object> convert(ByteBuf input) {
        if (!input.isReadable())
            return null;
        int key = input.readUnsignedByte();
        Schema schema = getSchema(key);
        if (schema != null)
            return KeyValuePair.of(key, schema.readFrom(input));
        byte[] bytes = new byte[input.readableBytes()];
        input.readBytes(bytes);
        log.debug("未识别的透传消息:ID[dec:{},hex:{}], VALUE[{}]", key, Integer.toHexString(key), ByteBufUtil.hexDump(bytes));
        return KeyValuePair.of(key, bytes);
    }

    @Override
    public void convert(ByteBuf output, KeyValuePair<Integer, Object> keyValuePair) {
        Integer key = keyValuePair.getId();
        Object value = keyValuePair.getValue();
        Schema schema = getSchema(key);
        if (schema != null) {
            output.writeByte(key);
            schema.writeTo(output, value);
        } else {
            if (value instanceof byte[]) {
                output.writeByte(key);
                output.writeBytes((byte[]) value);
                log.warn("未注册的透传消息:ID[dec:{},hex:{}], VALUE[{}]", key, Integer.toHexString(key), ByteBufUtil.hexDump((byte[]) value));
            } else {
                log.warn("未注册的透传消息:ID[dec:{},hex:{}], VALUE[{}]", key, Integer.toHexString(key), value);
            }
        }
    }
}