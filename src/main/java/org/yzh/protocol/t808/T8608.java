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
@Message(JT808.查询区域或线路数据)
public class T8608 extends JTMessage {

    /** @see org.yzh.protocol.commons.Shape */
    private int type;
    private int total;
    private int[] id;

    public T8608() {
    }

    public T8608(int type, int... id) {
        this.type = type;
        this.id = id;
        this.total = id.length;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "查询类型: 1.圆形 2.矩形 3.多边形 4.路线")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.DWORD, desc = "区域总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 5, type = DataType.DWORD, desc = "区域列表")
    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
        this.total = id.length;
    }
}