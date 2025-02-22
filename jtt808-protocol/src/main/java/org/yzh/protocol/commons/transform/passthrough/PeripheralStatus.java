package org.yzh.protocol.commons.transform.passthrough;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态查询
 * 外设状态信息：外设工作状态、设备报警信息
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class PeripheralStatus {

    public static final Integer key = 0xF7;

    private List<Item> items;


    @ToString
    @Data
    @Accessors(chain = true)
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
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<PeripheralStatus> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public PeripheralStatus readFrom(ByteBuf input) {
            byte total = input.readByte();
            List<Item> list = new ArrayList<>(total);
            for (int i = 0; i < total && input.isReadable(); i++) {
                Item item = new Item();
                item.id = input.readByte();
                int len = input.readUnsignedByte();
                item.workState = input.readByte();
                item.alarmStatus = input.readInt();
                input.skipBytes(len - 5);
                list.add(item);
            }
            return new PeripheralStatus().setItems(list);
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