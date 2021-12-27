package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.yzh.commons.model.APICodes;
import org.yzh.commons.model.APIException;
import org.yzh.commons.model.APIResult;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Shape;
import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.t808.*;
import org.yzh.web.endpoint.MessageManager;
import org.yzh.web.model.vo.Parameters;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("terminal")
public class JT808Controller {

    @Autowired
    private MessageManager messageManager;

    @Value("${jt-server.jt808.alarm-file.host}")
    private String host;

    @Value("${jt-server.jt808.alarm-file.port}")
    private int port;

    @Operation(summary = "9208 报警附件上传指令")
    @GetMapping("alarm_file/upload")
    public Mono<APIResult<T0001>> alarmFileUpload(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                                  @Parameter(description = "时间(YYMMDDHHMMSS)") @RequestParam String dateTime,
                                                  @Parameter(description = "报警序号") @RequestParam int serialNo,
                                                  @Parameter(description = "附件数量") @RequestParam int fileTotal,
                                                  @Parameter(description = "IP地址") String host,
                                                  @Parameter(description = "端口号") Integer port) {

        host = StrUtils.isBlank(host) ? this.host : host;
        port = port == null ? this.port : port;

        T9208 request = new T9208();
        request.setIp(host);
        request.setTcpPort(port);
        request.setUdpPort(0);
        request.setAlarmId(new AlarmId(clientId, dateTime, serialNo, fileTotal, 0));
        request.setAlarmNo(UUID.randomUUID().toString().replaceAll("-", ""));

        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8103 设置终端参数")
    @PutMapping("parameters")
    public Mono<APIResult<T0001>> setParameters(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody Parameters parameters) {
        Map<Integer, Object> map = parameters.toMap();
        T8103 request = new T8103(map);
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8104 8106 查询终端参数")
    @GetMapping("parameters")
    public Mono<APIResult<T0104>> getParameters(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                                @Parameter(description = "参数ID列表(为空查询全部,多个以逗号','分隔)") String id) {
        JTMessage request;
        if (StrUtils.isBlank(id)) {
            request = new JTMessage(JT808.查询终端参数);
        } else {
            request = new T8106(StrUtils.toInts(id, ","));
        }
        Mono<APIResult<T0104>> response = messageManager.requestR(clientId, request, T0104.class);
        return response;
    }

    @Operation(summary = "8105 终端控制")
    @PostMapping("control")
    public Mono<APIResult<T0001>> terminalControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T8105 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8107 查询终端属性")
    @GetMapping("attributes")
    public Mono<APIResult<T0107>> getAttributes(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        Mono<APIResult<T0107>> response = messageManager.requestR(clientId, new JTMessage(JT808.查询终端属性), T0107.class);
        return response;
    }

    @Operation(summary = "8201 位置信息查询")
    @GetMapping("location")
    public Mono<APIResult<T0201_0500>> location(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        Mono<APIResult<T0201_0500>> response = messageManager.requestR(clientId, new JTMessage(JT808.位置信息查询), T0201_0500.class);
        return response;
    }

    @Operation(summary = "8202 临时位置跟踪控制")
    @PostMapping("location/track")
    public Mono<APIResult<T0001>> track(@Parameter(description = "终端手机号") @RequestParam String clientId, T8202 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8203 人工确认报警消息")
    @PostMapping("alarm_ack")
    public Mono<APIResult<T0001>> alarmAck(@Parameter(description = "终端手机号") @RequestParam String clientId, T8203 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8300 文本信息下发")
    @PostMapping("info/text")
    public Mono<APIResult<T0001>> sendText(@Parameter(description = "终端手机号") @RequestParam String clientId, T8300 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8301 事件设置")
    @PutMapping("info/events")
    public Mono<APIResult<T0001>> setEvents(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8301 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8302 提问下发")
    @PostMapping("info/question")
    public Mono<APIResult<T0001>> sendQuestion(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8302 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8303 信息点播菜单设置")
    @PutMapping("info/menus")
    public Mono<APIResult<T0001>> setMenus(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8303 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8304 信息服务")
    @PostMapping("info/news")
    public Mono<APIResult<T0001>> sendNews(@Parameter(description = "终端手机号") @RequestParam String clientId, T8304 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8400 电话回拨")
    @PostMapping("phone/call_back")
    public Mono<APIResult<T0001>> phoneCallBack(@Parameter(description = "终端手机号") @RequestParam String clientId, T8400 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8401 设置电话本")
    @PutMapping("phone/book")
    public Mono<APIResult<T0001>> setPhoneBook(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8401 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8500 车辆控制")
    @PostMapping("control/vehicle")
    public Mono<APIResult<T0201_0500>> vehicleControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T8500 request) {
        Mono<APIResult<T0201_0500>> response = messageManager.requestR(clientId, request, T0201_0500.class);
        return response;
    }

    @Operation(summary = "8600 设置圆形区域")
    @PutMapping("area/circle")
    public Mono<APIResult<T0001>> setAreaCircle(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8600 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8602 设置矩形区域")
    @PutMapping("area/rectangle")
    public Mono<APIResult<T0001>> setAreaRectangle(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8602 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8604 设置多边形区域")
    @PutMapping("area/polygon")
    public Mono<APIResult<T0001>> setAreaPolygon(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8604 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8606 设置路线")
    @PutMapping("area/route")
    public Mono<APIResult<T0001>> setAreaRoute(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8606 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8601 8603 8605 8607 删除区域")
    @DeleteMapping("area")
    public Mono<APIResult<T0001>> removeArea(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                             @Parameter(description = "区域类型：1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                                             @Parameter(description = "区域ID列表(多个以逗号','分隔)") @RequestParam String id) {
        T8601 request = new T8601(StrUtils.toInts(id, ","));
        request.setMessageId(Shape.toMessageId(type));
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8608 查询区域或线路数据")
    @GetMapping("area/location")
    public Mono<APIResult<T0608>> findAreaLocation(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                                   @Parameter(description = "查询类型：1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                                                   @Parameter(description = "区域ID列表(多个以逗号','分隔)") @RequestParam String id) {
        T8608 request = new T8608(type, StrUtils.toInts(id, ","));
        Mono<APIResult<T0608>> response = messageManager.requestR(clientId, request, T0608.class);
        return response;
    }

    @Operation(summary = "8700 行驶记录仪数据采集命令")
    @GetMapping("drive_recorder/data")
    public Mono<APIResult<T0001>> getDriveRecorder(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, new JTMessage(JT808.行驶记录仪数据采集命令), T0001.class);
        return response;
    }

    @Operation(summary = "8701 行驶记录仪参数下传命令")
    @PutMapping("drive_recorder/parameters")
    public Mono<APIResult<T0001>> setDriveRecorderParameters(@Parameter(description = "终端手机号") @RequestParam String clientId, T8701 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8702 上报驾驶员身份信息请求")
    @GetMapping("driver_info/upload")
    public Mono<APIResult<T0702>> driverInfoUpload(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        Mono<APIResult<T0702>> response = messageManager.requestR(clientId, new JTMessage(JT808.上报驾驶员身份信息请求), T0702.class);
        return response;
    }

    @Operation(summary = "8801 摄像头立即拍摄命令")
    @PostMapping("media/video/record")
    public Mono<APIResult<T0805>> mediaVideoRecord(@Parameter(description = "终端手机号") @RequestParam String clientId, T8801 request) {
        Mono<APIResult<T0805>> response = messageManager.requestR(clientId, request, T0805.class);
        return response;
    }

    @Operation(summary = "8802 存储多媒体数据检索")
    @GetMapping("media/search")
    public Mono<APIResult<T0802>> mediaSearch(@Parameter(description = "终端手机号") @RequestParam String clientId, T8802 request) {
        Mono<APIResult<T0802>> response = messageManager.requestR(clientId, request, T0802.class);
        return response;
    }

    @Operation(summary = "8803 存储多媒体数据上传")
    @PostMapping("media/batch_upload")
    public Mono<APIResult<T0001>> mediaUploadBatch(@Parameter(description = "终端手机号") @RequestParam String clientId, T8803 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8804 录音开始命令")
    @PostMapping("media/audio/record")
    public Mono<APIResult<T0001>> mediaAudioRecord(@Parameter(description = "终端手机号") @RequestParam String clientId, T8804 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8805 单条存储多媒体数据检索上传命令")
    @PostMapping("media/upload")
    public Mono<APIResult<T0001>> mediaUpload(@Parameter(description = "终端手机号") @RequestParam String clientId, T8805 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8204 服务器向终端发起链路检测请求")
    @PostMapping("link_detection")
    public Mono<APIResult<T0001>> link_detection(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, new JTMessage(JT808.服务器向终端发起链路检测请求), T0001.class);
        return response;
    }

    @Operation(summary = "8108 下发终端升级包")
    @PostMapping("upgrade")
    public Mono<APIResult<T0001>> upgrade(@Parameter(description = "终端手机号") @RequestParam String clientId, T8108 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8900 数据下行透传")
    @PostMapping("passthrough")
    public Mono<APIResult<T0001>> passthrough(@Parameter(description = "终端手机号") @RequestParam String clientId, T8900 request) {
        Mono<APIResult<T0001>> response = messageManager.requestR(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8A00 平台RSA公钥")
    @PostMapping("rsa_swap")
    public Mono<APIResult<T0A00_8A00>> rsaSwap(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                               @Parameter(description = "e") @RequestParam int e,
                                               @Parameter(description = "n(BASE64编码)") @RequestParam String n) {
        byte[] src = Base64.getDecoder().decode(n);
        if (src.length == 129) {
            byte[] dest = new byte[128];
            System.arraycopy(src, 1, dest, 0, 128);
            src = dest;
        }
        if (src.length != 128)
            throw new APIException(APICodes.InvalidParameter, "e length is not 128");

        T0A00_8A00 request = new T0A00_8A00(e, src);
        Mono<APIResult<T0A00_8A00>> response = messageManager.requestR(clientId, request, T0A00_8A00.class);
        return response;
    }
}