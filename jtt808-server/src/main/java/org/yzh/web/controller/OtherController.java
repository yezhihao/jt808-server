package org.yzh.web.controller;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import io.github.yezhihao.netmc.util.AdapterCollection;
import io.github.yezhihao.protostar.util.Explain;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yzh.commons.model.APIResult;
import org.yzh.commons.util.LogUtils;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.web.config.WebLogAdapter;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.enums.SessionKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;

@RestController
@RequestMapping
public class OtherController {

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private JTMessageDecoder decoder;

    @Hidden
    @Operation(hidden = true)
    @GetMapping
    public void doc(HttpServletResponse response) throws IOException {
        response.sendRedirect("doc.html");
    }

    @Operation(summary = "终端实时信息查询")
    @GetMapping("device/all")
    public APIResult<Collection<Session>> all() {
        Collection<Session> all = sessionManager.all();
        return APIResult.ok(all);
    }

    @Operation(summary = "获得当前所有在线设备信息")
    @GetMapping("device/option")
    public APIResult<Collection<DeviceDO>> getClientId() {
        AdapterCollection<Session, DeviceDO> result = new AdapterCollection<>(sessionManager.all(), session -> {
            DeviceDO device = SessionKey.getDevice(session);
            if (device != null)
                return device;
            return new DeviceDO().mobileNo(session.getClientId());
        });
        return APIResult.ok(result);
    }

    @Operation(summary = "设备订阅")
    @PostMapping(value = "device/sse", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sseSub(@RequestParam String userId, @RequestParam String clientId, @RequestParam boolean sub) {
        if (sub) {
            WebLogAdapter.addClient(userId, clientId);
        } else {
            WebLogAdapter.removeClient(userId, clientId);
        }
        return "1";
    }

    @Operation(summary = "设备监控")
    @GetMapping(value = "device/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> sseConnect(@RequestParam String userId, @RequestParam(required = false, defaultValue = "0") String clientId) {
        return WebLogAdapter.addClient(userId, clientId);
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
    @PostMapping("device/raw")
    public Mono<String> postRaw(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                @Parameter(description = "16进制报文") @RequestParam String message) {
        Session session = sessionManager.get(clientId);
        if (session != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(message));

            return session.notify(byteBuf).map(unused -> "success")
                    .timeout(Duration.ofSeconds(10), Mono.just("timeout"))
                    .onErrorResume(throwable -> Mono.just("fail"));
        }
        return Mono.just("offline");
    }

    @Operation(summary = "修改日志级别")
    @GetMapping("logger")
    public String logger(@RequestParam LogUtils.Lv level) {
        LogUtils.setLevel(level.value);
        return "success";
    }

}