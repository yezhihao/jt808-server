package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.commons.model.APIResult;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.*;
import org.yzh.web.endpoint.MessageManager;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("device")
@RequiredArgsConstructor
public class JT808Controller {

    private final MessageManager messageManager;

    @Operation(summary = "8103 设置终端参数")
    @PostMapping("8103")
    public Mono<APIResult<T0001>> T8103(@RequestBody T8103 request) {
        return messageManager.requestR(request.build(), T0001.class);
    }

    @Operation(summary = "8104 查询终端参数")
    @PostMapping("8104")
    public Mono<APIResult<T0104>> T8104(@RequestBody JTMessage request) {
        return messageManager.requestR(request.messageId(JT808.查询终端参数), T0104.class);
    }

    @Operation(summary = "8106 查询指定终端参数")
    @PostMapping("8106")
    public Mono<APIResult<T0104>> T8106(@RequestBody T8106 request) {
        return messageManager.requestR(request, T0104.class);
    }

    @Operation(summary = "8105 终端控制")
    @PostMapping("8105")
    public Mono<APIResult<T0001>> T8105(@RequestBody T8105 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8107 查询终端属性")
    @PostMapping("8107")
    public Mono<APIResult<T0107>> T8107(@RequestBody JTMessage request) {
        return messageManager.requestR(request.messageId(JT808.查询终端属性), T0107.class);
    }

    @Operation(summary = "8201 位置信息查询")
    @PostMapping("8201")
    public Mono<APIResult<T0201_0500>> T8201(@RequestBody JTMessage request) {
        return messageManager.requestR(request.messageId(JT808.位置信息查询), T0201_0500.class);
    }

    @Operation(summary = "8202 临时位置跟踪控制")
    @PostMapping("8202")
    public Mono<APIResult<T0001>> T8202(@RequestBody T8202 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8203 人工确认报警消息")
    @PostMapping("8203")
    public Mono<APIResult<T0001>> T8203(@RequestBody T8203 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8204 服务器向终端发起链路检测请求")
    @PostMapping("8204")
    public Mono<APIResult<T0001>> T8204(@RequestBody JTMessage request) {
        return messageManager.requestR(request.messageId(JT808.服务器向终端发起链路检测请求), T0001.class);
    }

    @Operation(summary = "8300 文本信息下发")
    @PostMapping("8300")
    public Mono<APIResult<T0001>> T8300(@RequestBody T8300 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8301 事件设置")
    @PostMapping("8301")
    public Mono<APIResult<T0001>> T8301(@RequestBody T8301 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8302 提问下发")
    @PostMapping("8302")
    public Mono<APIResult<T0001>> T8302(@RequestBody T8302 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8303 信息点播菜单设置")
    @PostMapping("8303")
    public Mono<APIResult<T0001>> T8303(@RequestBody T8303 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8304 信息服务")
    @PostMapping("8304")
    public Mono<APIResult<T0001>> T8304(@RequestBody T8304 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8400 电话回拨")
    @PostMapping("8400")
    public Mono<APIResult<T0001>> T8400(@RequestBody T8400 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8401 设置电话本")
    @PostMapping("8401")
    public Mono<APIResult<T0001>> T8401(@RequestBody T8401 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8500 车辆控制")
    @PostMapping("8500")
    public Mono<APIResult<T0201_0500>> T8500(@RequestBody T8500 request) {
        return messageManager.requestR(request, T0201_0500.class);
    }

    @Operation(summary = "8600 设置圆形区域")
    @PostMapping("8600")
    public Mono<APIResult<T0001>> T8600(@RequestBody T8600 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8601 删除圆形区域")
    @PostMapping("8601")
    public Mono<APIResult<T0001>> T8601(@RequestBody T8601 request) {
        return messageManager.requestR(request.messageId(JT808.删除圆形区域), T0001.class);
    }

    @Operation(summary = "8602 设置矩形区域")
    @PostMapping("8602")
    public Mono<APIResult<T0001>> T8602(@RequestBody T8602 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8603 删除矩形区域")
    @PostMapping("8603")
    public Mono<APIResult<T0001>> T8603(@RequestBody T8601 request) {
        return messageManager.requestR(request.messageId(JT808.删除矩形区域), T0001.class);
    }

    @Operation(summary = "8604 设置多边形区域")
    @PostMapping("8604")
    public Mono<APIResult<T0001>> T8604(@RequestBody T8604 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8605 删除多边形区域")
    @PostMapping("8605")
    public Mono<APIResult<T0001>> T8605(@RequestBody T8601 request) {
        return messageManager.requestR(request.messageId(JT808.删除多边形区域), T0001.class);
    }

    @Operation(summary = "8606 设置路线")
    @PostMapping("8606")
    public Mono<APIResult<T0001>> T8606(@RequestBody T8606 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8607 删除路线")
    @PostMapping("8607")
    public Mono<APIResult<T0001>> T8607(@RequestBody T8601 request) {
        return messageManager.requestR(request.messageId(JT808.删除路线), T0001.class);
    }

    @Operation(summary = "8608 查询区域或线路数据")
    @PostMapping("8608")
    public Mono<APIResult<T0608>> T8608(@RequestBody T8608 request) {
        return messageManager.requestR(request, T0608.class);
    }

    @Operation(summary = "8700 行驶记录仪数据采集命令")
    @PostMapping("8700")
    public Mono<APIResult<T0001>> T8700(@RequestBody JTMessage request) {
        return messageManager.requestR(request.messageId(JT808.行驶记录仪数据采集命令), T0001.class);
    }

    @Operation(summary = "8701 行驶记录仪参数下传命令")
    @PostMapping("8701")
    public Mono<APIResult<T0001>> T8701(@RequestBody T8701 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8702 上报驾驶员身份信息请求")
    @PostMapping("8702")
    public Mono<APIResult<T0702>> T8702(@RequestBody JTMessage request) {
        return messageManager.requestR(request.messageId(JT808.上报驾驶员身份信息请求), T0702.class);
    }

    @Operation(summary = "8801 摄像头立即拍摄命令")
    @PostMapping("8801")
    public Mono<APIResult<T0805>> T8801(@RequestBody T8801 request) {
        return messageManager.requestR(request, T0805.class);
    }

    @Operation(summary = "8802 存储多媒体数据检索")
    @PostMapping("8802")
    public Mono<APIResult<T0802>> T8802(@RequestBody T8802 request) {
        return messageManager.requestR(request, T0802.class);
    }

    @Operation(summary = "8803 存储多媒体数据上传")
    @PostMapping("8803")
    public Mono<APIResult<T0001>> T8803(@RequestBody T8803 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8804 录音开始命令")
    @PostMapping("8804")
    public Mono<APIResult<T0001>> T8804(@RequestBody T8804 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8805 单条存储多媒体数据检索上传命令")
    @PostMapping("8805")
    public Mono<APIResult<T0001>> T8805(@RequestBody T8805 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8108 下发终端升级包")
    @PostMapping("8108")
    public Mono<APIResult<T0001>> T8108(@RequestBody T8108 request) {
        return messageManager.requestR(request, T0001.class);
    }

    @Operation(summary = "8900 数据下行透传")
    @PostMapping("8900")
    public Mono<APIResult<T0001>> T8900(@RequestBody T8900 request) {
        return messageManager.requestR(request.build(), T0001.class);
    }

    @Operation(summary = "8A00 平台RSA公钥")
    @PostMapping("8A00")
    public Mono<APIResult<T0A00_8A00>> T8A00(@RequestBody T0A00_8A00 request) {
        return messageManager.requestR(request, T0A00_8A00.class);
    }
}