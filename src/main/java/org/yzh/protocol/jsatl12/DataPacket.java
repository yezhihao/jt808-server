package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;

import java.nio.ByteBuffer;

/**
 * 文件数据上传
 * 帧头标识 0x30 0x31 0x63 0x64
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
//@Message(prefix = 0x30316364)
public class DataPacket {

    private int flag;
    private String name;
    private long offset;
    private long length;
    private ByteBuffer data;


    @Field(index = 0, type = DataType.DWORD, desc = "帧头标识")
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Field(index = 4, type = DataType.STRING, length = 50, desc = "文件名称")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(index = 54, type = DataType.DWORD, desc = "数据偏移量")
    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Field(index = 58, type = DataType.DWORD, desc = "数据长度")
    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Field(index = 62, type = DataType.BYTES, desc = "数据体")
    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }
}