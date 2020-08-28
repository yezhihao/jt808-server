package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.JSATL12;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JSATL12.文件上传完成消息)
public class T9212 extends AbstractMessage<Header> {

    private int nameLength;
    private String name;
    private int type;
    private int result;
    private int total;
    private List<DataInfo> items;

    public T9212() {
    }

    public T9212(int serialNo, String mobileNo) {
        super(new Header(JSATL12.文件上传完成消息, serialNo, mobileNo));
    }

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
        this.nameLength = name.getBytes(Charsets.GBK).length;
    }

    @Field(index = 1, indexOffsetName = "nameLength", type = DataType.BYTE, desc = "文件类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 2, indexOffsetName = "nameLength", type = DataType.BYTE, desc = "上传结果")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Field(index = 3, indexOffsetName = "nameLength", type = DataType.BYTE, desc = "补传数据包数量")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 4, indexOffsetName = "nameLength", type = DataType.LIST, desc = "补传数据包列表")
    public List<DataInfo> getItems() {
        return items;
    }

    public void setItems(List<DataInfo> items) {
        this.items = items;
    }
}