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
@Message({JT808.删除圆形区域, JT808.删除矩形区域, JT808.删除多边形区域, JT808.删除路线})
public class T8601 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "区域总数")
    private int total;
    @Field(index = 1, type = DataType.DWORD, desc = "区域列表")
    private int[] id;

    public T8601() {
    }

    public T8601(int... id) {
        this.id = id;
        this.total = id.length;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
        this.total = id.length;
    }
}