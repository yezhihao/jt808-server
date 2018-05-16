package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 终端升级通知
 */
public class TerminalUpgradeNotify extends PackageData<Header> {

    public static final int Terminal = 0;
    public static final int CardReader = 12;
    public static final int Beidou = 52;

    private Integer type;

    private Integer result;

    @Property(index = 0, type = DataType.BYTE, desc = "升级类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "升级结果")
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}