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
 */
@Message(JT808.查询服务器时间应答)
public class T8004 extends AbstractMessage<Header> {

    private String dateTime;

    public T8004() {
    }

    public T8004(String dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 0, type = DataType.BCD8421, length = 6, desc = "UTC时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}