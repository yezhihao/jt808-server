package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.单条存储多媒体数据检索上传命令)
public class T8805 extends JTMessage {

    @Field(index = 0, type = DataType.DWORD, desc = "多媒体ID(大于0)")
    private int id;
    @Field(index = 4, type = DataType.BYTE, desc = "删除标志：0.保留 1.删除 ")
    private int delete;

    public T8805() {
    }

    public T8805(int id, int delete) {
        this.id = id;
        this.delete = delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}