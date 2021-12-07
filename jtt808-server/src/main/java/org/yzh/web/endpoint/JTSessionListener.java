package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionListener;
import org.yzh.component.area.service.AreaService;
import org.yzh.web.mapper.DeviceStatusMapper;
import org.yzh.web.model.entity.DeviceStatusDO;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;

import java.util.Date;

public class JTSessionListener implements SessionListener {

    private final DeviceStatusMapper deviceStatusMapper;

    private final AreaService areaService;

    public JTSessionListener(DeviceStatusMapper deviceStatusMapper, AreaService areaService) {
        this.deviceStatusMapper = deviceStatusMapper;
        this.areaService = areaService;
    }

    @Override
    public void sessionCreated(Session session) {
    }

    @Override
    public void sessionRegistered(Session session) {
        DeviceInfo device = SessionKey.getDeviceInfo(session);
        if (device != null) {

            DeviceStatusDO status = new DeviceStatusDO();
            status.setDeviceId(device.getDeviceId());
            status.setOnline(true);
            status.setAgencyId(device.getAgencyId());
            status.setPlateNo(device.getPlateNo());
            status.setMobileNo(device.getClientId());
            status.setVehicleId(device.getVehicleId());

            if (deviceStatusMapper.update(status) < 1)
                deviceStatusMapper.insert(status);
            if (areaService != null)
                areaService.register(session);
        }
    }

    @Override
    public void sessionDestroyed(Session session) {
        DeviceInfo device = SessionKey.getDeviceInfo(session);
        if (device != null) {
            deviceStatusMapper.update(new DeviceStatusDO().deviceId(device.getDeviceId()).online(false));
            long onlineTime = session.getCreationTime();
            int onlineDuration = (int) (System.currentTimeMillis() - onlineTime) / 1000;
            deviceStatusMapper.insertOnlineRecord(device, new Date(onlineTime), onlineDuration);
            if (areaService != null)
                areaService.cancellation(session);
        }
    }
}