package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 里程，DWORD，1/10km，对应车上里程表读数
 * length 4
 */
public class Mileage extends Attribute {

    public static final int attributeId = 0x01;
    private int value;

    public Mileage() {
    }

    public Mileage(int value) {
        this.value = value;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static class Schema implements org.yzh.framework.orm.Schema<Mileage> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public Mileage readFrom(ByteBuf input) {
            Mileage message = new Mileage();
            message.value = (int) input.readUnsignedInt();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, Mileage message) {
            output.writeInt(message.value);
        }
    }
}