package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 胎压
 * length 30
 */
public class TirePressure extends Attribute {

    public static final int attributeId = 0x05;
    private byte[] value;

    public TirePressure() {
    }

    public TirePressure(byte... value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public static class Schema implements org.yzh.framework.orm.Schema<TirePressure> {

        public static final Schema INSTANCE = new Schema();

        private int length = 30;

        private Schema() {
        }

        @Override
        public TirePressure readFrom(ByteBuf input) {
            TirePressure message = new TirePressure();
            byte[] value = new byte[input.readableBytes()];
            input.readBytes(value);
            message.value = value;
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, TirePressure message) {
            byte[] bytes = message.value;
            int srcPos = length - bytes.length;
            if (srcPos > 0) {
                output.writeBytes(bytes);
                output.writeBytes(new byte[srcPos]);
            } else if (srcPos < 0) {
                output.writeBytes(bytes, -srcPos, length);
            } else {
                output.writeBytes(bytes);
            }
        }
    }
}