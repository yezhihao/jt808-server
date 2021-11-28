package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceStatusDO;
import org.yzh.protocol.DeviceInfo;

import java.util.Date;
import java.util.List;

@Repository
public interface DeviceStatusMapper {

    List<DeviceStatusDO> find(DeviceStatusDO query);

    int update(DeviceStatusDO record);

    int insert(DeviceStatusDO record);

    int insertOnlineRecord(@Param("d") DeviceInfo record, @Param("onlineTime") Date onlineTime, @Param("onlineDuration") int onlineDuration);
}