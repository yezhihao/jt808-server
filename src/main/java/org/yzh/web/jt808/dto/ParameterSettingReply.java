package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.common.MessageId;
import org.yzh.web.jt808.dto.basics.Header;
import org.yzh.web.jt808.dto.basics.TerminalParameter;

import java.util.List;

@Type(MessageId.查询终端参数应答)
public class ParameterSettingReply extends PackageData<Header> {

    private Integer serialNumber;
    private Integer total;

    private List<TerminalParameter> terminalParameters;

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
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