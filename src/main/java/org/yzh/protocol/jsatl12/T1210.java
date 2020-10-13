package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JSATL12;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JSATL12.报警附件信息消息)
public class T1210 extends AbstractMessage<Header> {

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

    /**
     * 0x00：正常报警文件信息
     * 0x01：补传报警文件信息
     */
    @Field(index = 55, type = DataType.BYTE, desc = "信息类型")
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
        private int nameLength;
        private String name;
        private long size;

        @Field(index = 0, type = DataType.BYTE, desc = "文件名称长度")
        public int getNameLength() {
            return nameLength;
        }

        public void setNameLength(int nameLength) {
            this.nameLength = nameLength;
        }

        @Field(index = 1, type = DataType.STRING, lengthName = "nameLength", desc = "文件名称")
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