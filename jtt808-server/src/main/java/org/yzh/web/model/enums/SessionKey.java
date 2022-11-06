package org.yzh.web.model.enums;

import io.github.yezhihao.netmc.session.Session;
import org.yzh.web.model.entity.DeviceDO;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public enum SessionKey {

    Device;

    public static DeviceDO getDevice(Session session) {
        return (DeviceDO) session.getAttribute(Device);
    }
}