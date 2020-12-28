package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.web.component.mybatis.Page;
import org.yzh.web.component.mybatis.PageInfo;
import org.yzh.web.component.mybatis.Pagination;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

@Controller
@RequestMapping
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Hidden
    @Operation(hidden = true)
    @GetMapping
    public String doc() {
        return "redirect:doc.html";
    }

    @Operation(summary = "位置信息查询")
    @GetMapping("location")
    @ResponseBody
    public Pagination<Location> find(LocationQuery query, PageInfo pageInfo) {
        Pagination<Location> result = Page.start(() -> locationService.find(query), pageInfo);
        return result;
    }


    @Operation(summary = "修改日志级别")
    @GetMapping("logger")
    @ResponseBody
    public String logger(@RequestParam(value = "value") Level level) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(level.value);
        ctx.updateLoggers();
        return "success";
    }

    public enum Level {
        TRACE(org.apache.logging.log4j.Level.TRACE),
        DEBUG(org.apache.logging.log4j.Level.DEBUG),
        INFO(org.apache.logging.log4j.Level.INFO),
        WARN(org.apache.logging.log4j.Level.WARN),
        ERROR(org.apache.logging.log4j.Level.ERROR);

        Level(org.apache.logging.log4j.Level value) {
            this.value = value;
        }

        private org.apache.logging.log4j.Level value;
    }
}