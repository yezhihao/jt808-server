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
import org.yzh.commons.mybatis.Page;
import org.yzh.commons.mybatis.PageInfo;
import org.yzh.commons.mybatis.Pagination;
import org.yzh.web.endpoint.LoggingPusher;
import org.yzh.commons.model.APIResult;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class OtherController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private LoggingPusher loggingPusher;

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

    @Operation(summary = "获得当前所有在线设备信息")
    @GetMapping("terminal/option")
    public APIResult<List<String>> getClientId() {
        Collection<Session> all = sessionManager.all();
        List<String> result = all.stream().map(session -> session.getId()).collect(Collectors.toList());
        return new APIResult(result);
    }

    @Operation(summary = "websocket订阅")
    @PostMapping("terminal/sub")
    public APIResult<DeviceInfo> sub(@RequestParam String clientId) {
        Session session = sessionManager.get(clientId);
        if (session != null) {
            loggingPusher.addClient(session.getClientId());
            return new APIResult(session.getAttribute(SessionKey.DeviceInfo));
        }
        return APIResult.SUCCESS;
    }

    @Operation(summary = "websocket取消订阅")
    @PostMapping("terminal/unsub")
    public APIResult<List<String>> unsub(@RequestParam String clientId) {
        loggingPusher.removeClient(clientId);
        return APIResult.SUCCESS;
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