package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 里程
 * length 2 油量，WORD，1/10L，对应车上油量表读数
 */
public class Oil extends Attribute {

    public static final int attributeId = 0x02;
    private int value;

    public Oil() {
    }

    public Oil(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<Oil> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public Oil readFrom(ByteBuf input) {
            Oil message = new Oil();
            message.value = input.readUnsignedShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, Oil message) {
            output.writeShort(message.value);
        }
    }
}