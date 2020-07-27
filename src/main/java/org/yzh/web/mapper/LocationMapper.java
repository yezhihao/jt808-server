package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.Location;

import java.util.List;

@Mapper
@Repository
public interface LocationMapper {

    Location get(Integer id);

    int batchInsert(@Param("list") List<Location> list);

}