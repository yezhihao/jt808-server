package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

import java.util.List;

/**
 * 终端参数
 */
public class ParameterSetting extends PackageData<Header> {

    private Integer total;

    private List<TerminalParameter> parameters;

    @Property(index = 0, type = DataType.BYTE, desc = "参数总数")
    public Integer getTotal() {
        if (parameters == null || parameters.isEmpty())
            return 0;
        return parameters.size();
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 1, type = DataType.LIST, desc = "参数项列表")
    public List<TerminalParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<TerminalParameter> parameters) {
        this.parameters = parameters;
    }
}