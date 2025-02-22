package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 胎压 0x05
 * length 30
 */
@ToString
@Data
@Accessors(chain = true)
public class TirePressure {

    public static final Integer key = 5;

    public static final Schema<TirePressure> SCHEMA = new TirePressureSchema();

    private byte[] value;

    private static class TirePressureSchema implements Schema<TirePressure> {

        private TirePressureSchema() {
        }

        @Override
        public TirePressure readFrom(ByteBuf input) {
            int len = input.readableBytes();
            if (len > 30)
                len = 30;
            byte[] value = new byte[len];
            input.readBytes(value);
            return new TirePressure().setValue(value);
        }

        @Override
        public void writeTo(ByteBuf output, TirePressure message) {
            ByteBufUtils.writeFixedLength(output, 30, message.value);
        }
    }
}