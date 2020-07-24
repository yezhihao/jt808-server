package org.yzh.web.jt.t808;

import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.摄像头立即拍摄命令应答)
public class T0805 extends AbstractMessage<Header> {

    private Integer serialNo;
    private Integer result;
    private Integer total;
    private byte[] idList;

    public T0805() {
    }

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "结果")
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Field(index = 2, type = DataType.WORD, desc = "多媒体ID个数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 4, type = DataType.BYTES, desc = "多媒体ID列表")
    public byte[] getIdList() {
        return idList;
    }

    public void setIdList(byte[] idList) {
        this.idList = idList;
    }
}