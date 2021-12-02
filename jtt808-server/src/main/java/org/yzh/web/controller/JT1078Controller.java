package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.commons.model.APIResult;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;
import org.yzh.protocol.t1078.*;
import org.yzh.protocol.t808.T0001;
import org.yzh.web.endpoint.MessageManager;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("media")
public class JT1078Controller {

    @Autowired
    private MessageManager messageManager;

    @Operation(summary = "9003 查询终端音视频属性")
    @GetMapping("attributes")
    public Mono<APIResult<T1003>> getAttributes(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        Mono<APIResult<T1003>> response = messageManager.requestR(clientId, new JTMessage(JT1078.查询终端音视频属性), T1003.class);
        return response;
    }

    @Operation(summary = "9101 实时音视频传输请求")
    @GetMapping("realtime/play")
    public Mono<APIResult<T0001>> realtimePlay(@Parameter(description = "终端手机号") @RequestParam String clientId, T9101 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "9102 音视频实时传输控制")
    @GetMapping("realtime/control")
    public Mono<APIResult<T0001>> realtimeControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T9102 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "9201 平台下发远程录像回放请求")
    @GetMapping("history/play")
    public Mono<APIResult<T1205>> historyPlay(@Parameter(description = "终端手机号") @RequestParam String clientId, T9201 request) {
        Mono<APIResult<T1205>> response = messageManager.requestR(clientId, request, T1205.class);
        return response;
    }

    @Operation(summary = "9202 平台下发远程录像回放控制")
    @GetMapping("history/control")
    public Mono<APIResult<T0001>> historyControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T9202 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "9205 查询资源列表")
    @GetMapping("file/search")
    public Mono<APIResult<T1205>> fileSearch(@Parameter(description = "终端手机号") @RequestParam String clientId, T9205 request) {
        Mono<APIResult<T1205>> response = messageManager.requestR(clientId, request, T1205.class);
        return response;
    }

    @Operation(summary = "9206 文件上传指令")
    @GetMapping("file/upload")
    public Mono<APIResult<T0001>> fileUpload(@Parameter(description = "终端手机号") @RequestParam String clientId, T9206 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "9207 文件上传控制")
    @GetMapping("file/upload/control")
    public Mono<APIResult<T0001>> fileUploadControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T9207 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "9301 云台旋转")
    @GetMapping("pan_tilt/revolve")
    public Mono<APIResult<T0001>> panTiltRevolve(@Parameter(description = "终端手机号") @RequestParam String clientId, T9301 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "9302 9303 9304 9305 9306 云台控制")
    @GetMapping("pan_tilt/control")
    public Mono<APIResult<T0001>> panTiltControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T9302 request,
                                                 @Parameter(description = "控制类型：9302.云台调整焦距控制 9303.云台调整光圈控制 9304.云台雨刷控制 9305.红外补光控制 9306.云台变倍控制") @RequestParam String type) {
        request.setMessageId(Integer.parseInt(type, 16));
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }
}