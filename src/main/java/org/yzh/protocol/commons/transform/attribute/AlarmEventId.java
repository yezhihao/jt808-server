package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 需要人工确认报警事件的 ID，WORD，从 1 开始计数
 * length 2
 */
public class AlarmEventId extends Attribute {

    public static final int attributeId = 0x04;
    private int value;

    public AlarmEventId() {
    }

    public AlarmEventId(int value) {
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<AlarmEventId> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public AlarmEventId readFrom(ByteBuf input) {
            AlarmEventId message = new AlarmEventId();
            message.value = input.readUnsignedShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, AlarmEventId message) {
            output.writeShort(message.value);
        }
    }
}