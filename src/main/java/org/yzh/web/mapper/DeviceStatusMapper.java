package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceStatusDO;

import java.util.List;

@Repository
public interface DeviceStatusMapper {

    List<DeviceStatusDO> find(DeviceStatusDO query);

    int update(DeviceStatusDO record);

    int insert(DeviceStatusDO record);
}