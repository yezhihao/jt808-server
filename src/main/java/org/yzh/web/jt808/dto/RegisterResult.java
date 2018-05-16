package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

public class RegisterResult extends PackageData<Header> {

    public static final int Success = 0;
    public static final int CarAlreadyRegistered = 1;
    public static final int CarNotFound = 2;
    public static final int TerminalAlreadyRegistered = 3;
    public static final int TerminalNotFound = 4;

    private Integer flowId;
    private Integer resultCode;
    private String replyToken;

    public RegisterResult() {
    }

    public RegisterResult(Header header, Integer resultCode, String replyToken) {
        super(header);
        this.flowId = header.getFlowId();
        this.resultCode = resultCode;
        this.replyToken = replyToken;
    }

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号,对应的终端注册消息的流水号")
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    @Property(index = 2, type = DataType.BYTE, desc = "结果（0:成功,1:车辆已被注册,2:数据库中无该车辆）")
    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer code) {
        this.resultCode = code;
    }

    @Property(index = 3, type = DataType.STRING, desc = "鉴权码,只有在成功后才有该字段")
    public String getReplyToken() {
        return replyToken;
    }

    public void setReplyToken(String token) {
        this.replyToken = token;
    }

}