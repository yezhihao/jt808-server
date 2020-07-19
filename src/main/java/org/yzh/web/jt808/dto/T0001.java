package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

@Type({MessageId.终端通用应答, MessageId.平台通用应答})
public class T0001 extends AbstractBody {

    public static final int Success = 0;
    public static final int Fial = 1;
    public static final int MessageError = 2;
    public static final int NotSupport = 3;
    public static final int AlarmConfirmation = 4;

    private Integer serialNo;

    private Integer replyId;

    private Integer resultCode;

    public T0001() {
    }

    public T0001(Integer replyId, Integer serialNo, Integer resultCode) {
        this.replyId = replyId;
        this.serialNo = serialNo;
        this.resultCode = resultCode;
    }

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Property(index = 2, type = DataType.WORD, desc = "应答ID")
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    @Property(index = 4, type = DataType.BYTE, desc = "结果（响应码）")
    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

}