package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.IdStrategy;
import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.converter.MapConverter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 终端参数项转换器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParameterConverter extends MapConverter<Integer, Object> {

    private static final Logger log = LoggerFactory.getLogger(ParameterConverter.class);

    private static final IdStrategy INSTANCE = ParameterType.INSTANCE;

    @Override
    public Object convert(Integer key, ByteBuf input) {
        if (!input.isReadable())
            return null;
        Schema schema = INSTANCE.getSchema(key);
        if (schema != null)
            return INSTANCE.readFrom(key, input);
        byte[] bytes = new byte[input.readableBytes()];
        input.readBytes(bytes);
        log.warn("未识别的终端参数项:ID[dec:{},hex:{}], VALUE[{}]", key, Integer.toHexString(key), ByteBufUtil.hexDump(bytes));
        return bytes;
    }

    @Override
    public void convert(Integer key, ByteBuf output, Object value) {
        Schema schema = INSTANCE.getSchema(key);
        if (schema != null) {
            schema.writeTo(output, value);
        } else {
            log.warn("未注册的终端参数项:ID[dec:{},hex:{}], VALUE[{}]", key, Integer.toHexString(key), value);
        }
    }

    @Override
    protected Integer readKey(ByteBuf input) {
        return input.readInt();
    }

    @Override
    protected void writeKey(ByteBuf output, Integer key) {
        output.writeInt(key);
    }

    @Override
    protected int valueSize() {
        return 1;
    }
}