package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceStatusDO;

import java.util.List;

@Mapper
@Repository
public interface DeviceStatusMapper {

    List<DeviceStatusDO> find(DeviceStatusDO query);

    int update(DeviceStatusDO record);

    int insert(DeviceStatusDO record);
}