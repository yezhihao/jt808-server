package org.yzh.web.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceDO;

import java.util.List;

@Repository
public interface DeviceMapper {

    List<DeviceDO> find(DeviceDO query);

    DeviceDO getByMobileNo(String mobileNo);

    @Cacheable(cacheNames = "DeviceDO.deviceId")
    DeviceDO get(String deviceId);

    @CacheEvict(cacheNames = "DeviceDO.deviceId", key = "#record.deviceId")
    int insert(DeviceDO record);

    int update(DeviceDO record);

    int delete(String deviceId);
}