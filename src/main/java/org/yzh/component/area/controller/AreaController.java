package org.yzh.component.area.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "查询区域")
    @GetMapping
    public Pagination<AreaDO> find(AreaQuery query, PageInfo pageInfo) {
        Pagination<AreaDO> result = Page.start(() -> areaMapper.find(query), pageInfo);
        return result;
    }

    @Operation(summary = "新增|更新区域")
    @PostMapping
    public APIResult<Integer> save(@Validated AreaDO record) {
        if (record.getId() != null) {
            int row = areaMapper.update(record);
            return APIResult.ok(row);
        } else {
            areaMapper.insert(record);
            return APIResult.ok(record.getId());
        }
    }

    @Operation(summary = "启用|禁用区域")
    @PutMapping("enable")
    public APIResult<Integer> enable(@Parameter(description = "区域ID") @RequestParam Integer id,
                                     @Parameter(description = "0.禁用 1.启用") @RequestParam int enable) {
        int row = areaMapper.delete(id, enable == 0, "system");
        return APIResult.ok(row);
    }

    @Operation(summary = "绑定|解绑区域")
    @PutMapping("vehicle")
    public APIResult<Integer> addVehicle(@Parameter(description = "车辆ID") @RequestParam Integer vehicleId,
                                         @Parameter(description = "区域ID") @RequestParam Integer areaId,
                                         @Parameter(description = "0.解绑 1.绑定") @RequestParam int bind) {
        int row;
        if (bind == 0)
            row = areaMapper.removeVehicle(vehicleId, areaId);
        else
            row = areaMapper.addVehicle(vehicleId, areaId, "system");
        return APIResult.ok(row);
    }
}