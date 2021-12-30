package org.yzh.web.model.enums;

import io.github.yezhihao.netmc.session.Session;
import org.yzh.protocol.basics.JTMessageFilter;
import org.yzh.protocol.t808.T0200;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public enum SessionKey {

    DeviceInfo,
    Snapshot,
    AreaFilter;

    public static org.yzh.web.model.vo.DeviceInfo getDeviceInfo(Session session) {
        return (org.yzh.web.model.vo.DeviceInfo) session.getAttribute(DeviceInfo);
    }

    public static T0200 getSnapshot(Session session) {
        return (T0200) session.getAttribute(Snapshot);
    }

    public static JTMessageFilter getAreaFilter(Session session) {
        return (JTMessageFilter) session.getAttribute(AreaFilter);
    }
}