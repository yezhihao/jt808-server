package org.yzh.web.model.enums;

import io.github.yezhihao.netmc.session.Session;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public enum SessionKey {

    DeviceInfo;

    public static org.yzh.web.model.vo.DeviceInfo getDeviceInfo(Session session) {
        return (org.yzh.web.model.vo.DeviceInfo) session.getAttribute(DeviceInfo);
    }
}