package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;

/**
 * 胎压
 * length 30
 */
public class TirePressure {

    public static final int id = 0x05;

    public static int id() {
        return id;
    }

    private byte[] value;

    public TirePressure() {
    }

    public TirePressure(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TirePressure{");
        sb.append("value=").append(Arrays.toString(value));
        sb.append('}');
        return sb.toString();
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<TirePressure> {

        public static final Schema INSTANCE = new Schema();

        private final int length = 30;

        private Schema() {
        }

        @Override
        public TirePressure readFrom(ByteBuf input) {
            int len = input.readableBytes();
            if (len > length)
                len = length;
            byte[] value = new byte[len];
            input.readBytes(value);
            return new TirePressure(value);
        }

        @Override
        public void writeTo(ByteBuf output, TirePressure message) {
            ByteBufUtils.writeFixedLength(output, length, message.value);
        }
    }
}