package org.yzh.web.mapper;

import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.VehicleDO;

import java.util.List;

@Repository
public interface VehicleMapper {

    List<VehicleDO> find(VehicleDO query);

    VehicleDO getByPlateNo(String plateNo);

    VehicleDO get(int id);

    int update(VehicleDO record);

    int insert(VehicleDO record);

    int delete(int id);
}