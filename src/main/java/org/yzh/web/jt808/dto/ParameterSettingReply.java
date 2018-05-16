package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

import java.util.List;

/**
 * 终端参数
 */
public class ParameterSettingReply extends PackageData<Header> {

    private Integer flowId;

    private Integer total;

    private List<TerminalParameter> terminalParameters;

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    @Property(index = 2, type = DataType.BYTE, desc = "应答参数个数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 3, type = DataType.LIST, desc = "参数项列表")
    public List<TerminalParameter> getTerminalParameters() {
        return terminalParameters;
    }

    public void setTerminalParameters(List<TerminalParameter> terminalParameters) {
        this.terminalParameters = terminalParameters;
    }

}