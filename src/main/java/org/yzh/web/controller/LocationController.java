package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.web.component.mybatis.Page;
import org.yzh.web.component.mybatis.PageInfo;
import org.yzh.web.component.mybatis.Pagination;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "位置信息")
@Controller
@RequestMapping
public class LocationController {

    @Autowired
    private LocationService locationService;

    @ApiIgnore
    @GetMapping
    public String doc() {
        return "redirect:doc.html";
    }

    @ApiOperation(value = "位置信息查询")
    @GetMapping("location")
    @ResponseBody
    public Pagination<Location> find(LocationQuery query, PageInfo pageInfo) {
        Pagination<Location> result = Page.start(() -> locationService.find(query), pageInfo);
        return result;
    }
}