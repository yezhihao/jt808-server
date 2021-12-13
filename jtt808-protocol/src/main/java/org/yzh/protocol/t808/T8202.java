package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.临时位置跟踪控制)
public class T8202 extends JTMessage {

    @Field(length = 2, desc = "时间间隔(秒)")
    private int interval;
    @Field(length = 4, desc = "有效期(秒)")
    private int validityPeriod;

    public T8202() {
    }

    public T8202(int interval, int validityPeriod) {
        this.interval = interval;
        this.validityPeriod = validityPeriod;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}