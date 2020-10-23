package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * IO 状态位，定义见表 32
 * length 2
 */
public class IoState extends Attribute {

    public static final int attributeId = 0x2a;
    private int value;

    public IoState() {
    }

    public IoState(int value) {
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

    public static class Schema implements org.yzh.framework.orm.Schema<IoState> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public IoState readFrom(ByteBuf input) {
            IoState message = new IoState();
            message.value = input.readUnsignedShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, IoState message) {
            output.writeShort(message.value);
        }
    }
}