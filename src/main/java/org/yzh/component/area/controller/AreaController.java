package org.yzh.component.area.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yzh.commons.model.APIResult;
import org.yzh.commons.mybatis.Page;
import org.yzh.commons.mybatis.PageInfo;
import org.yzh.commons.mybatis.Pagination;
import org.yzh.component.area.mapper.AreaMapper;
import org.yzh.component.area.model.entity.AreaDO;
import org.yzh.component.area.model.vo.AreaQuery;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@RestController
@RequestMapping("area")
public class AreaController {

    @Autowired
    private AreaMapper areaMapper;

    @Operation(summary = "区域查询")
    @GetMapping
    public Pagination<AreaDO> find(AreaQuery query, PageInfo pageInfo) {
        Pagination<AreaDO> result = Page.start(() -> areaMapper.find(query), pageInfo);
        return result;
    }

    @Operation(summary = "区域新增")
    @PostMapping
    public APIResult add(@Validated AreaDO record) {
        if (record.getId() != null) {
            areaMapper.update(record);
        } else {
            areaMapper.insert(record);
        }
        return new APIResult(record.getId());
    }

    @DeleteMapping
    public APIResult delete(@RequestParam Integer id) {
        int row = areaMapper.delete(id);
        return new APIResult(row);
    }

    @PostMapping("vehicle")
    public APIResult addVehicle(@RequestParam Integer areaId, @RequestParam Integer vehicleId) {
        int row = areaMapper.insertVehicle(areaId, vehicleId);
        return new APIResult(row);
    }

    @DeleteMapping("vehicle")
    public APIResult deleteVehicle(@RequestParam Integer areaId, @RequestParam Integer vehicleId) {
        int row = areaMapper.deleteVehicle(areaId, vehicleId);
        return new APIResult(row);
    }
}