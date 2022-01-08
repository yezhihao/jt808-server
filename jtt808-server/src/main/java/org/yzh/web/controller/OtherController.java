package org.yzh.web.controller;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import io.github.yezhihao.protostar.SchemaManager;
import io.github.yezhihao.protostar.util.Explain;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.yzh.commons.model.APIResult;
import org.yzh.commons.util.LogUtils;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.MultiPacketDecoder;
import org.yzh.web.config.WebLogAdapter;
import org.yzh.web.model.vo.DeviceInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class OtherController {

    private final SessionManager sessionManager;

    private final JTMessageDecoder decoder;

    public OtherController(SessionManager sessionManager, SchemaManager schemaManager) {
        this.sessionManager = sessionManager;
        this.decoder = new MultiPacketDecoder(schemaManager);
    }

    @Hidden
    @Operation(hidden = true)
    @GetMapping
    public void doc(HttpServletResponse response) throws IOException {
        response.sendRedirect("doc.html");
    }

    @Operation(summary = "终端实时信息查询")
    @GetMapping("terminal/all")
    public APIResult<Collection<Session>> all() {
        Collection<Session> all = sessionManager.all();
        return new APIResult<>(all);
    }

    @Operation(summary = "获得当前所有在线设备信息")
    @GetMapping("terminal/option")
    public APIResult<List<String>> getClientId() {
        Collection<Session> all = sessionManager.all();
        List<String> result = all.stream().map(Session::getId).collect(Collectors.toList());
        return APIResult.ok(result);
    }

    @Operation(summary = "websocket订阅")
    @PostMapping("terminal/ws")
    public APIResult<DeviceInfo> ws(@RequestParam String clientId, @RequestParam int sub) {
        if (sub > 0) {
            Session session = sessionManager.get(clientId);
            if (session != null) {
                WebLogAdapter.addClient(session.getClientId());
                return new APIResult(Collections.singletonMap("clientId", session.getClientId()));
            }
        } else {
            WebLogAdapter.removeClient(clientId);
        }
        return APIResult.SUCCESS;
    }

    @Operation(summary = "808协议分析工具")
    @RequestMapping(value = "message/explain", method = {RequestMethod.POST, RequestMethod.GET})
    public String decode(@Parameter(description = "16进制报文") @RequestParam String hex) {
        Explain explain = new Explain();
        hex = hex.replace(" ", "");
        String[] lines = hex.split("\n");
        for (String line : lines) {
            String[] msgs = line.split("7e7e");
            for (String msg : msgs) {
                ByteBuf byteBuf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(msg));
                decoder.decode(byteBuf, explain);
            }
        }
        return explain.toString();
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

    @Operation(summary = "修改日志级别")
    @GetMapping("logger")
    public String logger(@RequestParam LogUtils.Lv level) {
        LogUtils.setLevel(level.value);
        return "success";
    }

}