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
@Message(JT808.信息服务)
public class T8304 extends AbstractMessage<Header> {

    private int type;
    private String content;

    public T8304() {
    }

    public T8304(String mobileNo) {
        super(new Header(mobileNo, JT808.信息服务));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "信息类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 3, type = DataType.STRING, lengthSize = 2, desc = "文本信息")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}