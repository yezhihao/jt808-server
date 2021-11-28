package org.yzh.web.mapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.VehicleDO;

import java.util.List;

@Repository
public interface VehicleMapper {

    List<VehicleDO> find(VehicleDO query);

    @Cacheable(cacheNames = "VehicleDO.plateNo")
    VehicleDO getByPlateNo(String plateNo);

    VehicleDO get(int id);

    int update(VehicleDO record);

    @CacheEvict(cacheNames = "VehicleDO.plateNo", key = "#record.plateNo")
    int insert(VehicleDO record);

    int delete(int id);
}