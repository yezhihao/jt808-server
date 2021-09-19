package org.yzh.protocol.commons.transform.passthrough;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态查询
 * 外设状态信息：外设工作状态、设备报警信息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
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
            items = new ArrayList<>(4);
        items.add(new Item(id, workState, alarmStatus));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(150);
        sb.append("PeripheralStatus{");
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }

    public static class Item {
        /**
         * 外设ID：
         * 100.高级驾驶辅助系统(ADAS)
         * 101.驾驶员状态监控系统(DSM)
         * 102.轮胎气压监测系统(TPMS)
         * 103.盲点监测系统(BSD)
         */
        private byte id;
        /** 工作状态：1.正常工作 2.待机状态 3.升级维护 4.设备异常 16.断开连接 */
        private byte workState;
        /**
         * 报警状态：
         * 按位设置：0.表示无 1.表示有
         * [0]摄像头异常
         * [1]主存储器异常
         * [2]辅存储器异常
         * [3]红外补光异常
         * [4]扬声器异常
         * [5]电池异常
         * [6~9]预留
         * [10]通讯模块异常
         * [ll]定位模块异常
         * [12~31]预留
         */
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
            final StringBuilder sb = new StringBuilder(50);
            sb.append("{id=").append(id);
            sb.append(", workState=").append(workState);
            sb.append(", alarmStatus=").append(Integer.toBinaryString(alarmStatus));
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<PeripheralStatus> {

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