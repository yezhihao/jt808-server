package org.yzh.web.model.protocol;

import io.github.yezhihao.protostar.annotation.MergeSuperclass;
import org.yzh.protocol.commons.DateUtils;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;

import java.time.LocalDateTime;

@MergeSuperclass
public class T0200 extends org.yzh.protocol.t808.T0200 {

    private boolean updated;
    private String deviceId;
    private String plateNo;
    private int vehicleId;
    private double lng;
    private double lat;
    private float speedKph;
    private LocalDateTime deviceTime;

    @Override
    public boolean transform() {
        lng = getLongitude() / 1000000d;
        lat = getLatitude() / 1000000d;
        speedKph = getSpeed() / 10f;
        if (getDateTime() != null)
            deviceTime = DateUtils.parse(getDateTime());

        DeviceInfo device = SessionKey.getDeviceInfo(session);
        if (device != null) {
            deviceId = device.getDeviceId();
            plateNo = device.getPlateNo();
            vehicleId = device.getVehicleId();
        } else {
            deviceId = clientId;
            plateNo = "";
        }
        return deviceTime != null;
    }


    public boolean updated() {
        return updated ? true : !(updated = true);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public float getSpeedKph() {
        return speedKph;
    }

    public LocalDateTime getDeviceTime() {
        return deviceTime;
    }
}