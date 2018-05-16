package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 信息点播/取消
 */
public class MessageSubOperate extends PackageData<Header> {

    private Integer type;

    private Integer action;

    @Property(index = 0, type = DataType.BYTE, desc = "消息类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "点播/取消标志 0：取消；1：点播")
    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}