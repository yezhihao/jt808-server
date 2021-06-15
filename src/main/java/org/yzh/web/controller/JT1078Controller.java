package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t1078.*;
import org.yzh.protocol.t808.T0001;
import org.yzh.web.endpoint.MessageManager;

@RestController
@RequestMapping("media")
public class JT1078Controller {

    @Autowired
    private MessageManager messageManager;

    @Operation(summary = "9101 实时音视频传输请求")
    @GetMapping("realtime/play")
    public T0001 play(@Parameter(description = "终端手机号") @RequestParam String clientId, T9101 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "9102 音视频实时传输控制")
    @GetMapping("realtime/control")
    public T0001 control(@Parameter(description = "终端手机号") @RequestParam String clientId, T9102 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "9205 查询资源列表")
    @GetMapping("file/search")
    public T1205 search(@Parameter(description = "终端手机号") @RequestParam String clientId, T9205 request) {
        request.setHeader(new Header(clientId));
        request.setWarnBit(new int[2]);
        T1205 response = messageManager.request(request, T1205.class);
        return response;
    }

    @Operation(summary = "9206 文件上传指令")
    @GetMapping("file/upload")
    public T0001 upload(@Parameter(description = "终端手机号") @RequestParam String clientId, T9206 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "9201 平台下发远程录像回放请求")
    @GetMapping("history/search")
    public T1205 search(@Parameter(description = "终端手机号") @RequestParam String clientId, T9201 request) {
        request.setHeader(new Header(clientId));
        T1205 response = messageManager.request(request, T1205.class);
        return response;
    }

    @Operation(summary = "9202 平台下发远程录像回放请求")
    @GetMapping("history/control")
    public T0001 search(@Parameter(description = "终端手机号") @RequestParam String clientId, T9202 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }


    @Operation(summary = "9301 云台旋转")
    @GetMapping("pan_tilt/revolve")
    public T0001 panTiltRevolve(@Parameter(description = "终端手机号") @RequestParam String clientId, T9301 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "9302 9303 9304 9305 9306 云台控制")
    @GetMapping("pan_tilt/control")
    public T0001 panTiltControl(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestParam int messageId, T9301 request) {
        request.setHeader(new Header(clientId, messageId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }
}