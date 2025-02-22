package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.commons.model.R;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.t1078.*;
import org.yzh.protocol.t808.T0001;
import org.yzh.web.config.JTProperties;
import org.yzh.web.endpoint.MessageManager;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("device")
@RequiredArgsConstructor
public class JT1078Controller {

    private final MessageManager messageManager;

    private final JTProperties jtProperties;

    @Operation(summary = "9003 查询终端音视频属性")
    @PostMapping("9003")
    public Mono<R<T1003>> T9003(@RequestBody JTMessage request) {
        return messageManager.requestR(request.setMessageId(JT1078.查询终端音视频属性), T1003.class);
    }

    @Operation(summary = "9101 实时音视频传输请求")
    @PostMapping("9101")
    public Mono<R<T0001>> T9101(@RequestBody T9101 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9102 音视频实时传输控制")
    @PostMapping("9102")
    public Mono<R<T0001>> T9102(@RequestBody T9102 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9201 平台下发远程录像回放请求")
    @PostMapping("9201")
    public Mono<R<T1205>> T9201(@RequestBody T9201 request) {
        return messageManager.requestR(request, T1205.class);
    }

    @Operation(summary = "9202 平台下发远程录像回放控制")
    @PostMapping("9202")
    public Mono<R<T0001>> T9202(@RequestBody T9202 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9205 查询资源列表")
    @PostMapping("9205")
    public Mono<R<T1205>> T9205(@RequestBody T9205 request) {
        return messageManager.requestR(request, T1205.class);
    }

    @Operation(summary = "9206 文件上传指令")
    @PostMapping("9206")
    public Mono<R<T0001>> T9206(@RequestBody T9206 request) {
        jtProperties.configure(request);
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9207 文件上传控制")
    @PostMapping("9207")
    public Mono<R<T0001>> T9207(@RequestBody T9207 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9208 报警附件上传指令(苏标)")
    @PostMapping("9208")
    public Mono<R<T0001>> T9208(@RequestBody T9208 request) {
        jtProperties.configure(request);
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9301 云台旋转")
    @PostMapping("9301")
    public Mono<R<T0001>> T9301(@RequestBody T9301 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "9302 云台调整焦距控制")
    @PostMapping("9302")
    public Mono<R<T0001>> T9302(@RequestBody T9302 request) {
        return messageManager.requestR(request.setMessageId(JT1078.云台调整焦距控制), T0001.class);
    }

    @Operation(summary = "9303 云台调整光圈控制")
    @PostMapping("9303")
    public Mono<R<T0001>> T9303(@RequestBody T9302 request) {
        return messageManager.requestR(request.setMessageId(JT1078.云台调整光圈控制), T0001.class);
    }

    @Operation(summary = "9304 云台雨刷控制")
    @PostMapping("9304")
    public Mono<R<T0001>> T9304(@RequestBody T9302 request) {
        return messageManager.requestR(request.setMessageId(JT1078.云台雨刷控制), T0001.class);
    }

    @Operation(summary = "9305 红外补光控制")
    @PostMapping("9305")
    public Mono<R<T0001>> T9305(@RequestBody T9302 request) {
        return messageManager.requestR(request.setMessageId(JT1078.红外补光控制), T0001.class);
    }

    @Operation(summary = "9306 云台变倍控制")
    @PostMapping("9306")
    public Mono<R<T0001>> T9306(@RequestBody T9302 request) {
        return messageManager.requestR(request.setMessageId(JT1078.云台变倍控制), T0001.class);
    }
}