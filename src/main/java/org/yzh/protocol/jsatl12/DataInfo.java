package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;

/**
 * 补传数据包信息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message
public class DataInfo {

    @Field(index = 0, type = DataType.DWORD, desc = "数据偏移量")
    private long offset;
    @Field(index = 1, type = DataType.DWORD, desc = "数据长度")
    private long length;

    public DataInfo() {
    }

    public DataInfo(long offset, long length) {
        this.offset = offset;
        this.length = length;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}