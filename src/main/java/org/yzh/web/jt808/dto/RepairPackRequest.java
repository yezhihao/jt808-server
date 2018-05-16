package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 补传分包请求
 */
public class RepairPackRequest extends PackageData<Header> {

    private Integer originalFlowId;

    private Integer total;

    private byte[] idList;

    public RepairPackRequest() {
    }

    @Property(index = 0, type = DataType.WORD, desc = "原始消息流水号")
    public Integer getOriginalFlowId() {
        return originalFlowId;
    }

    public void setOriginalFlowId(Integer originalFlowId) {
        this.originalFlowId = originalFlowId;
    }

    @Property(index = 4, type = DataType.BYTE, desc = "原始消息流水号")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 5, type = DataType.BYTES, desc = "重传包ID列表")
    public byte[] getIdList() {
        return idList;
    }

    public void setIdList(byte[] idList) {
        this.idList = idList;
    }
}