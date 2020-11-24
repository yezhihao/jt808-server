package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationMapper {

    List<Location> find(LocationQuery query);

    Location get(@Param("deviceId") String deviceId, @Param("deviceTime") LocalDateTime deviceTime);
}