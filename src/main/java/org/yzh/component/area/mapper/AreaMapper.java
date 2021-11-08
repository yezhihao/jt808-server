package org.yzh.component.area.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.component.area.model.entity.AreaDO;
import org.yzh.component.area.model.vo.AreaQuery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Repository
public interface AreaMapper {

    int[] findVehicleId(LocalDateTime updateTime);

    int[] findAreaId(int vehicleId);

    List<AreaDO> find(AreaQuery query);

    int update(AreaDO record);

    int insert(AreaDO record);

    int removeVehicle(@Param("vehicleId") int vehicleId, @Param("areaId") Integer areaId);

    int addVehicle(@Param("vehicleId") int vehicleId, @Param("areaId") int areaId, @Param("createdBy") String createdBy);
}