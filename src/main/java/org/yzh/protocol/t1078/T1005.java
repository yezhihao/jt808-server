package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.终端上传乘客流量)
public class T1005 extends JTMessage {

    @Field(index = 0, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
    private String startTime;
    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
    private String endTime;
    @Field(index = 12, type = DataType.WORD, desc = "从起始时间到结束时间的上车人数")
    private int getOnCount;
    @Field(index = 14, type = DataType.WORD, desc = "从起始时间到结束时间的下车人数")
    private int getOffCount;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getGetOnCount() {
        return getOnCount;
    }

    public void setGetOnCount(int getOnCount) {
        this.getOnCount = getOnCount;
    }

    public int getGetOffCount() {
        return getOffCount;
    }

    public void setGetOffCount(int getOffCount) {
        this.getOffCount = getOffCount;
    }
}