package org.yzh.protocol.commons.transform.passthrough;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class PeripheralStatus {

    public static final int ID = 0xF7;

    private List<Item> items;

    public PeripheralStatus() {
    }

    public PeripheralStatus(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(byte id, byte workState, int alarmStatus) {
        if (items == null)
            items = new ArrayList<>();
        items.add(new Item(id, workState, alarmStatus));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append('{');
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }

    public static class Item {
        private byte id;
        private byte workState;
        private int alarmStatus;

        public Item() {
        }

        public Item(byte id, byte workState, int alarmStatus) {
            this.id = id;
            this.workState = workState;
            this.alarmStatus = alarmStatus;
        }

        public byte getId() {
            return id;
        }

        public void setId(byte id) {
            this.id = id;
        }

        public byte getWorkState() {
            return workState;
        }

        public void setWorkState(byte workState) {
            this.workState = workState;
        }

        public int getAlarmStatus() {
            return alarmStatus;
        }

        public void setAlarmStatus(int alarmStatus) {
            this.alarmStatus = alarmStatus;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(32);
            sb.append('{');
            sb.append("workState=").append(workState);
            sb.append(", alarmStatus=").append(alarmStatus);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Schema implements org.yzh.framework.orm.Schema<PeripheralStatus> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public PeripheralStatus readFrom(ByteBuf input) {
            byte total = input.readByte();
            List<Item> list = new ArrayList<>(total);
            while (input.isReadable()) {
                Item item = new Item();
                item.id = input.readByte();
                input.readByte();
                item.workState = input.readByte();
                item.alarmStatus = input.readInt();
                list.add(item);
            }
            return new PeripheralStatus(list);
        }

        @Override
        public void writeTo(ByteBuf output, PeripheralStatus message) {
            List<Item> items = message.getItems();
            output.writeByte(items.size());

            for (Item item : items) {
                output.writeByte(item.id);
                output.writeByte(5);

                output.writeByte(item.workState);
                output.writeInt(item.alarmStatus);
            }
        }
    }
}