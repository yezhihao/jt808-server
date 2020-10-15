package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JSATL12;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JSATL12.文件信息上传, JSATL12.文件上传完成消息})
public class T1211 extends AbstractMessage<Header> {

    private String name;
    private int type;
    private long size;

    @Field(index = 1, type = DataType.STRING, lengthSize = 1, desc = "文件名称")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "文件类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 2, type = DataType.DWORD, desc = "文件大小")
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}