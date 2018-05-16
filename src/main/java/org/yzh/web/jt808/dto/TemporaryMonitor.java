package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 临时位置跟踪控制
 */
public class TemporaryMonitor extends PackageData<Header> {

    private Integer interval;

    private Integer validityTime;

    @Property(index = 0, type = DataType.WORD, desc = "时间间隔（秒）")
    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Property(index = 2, type = DataType.DWORD, desc = "有效期（秒）")
    public Integer getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Integer validityTime) {
        this.validityTime = validityTime;
    }
}