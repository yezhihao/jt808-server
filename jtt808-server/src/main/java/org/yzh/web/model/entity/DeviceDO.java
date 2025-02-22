package org.yzh.web.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.yzh.protocol.t808.T0200;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class DeviceDO {

    @Schema(description = "设备id")
    private String deviceId;
    @Schema(description = "设备手机号")
    private String mobileNo;
    @Schema(description = "车牌号")
    private String plateNo;
    @Schema(description = "机构id")
    protected int agencyId;
    @Schema(description = "司机id(人脸识别)")
    protected int driverId;
    @Schema(description = "协议版本号")
    private int protocolVersion;
    @Schema(description = "实时状态")
    private T0200 location;

    public void updateLocation(T0200 location) {
        if (this.location == null) {
            this.location = location;
        } else if (this.location.getDeviceTime().isBefore(location.getDeviceTime())) {
            this.location = location;
        }
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DeviceDO other = (DeviceDO) that;
        return Objects.equals(this.deviceId, other.deviceId);
    }

    @Override
    public int hashCode() {
        return ((deviceId == null) ? 0 : deviceId.hashCode());
    }
}