package org.yzh.component.area;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.commons.util.DateUtils;
import org.yzh.component.area.model.Area;
import org.yzh.component.area.model.VehicleArea;
import org.yzh.protocol.t808.T0200;

import java.time.LocalDateTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class AreaFilter {

    private static final Logger log = LoggerFactory.getLogger(AreaFilter.class.getSimpleName());

    private VehicleArea[] areas;

    public AreaFilter(VehicleArea[] areas) {
        this.areas = areas;
    }

    public void updateAreas(VehicleArea[] areas) {
        this.areas = areas;
    }

    public void doFilter(T0200 data) {
        LocalDateTime deviceTime = data.getDeviceTime();
        float speed = data.getSpeedKph();
        double lng = data.getLng();
        double lat = data.getLat();
        int vehicleId = data.getVehicleId();

        final VehicleArea[] areas = this.areas;
        for (int i = 0; i < areas.length; i++) {

            final VehicleArea vehicleArea = areas[i];
            final Area area = vehicleArea.getArea();

            if (!area.contains(deviceTime)) {
                vehicleArea.setEntryTime(0);
                continue;
            }

            int areaId = area.getId();
            int limitInOut = area.getLimitInOut();
            int limitSpeed = area.getLimitSpeed();
            int limitTime = area.getLimitTime();
            String areaName = area.getName();

            if (area.contains(lng, lat)) {
                if (limitInOut == 1) {
                    log.warn("{},{},{},{},{},{},{},{}", vehicleId, lng, lat, speed, deviceTime, 4, areaName, "进区域报警", "进入" + areaName + "报警");
                }
                if (speed >= limitSpeed) {
                    log.warn("{},{},{},{},{},{},{},{}", vehicleId, lng, lat, speed, deviceTime, 1, areaName, "区域内超速", "当前速度：" + speed + "km/h;" + areaName + "区域限速：" + limitSpeed + "km/h");
                }

                long currentTime = deviceTime.toEpochSecond(DateUtils.GMT8);
                long beforeTime = vehicleArea.getEntryTime();

                if (beforeTime == 0) {
                    vehicleArea.setEntryTime(currentTime);
                } else {
                    int time = (int) ((currentTime - beforeTime) / 60L);
                    if (time > limitTime) {
                        log.warn("{},{},{},{},{},{},{},{}", vehicleId, lng, lat, speed, deviceTime, 7, areaName, "区域内停车超时", areaName + "任务区域停车超时报警;停车：" + time + "分钟，限时：" + limitTime + "分钟");
                    }
                }
            } else {
                if (vehicleArea.getEntryTime() != 0) {
                    vehicleArea.setEntryTime(0);
                    if (limitInOut == 2) {
                        log.warn("{},{},{},{},{},{},{},{}", vehicleId, lng, lat, speed, deviceTime, 4, areaName, "出区域报警", "离开" + areaName + "报警");
                    }
                }
            }
        }
    }
}
