package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JSATL12;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JSATL12.报警附件信息消息)
public class T1210 extends JTMessage {

    @Field(index = 0, type = DataType.STRING, length = 7, desc = "终端ID", version = {-1, 0})
    @Field(index = 0, type = DataType.STRING, length = 30, desc = "终端ID(粤标)", version = 1)
    private String deviceId;
    @Field(index = 7, type = DataType.OBJ, length = 16, desc = "报警标识号", version = {-1, 0})
    @Field(index = 30, type = DataType.OBJ, length = 40, desc = "报警标识号(粤标)", version = 1)
    private AlarmId alarmId;
    @Field(index = 70, type = DataType.STRING, length = 32, desc = "报警编号")
    private String alarmNo;
    @Field(index = 102, type = DataType.BYTE, desc = "信息类型：0.正常报警文件信息 1.补传报警文件信息")
    private int type;
    @Field(index = 103, type = DataType.BYTE, desc = "附件数量")
    private int total;
    @Field(index = 104, type = DataType.LIST, desc = "附件信息列表")
    private List<Item> items;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmNo() {
        return alarmNo;
    }

    public void setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        @Field(index = 0, type = DataType.STRING, lengthSize = 1, desc = "文件名称")
        private String name;
        @Field(index = 1, type = DataType.DWORD, desc = "文件大小")
        private long size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(80);
            sb.append("{name=").append(name);
            sb.append(",size=").append(size);
            sb.append('}');
            return sb.toString();
        }
    }
}