package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询指定终端参数)
public class T8106 extends JTMessage {

    private int total;
    private int[] id;

    public T8106() {
    }

    public T8106(int... id) {
        this.id = id;
        this.total = id.length;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "参数总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.DWORD, desc = "参数ID列表")
    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
        this.total = id.length;
    }
}