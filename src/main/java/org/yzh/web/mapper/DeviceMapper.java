package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceDO;

@Repository
public interface DeviceMapper {

    DeviceDO get(String deviceId);

    DeviceDO getByMobileNo(String mobileNo);

    int insert(DeviceDO record);

    int update(DeviceDO record);

    int delete(String deviceId);
}