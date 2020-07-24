package org.yzh.web.jt.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.basics.TerminalParameter;
import org.yzh.web.jt.common.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.查询终端参数应答)
public class T0104 extends AbstractMessage<Header> {

    private Integer serialNo;
    private Integer total;

    private List<TerminalParameter> terminalParameters;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "应答参数个数")
    public Integer getTotal() {
        if (total == null)
            return terminalParameters.size();
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 3, type = DataType.LIST, desc = "参数项列表")
    public List<TerminalParameter> getTerminalParameters() {
        return terminalParameters;
    }

    public void setTerminalParameters(List<TerminalParameter> terminalParameters) {
        this.terminalParameters = terminalParameters;
    }

    public void addTerminalParameter(TerminalParameter terminalParameter) {
        if (terminalParameters == null)
            terminalParameters = new ArrayList<>();
        terminalParameters.add(terminalParameter);
    }
}