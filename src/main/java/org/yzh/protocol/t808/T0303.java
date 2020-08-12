package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@Message(JT808.信息点播_取消)
public class T0303 extends AbstractMessage<Header> {

    private int type;
    private int action;

    @Field(index = 0, type = DataType.BYTE, desc = "消息类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "点播/取消标志 0：取消；1：点播")
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}