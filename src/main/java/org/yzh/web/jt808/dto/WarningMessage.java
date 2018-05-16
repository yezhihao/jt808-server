package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 人工确认报警消息
 */
public class WarningMessage extends PackageData<Header> {

    private Integer flowId;

    private Integer type;

    @Property(index = 0, type = DataType.WORD, desc = "消息流水号")
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    @Property(index = 2, type = DataType.DWORD, desc = "报警类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}