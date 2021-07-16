package org.yzh.web.model.enums;

import io.github.yezhihao.netmc.session.Session;
import org.yzh.protocol.t808.T0200;

/**
 * Created by Alan.ye on 2017/5/20.
 */
public enum SessionKey {

    DeviceInfo,
    Snapshot;

    public static org.yzh.web.model.vo.DeviceInfo getDeviceInfo(Session session) {
        return (org.yzh.web.model.vo.DeviceInfo) session.getAttribute(DeviceInfo);
    }

    public static T0200 getSnapshot(Session session) {
        return (T0200) session.getAttribute(Snapshot);
    }
}