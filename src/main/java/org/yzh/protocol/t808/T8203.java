package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.model.Response;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.人工确认报警消息)
public class T8203 extends AbstractMessage<Header> implements Response {

    private int serialNo;
    private int type;


    public T8203() {
    }

    public T8203(String mobileNo, int serialNo, int type) {
        super(new Header(mobileNo, JT808.人工确认报警消息));
        this.serialNo = serialNo;
        this.type = type;
    }


    @Field(index = 0, type = DataType.WORD, desc = "消息流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * [ 0 ]   1：确认紧急报警；
     * [1-2]   保留
     * [ 3 ]   1：确认危险预警；
     * [4-19]  保留
     * [ 20 ]  1：确认进出区域报警；
     * [ 21 ]  1：确认进出路线报警；
     * [ 22 ]  1：确认路段行驶时间不足/过长报警；
     * [23-26] 保留
     * [ 27 ]  1：确认车辆非法点火报警；
     * [ 28 ]  1：确认车辆非法位移报警；
     * [29-31] 保留
     */
    @Field(index = 2, type = DataType.DWORD, desc = "报警类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}