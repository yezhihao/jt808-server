package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.JSATL12;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JSATL12.文件信息上传, JSATL12.文件上传完成消息})
public class T1211 extends AbstractMessage<Header> {

    private int nameLength;
    private String name;
    private int type;
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
        this.nameLength = name.getBytes(Charsets.GBK).length;
    }

    @Field(index = 1, indexOffsetName = "nameLength", type = DataType.BYTE, desc = "文件类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 2, indexOffsetName = "nameLength", type = DataType.DWORD, desc = "文件大小")
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}