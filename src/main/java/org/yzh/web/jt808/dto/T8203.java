package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(MessageId.人工确认报警消息)
public class T8203 extends AbstractBody {

    private Integer serialNo;
    private Integer type;

    @Property(index = 0, type = DataType.WORD, desc = "消息流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
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
    @Property(index = 2, type = DataType.DWORD, desc = "报警类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}