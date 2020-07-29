package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.ResultCode;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message({JT808.终端通用应答, JT808.平台通用应答})
public class T0001 extends AbstractMessage<Header> {

    private Integer serialNo;
    private Integer replyId;
    /** @see ResultCode */
    private Integer resultCode;

    public T0001() {
    }

    public T0001(Integer replyId, Integer serialNo, Integer resultCode) {
        this.replyId = replyId;
        this.serialNo = serialNo;
        this.resultCode = resultCode;
    }

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.WORD, desc = "应答ID")
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "结果（响应码）")
    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

}