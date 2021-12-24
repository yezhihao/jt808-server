package org.yzh.component.area;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.component.area.model.Area;
import org.yzh.component.area.model.VehicleArea;
import org.yzh.protocol.basics.JTMessageFilter;
import org.yzh.protocol.commons.DateUtils;
import org.yzh.web.model.protocol.T0200;

import java.time.LocalDateTime;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class AreaFilter implements JTMessageFilter<T0200> {

    private static final Logger log = LoggerFactory.getLogger(AreaFilter.class);

    private VehicleArea[] areas;

    public VehicleArea[] getAreas() {
        return areas;
    }

    public AreaFilter updateAreas(VehicleArea[] areas) {
        this.areas = areas;
        return this;
    }

    @Override
    public boolean doFilter(T0200 data) {
        final VehicleArea[] areas = this.areas;
        if (areas == null)
            return true;

        LocalDateTime deviceTime = data.getDeviceTime();
        float speed = data.getSpeedKph();
        double lng = data.getLng();
        double lat = data.getLat();
        int vehicleId = data.getVehicleId();

        for (int i = 0; i < areas.length; i++) {

            final VehicleArea vehicleArea = areas[i];
            final Area area = vehicleArea.getArea();

            if (!area.contains(deviceTime)) {
                vehicleArea.setEntryTime(0);
                continue;
            }

            if (area.contains(lng, lat)) {
                if (area.limitInOut == 1) {
                    log.info("车辆[{}]状态[{},{},{}]{}:{}", vehicleId, lng, lat, speed, "进区域报警", area.name);
                }
                if (data.getSpeed() >= area.limitSpeed) {
                    log.info("车辆[{}]状态[{},{},{}]{}:{}", vehicleId, lng, lat, speed, "区域内超速", area.name + ",当前速度:" + speed + "km/h," + "区域限速:" + area.limitSpeed / 10d + "km/h");
                }

                long currentTime = deviceTime.toEpochSecond(DateUtils.GMT8);
                long beforeTime = vehicleArea.getEntryTime();

                if (beforeTime == 0) {
                    vehicleArea.setEntryTime(currentTime);
                } else {
                    int time = (int) (currentTime - beforeTime);
                    if (time > area.limitTime) {
                        log.info("车辆[{}]状态[{},{},{}]{}:{}", vehicleId, lng, lat, speed, "区域内停车超时", area.name + ",停车:" + time + "秒,限时:" + area.limitTime + "秒");
                    }
                }
            } else {
                if (vehicleArea.getEntryTime() != 0) {
                    vehicleArea.setEntryTime(0);
                    if (area.limitInOut == 2) {
                        log.info("车辆[{}]状态[{},{},{}]{}:{}", vehicleId, lng, lat, speed, "出区域报警", area.name);
                    }
                }
            }
        }
        return true;
    }
}