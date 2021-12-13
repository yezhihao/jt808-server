package org.yzh.protocol.t808;

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

    @Field(totalUnit = 1, desc = "区域列表")
    private int[] id;

    public T8601() {
    }

    public T8601(int... id) {
        this.id = id;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }
}