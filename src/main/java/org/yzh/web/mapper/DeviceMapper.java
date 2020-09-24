package org.yzh.web.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceDO;

@Repository
public interface DeviceMapper {

    DeviceDO get(String deviceId);

    @Cacheable(cacheNames = "DeviceDO", key = "#mobileNo")
    DeviceDO getByMobileNo(String mobileNo);

    @CacheEvict(cacheNames = "DeviceDO", key = "#record.mobileNo")
    int insert(DeviceDO record);

    @CacheEvict(cacheNames = "DeviceDO", key = "#record.mobileNo")
    int update(DeviceDO record);

    int delete(String deviceId);
}