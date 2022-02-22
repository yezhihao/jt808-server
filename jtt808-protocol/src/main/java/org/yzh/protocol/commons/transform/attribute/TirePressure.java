package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;

/**
 * 胎压 0x05
 * length 30
 */
public class TirePressure {

    public static final int key = 5;

    public static final Schema<TirePressure> SCHEMA = new TirePressureSchema();

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
        final StringBuilder sb = new StringBuilder(32);
        sb.append("TirePressure{value=").append(Arrays.toString(value));
        sb.append('}');
        return sb.toString();
    }

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
            return new TirePressure(value);
        }

        @Override
        public void writeTo(ByteBuf output, TirePressure message) {
            ByteBufUtils.writeFixedLength(output, 30, message.value);
        }
    }
}