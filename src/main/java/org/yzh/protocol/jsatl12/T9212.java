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
@Message(JSATL12.文件上传完成消息应答)
public class T9212 extends JTMessage {

    @Field(index = 0, type = DataType.STRING, lengthSize = 1, desc = "文件名称")
    private String name;
    @Field(index = 1, type = DataType.BYTE, desc = "文件类型 0.图片 1.音频 2.视频 3.文本 4.其它")
    private int type;
    @Field(index = 2, type = DataType.BYTE, desc = "上传结果")
    private int result;
    @Field(index = 3, type = DataType.BYTE, desc = "补传数据包数量")
    private int total;
    @Field(index = 4, type = DataType.LIST, desc = "补传数据包列表")
    private List<DataInfo> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataInfo> getItems() {
        return items;
    }

    public void setItems(List<DataInfo> items) {
        this.items = items;
    }
}