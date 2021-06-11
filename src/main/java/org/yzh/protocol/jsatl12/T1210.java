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

    private String terminalId;
    private AlarmId alarmId;
    private String alarmNo;
    private int type;
    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.STRING, length = 7, desc = "终端ID")
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Field(index = 7, type = DataType.OBJ, length = 16, desc = "报警标识号")
    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Field(index = 23, type = DataType.STRING, length = 32, desc = "报警编号")
    public String getAlarmNo() {
        return alarmNo;
    }

    public void setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
    }

    @Field(index = 55, type = DataType.BYTE, desc = "信息类型: 0.正常报警文件信息 1.补传报警文件信息")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 56, type = DataType.BYTE, desc = "附件数量")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 57, type = DataType.LIST, desc = "附件信息列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private String name;
        private long size;

        @Field(index = 1, type = DataType.STRING, lengthSize = 1, desc = "文件名称")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Field(index = 1, type = DataType.DWORD, desc = "文件大小")
        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }
}