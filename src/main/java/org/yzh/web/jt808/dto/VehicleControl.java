package org.yzh.web.jt808.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 车辆控制
 */
public class VehicleControl extends PackageData<Header> {

    private Integer sign;

    private int[] signs;

    @JsonIgnore
    @Property(index = 0, type = DataType.BYTE, desc = "控制标志")
    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public int[] getSigns() {
        return signs;
    }

    public void setSigns(int[] signs) {
        int sign = 0;
        for (int b : signs) {
            sign |= 1 << b;
        }
        this.sign = sign;
        this.signs = signs;
    }

}