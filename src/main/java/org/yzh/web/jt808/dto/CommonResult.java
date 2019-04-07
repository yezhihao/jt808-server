package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.common.MessageId;
import org.yzh.web.jt808.dto.basics.Header;

@Type({MessageId.平台通用应答, MessageId.终端通用应答})
public class CommonResult extends PackageData<Header> {

    public static final int Success = 0;
    public static final int Fial = 1;
    public static final int MessageError = 2;
    public static final int NotSupport = 3;
    public static final int AlarmConfirmation = 4;

    private Integer flowId;

    private Integer replyId;

    private Integer resultCode;

    public CommonResult() {
    }

    public CommonResult(Header header, Integer replyId, Integer flowId, Integer resultCode) {
        super(header);
        this.replyId = replyId;
        this.flowId = flowId;
        this.resultCode = resultCode;
    }

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
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