package org.yzh.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionListener;
import org.yzh.web.mapper.DeviceMapper;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.vo.DeviceInfo;

import java.time.LocalDateTime;

public class JTSessionListener implements SessionListener {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public void sessionCreated(Session se) {
    }

    @Override
    public void sessionDestroyed(Session session) {
        DeviceInfo device = (DeviceInfo) session.getSubject();
        if (device != null)
            deviceMapper.update(new DeviceDO(device.getDeviceId(), false, LocalDateTime.now()));
    }
}