package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询指定终端参数)
public class T8106 extends JTMessage {

    @Field(totalUnit = 1, desc = "参数ID列表")
    private int[] id;

    public T8106() {
    }

    public T8106(int... id) {
        this.id = id;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }
}