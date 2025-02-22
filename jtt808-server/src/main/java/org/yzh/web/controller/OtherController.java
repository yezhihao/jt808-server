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
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yzh.commons.model.APICodes;
import org.yzh.commons.model.R;
import org.yzh.commons.spring.SSEService;
import org.yzh.commons.util.LogUtils;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.enums.SessionKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OtherController {

    private final SessionManager sessionManager;
    private final JTMessageDecoder decoder;
    private final SSEService sseService;

    @Hidden
    @Operation(hidden = true)
    @GetMapping
    public void doc(HttpServletResponse response) throws IOException {
        response.sendRedirect("doc.html");
    }

    @Operation(summary = "终端实时信息查询")
    @GetMapping("device/all")
    public R<Collection<Session>> all() {
        Collection<Session> all = sessionManager.values();
        return R.success(all);
    }

    @Operation(summary = "获得当前所有在线设备信息")
    @GetMapping("device/option")
    public R<Collection<DeviceDO>> getClientId() {
        AdapterCollection<Session, DeviceDO> result = new AdapterCollection<>(sessionManager.values(), session -> {
            DeviceDO device = session.getAttribute(SessionKey.Device);
            if (device != null)
                return device;
            return new DeviceDO().setMobileNo(session.getClientId());
        });
        return R.success(result);
    }

    @Operation(summary = "SSE事件订阅")
    @PostMapping(value = "sse/event")
    public R sseEvent(@RequestParam String userId, String add, String del) {
        sseService.delEvent(userId, del);
        if (!sseService.addEvent(userId, add))
            return R.error(APICodes.InvalidParameter, "未建立连接");
        return R.SUCCESS;
    }

    @Operation(summary = "SSE连接建立")
    @GetMapping(value = "sse/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> sseConnect(HttpServletResponse response) {
        response.addHeader("X-Accel-Buffering", "no");
        return sseService.connect();
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