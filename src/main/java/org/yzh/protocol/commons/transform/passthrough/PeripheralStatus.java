package org.yzh.protocol.commons.transform.passthrough;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;

import java.util.List;

public class PeripheralStatus {

    public static final int ID = 0xF7;

    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE)
    public int getTotal() {
        if (items != null)
            return items.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.LIST)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PeripheralStatus{");
        sb.append("total=").append(total);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }

    public static class Item {
        private byte workState;
        private int alarmStatus;

        @Field(index = 0, type = DataType.BYTE, version = 0)
        public byte getWorkState() {
            return workState;
        }

        public void setWorkState(byte workState) {
            this.workState = workState;
        }

        @Field(index = 1, type = DataType.DWORD, version = 0)
        public int getAlarmStatus() {
            return alarmStatus;
        }

        public void setAlarmStatus(int alarmStatus) {
            this.alarmStatus = alarmStatus;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append('{');
            sb.append("workState=").append(workState);
            sb.append(", alarmStatus=").append(alarmStatus);
            sb.append('}');
            return sb.toString();
        }
    }
}
