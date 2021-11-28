package org.yzh.protocol;

import io.github.yezhihao.netmc.session.Session;
import org.yzh.protocol.basics.JTMessageFilter;
import org.yzh.protocol.t808.T0200;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public enum SessionKey {

    DeviceInfo,
    Snapshot,
    AreaFilter;

    public static org.yzh.protocol.DeviceInfo getDeviceInfo(Session session) {
        return (org.yzh.protocol.DeviceInfo) session.getAttribute(DeviceInfo);
    }

    public static T0200 getSnapshot(Session session) {
        return (T0200) session.getAttribute(Snapshot);
    }

    public static JTMessageFilter getAreaFilter(Session session) {
        return (JTMessageFilter) session.getAttribute(AreaFilter);
    }
}