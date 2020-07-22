package org.yzh.web.jt808.dto;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.jt808.common.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(JT808.提问应答)
public class T0302 extends AbstractBody {

    private Integer serialNo;
    private Integer answerId;

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Property(index = 2, type = DataType.BYTE, desc = "答案ID")
    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}