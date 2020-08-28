package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;

/**
 * 补传数据包信息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message
public class DataInfo {

    private long offset;
    private long length;

    public DataInfo(long offset, long length) {
        this.offset = offset;
        this.length = length;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "数据偏移量")
    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Field(index = 1, type = DataType.DWORD, desc = "数据长度")
    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}