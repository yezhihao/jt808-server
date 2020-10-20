package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.mvc.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.mvc.model.Response;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.终端通用应答, JT808.平台通用应答})
public class T0001 extends AbstractMessage<Header> implements Response {

    public static final int Success = 0; //成功、确认
    public static final int Failure = 1;//失败
    public static final int MessageError = 2;//消息有误
    public static final int NotSupport = 3;//不支持
    public static final int AlarmAck = 4;//报警处理确认

    private int serialNo;
    private int replyId;
    private int resultCode;

    public T0001() {
    }

    public T0001(String mobileNo, int serialNo) {
        super(new Header(JT808.终端通用应答, serialNo, mobileNo));
    }

    public T0001(int serialNo, String mobileNo) {
        super(new Header(JT808.平台通用应答, serialNo, mobileNo));
    }

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.WORD, desc = "应答ID")
    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "结果（响应码）")
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}