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
@Message(JT808.临时位置跟踪控制)
public class T8202 extends AbstractMessage<Header> {

    private int interval;
    private int validityPeriod;

    public T8202() {
    }

    public T8202(Header header, int interval, int validityPeriod) {
        super(header);
        this.interval = interval;
        this.validityPeriod = validityPeriod;
    }

    @Field(index = 0, type = DataType.WORD, desc = "时间间隔（秒）")
    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Field(index = 2, type = DataType.DWORD, desc = "有效期（秒）")
    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}