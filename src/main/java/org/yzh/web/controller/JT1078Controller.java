package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT1078;
import org.yzh.protocol.t1078.*;
import org.yzh.protocol.t808.T0001;

@Api(description = "terminal api")
@RestController
@RequestMapping("media")
public class JT1078Controller {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;


    @ApiOperation(value = "实时音视频传输请求")
    @GetMapping("realtime/play")
    public T0001 play(@ApiParam("终端手机号") @RequestParam String clientId, T9101 request) {
        request.setHeader(new Header(clientId, JT1078.实时音视频传输请求));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "音视频实时传输控制")
    @GetMapping("realtime/control")
    public T0001 control(@ApiParam("终端手机号") @RequestParam String clientId, T9102 request) {
        request.setHeader(new Header(clientId, JT1078.音视频实时传输控制));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "查询资源列表")
    @GetMapping("file/search")
    public T1205 search(@ApiParam("终端手机号") @RequestParam String clientId, T9205 request) {
        request.setHeader(new Header(clientId, JT1078.查询资源列表));
        T1205 response = messageManager.request(request, T1205.class);
        return response;
    }

    @ApiOperation(value = "文件上传指令")
    @GetMapping("file/upload")
    public T0001 upload(@ApiParam("终端手机号") @RequestParam String clientId, T9206 request) {
        request.setHeader(new Header(clientId, JT1078.文件上传指令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "平台下发远程录像回放请求")
    @GetMapping("history/search")
    public T1205 search(@ApiParam("终端手机号") @RequestParam String clientId, T9201 request) {
        request.setHeader(new Header(clientId, JT1078.平台下发远程录像回放请求));
        T1205 response = messageManager.request(request, T1205.class);
        return response;
    }

    @ApiOperation(value = "平台下发远程录像回放请求")
    @GetMapping("history/control")
    public T0001 search(@ApiParam("终端手机号") @RequestParam String clientId, T9202 request) {
        request.setHeader(new Header(clientId, JT1078.平台下发远程录像回放控制));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }


    @ApiOperation(value = "云台旋转")
    @GetMapping(" pan_tilt/revolve")
    public T0001 panTiltRevolve(@ApiParam("终端手机号") @RequestParam String clientId, T9301 request) {
        request.setHeader(new Header(clientId, JT1078.云台旋转));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "云台控制")
    @GetMapping(" pan_tilt/control")
    public T0001 panTiltControl(@ApiParam("终端手机号") @RequestParam String clientId, @RequestParam int messageId, T9301 request) {
        request.setHeader(new Header(clientId, messageId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }
}