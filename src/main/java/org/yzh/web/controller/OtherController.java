package org.yzh.web.controller;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yzh.protocol.commons.LoggingFilter;
import org.yzh.web.commons.StrUtils;
import org.yzh.web.component.mybatis.Page;
import org.yzh.web.component.mybatis.PageInfo;
import org.yzh.web.component.mybatis.Pagination;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping
public class OtherController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SessionManager sessionManager;

    @Hidden
    @Operation(hidden = true)
    @GetMapping
    public void doc(HttpServletResponse response) throws IOException {
        response.sendRedirect("doc.html");
    }

    @Operation(summary = "获得当前所有在线设备信息")
    @GetMapping("terminal/all")
    public Collection<Session> all() {
        return sessionManager.all();
    }

    @Operation(summary = "原始消息发送")
    @PostMapping("terminal/raw")
    public String postRaw(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "16进制报文") @RequestParam String message) {
        Session session = sessionManager.get(clientId);
        if (session != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(message));
            session.notify(byteBuf);
            return "success";
        }
        return "fail";
    }

    @Operation(summary = "位置信息查询")
    @GetMapping("location")
    public Pagination<Location> find(LocationQuery query, PageInfo pageInfo) {
        Pagination<Location> result = Page.start(() -> locationService.find(query), pageInfo);
        return result;
    }

    @Operation(summary = "日志拦截器")
    @GetMapping("logger/filter")
    public Map loggerFilter(@Parameter(description = "终端手机号") String clientId,
                            @Parameter(description = "忽略的消息ID(十六进制字符串,多个以空格分隔)", example = "0002 0200") String ignoreMsgIds) {
        LoggingFilter.setFilter(clientId, ignoreMsgIds);

        return StrUtils.newMap(
                "clientId", LoggingFilter.getClientId(),
                "ignoreMsgIds", LoggingFilter.getIgnoreMsgIds()
        );
    }

    @Operation(summary = "清空日志拦截器")
    @DeleteMapping("logger/filter")
    public String deleteLoggerFilter() {
        LoggingFilter.clear();
        return "success";
    }

    @Operation(summary = "修改日志级别")
    @GetMapping("logger")
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