package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceDO;

import java.util.List;

@Mapper
@Repository
public interface DeviceMapper {

    List<DeviceDO> find(DeviceDO query);

    DeviceDO getByMobileNo(String mobileNo);

    DeviceDO get(String deviceId);

    int insert(DeviceDO record);

    int update(DeviceDO record);

    int delete(String deviceId);
}