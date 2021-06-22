package org.yzh.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Shape;
import org.yzh.protocol.commons.transform.Parameters;
import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.StrUtils;
import org.yzh.web.endpoint.MessageManager;
import org.yzh.web.model.APIException;
import org.yzh.web.model.enums.DefaultCodes;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("terminal")
public class JT808Controller {

    @Autowired
    private MessageManager messageManager;

    @Value("${tcp-server.jt808.alarm-file.host}")
    private String host;

    @Value("${tcp-server.jt808.alarm-file.port}")
    private int port;

    @Operation(summary = "9208 报警附件上传指令")
    @GetMapping("alarm_file/upload")
    public T0001 alarmFileUpload(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                 @Parameter(description = "时间(YYMMDDHHMMSS)") @RequestParam String dateTime,
                                 @Parameter(description = "报警序号") @RequestParam int serialNo,
                                 @Parameter(description = "附件数量") @RequestParam int fileTotal,
                                 @Parameter(description = "IP地址") @RequestParam(required = false) String host,
                                 @Parameter(description = "端口号") @RequestParam(required = false) Integer port) {

        host = StringUtils.isBlank(host) ? this.host : host;
        port = port == null ? this.port : port;

        T9208 request = new T9208();
        request.setIp(host);
        request.setTcpPort(port);
        request.setUdpPort(0);
        request.setAlarmId(new AlarmId("", dateTime, serialNo, fileTotal, 0));
        request.setAlarmNo(UUID.randomUUID().toString().replaceAll("-", ""));

        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8103 设置终端参数")
    @PutMapping("parameters")
    public T0001 setParameters(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody Parameters parameters) {
        Map<Integer, Object> map = parameters.toMap();
        T8103 request = new T8103(map);
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8104 8106 查询终端参数")
    @GetMapping("parameters")
    public T0104 getParameters(@Parameter(description = "终端手机号") @RequestParam String clientId,
                               @Parameter(description = "参数ID列表(为空查询全部,多个以逗号','分隔)") @RequestParam(required = false) String id) {
        JTMessage request;
        if (StringUtils.isBlank(id)) {
            request = new JTMessage(clientId, JT808.查询终端参数);
        } else {
            request = new T8106(StrUtils.toInts(id, ","));
        }
        T0104 response = messageManager.request(clientId, request, T0104.class);
        return response;
    }

    @Operation(summary = "8105 终端控制")
    @PostMapping("control")
    public T0001 terminalControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T8105 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8107 查询终端属性")
    @GetMapping("attributes")
    public T0107 getAttributes(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        T0107 response = messageManager.request(new JTMessage(clientId, JT808.查询终端属性), T0107.class);
        return response;
    }

    @Operation(summary = "8201 位置信息查询")
    @GetMapping("location")
    public T0201_0500 location(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        T0201_0500 response = messageManager.request(new JTMessage(clientId, JT808.位置信息查询), T0201_0500.class);
        return response;
    }

    @Operation(summary = "8202 临时位置跟踪控制")
    @PostMapping("location/track")
    public T0001 track(@Parameter(description = "终端手机号") @RequestParam String clientId, T8202 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8203 人工确认报警消息")
    @PostMapping("alarm_ack")
    public T0001 alarmAck(@Parameter(description = "终端手机号") @RequestParam String clientId, T8203 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8300 文本信息下发")
    @PostMapping("info/text")
    public T0001 sendText(@Parameter(description = "终端手机号") @RequestParam String clientId, T8300 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8301 事件设置")
    @PutMapping("info/events")
    public T0001 setEvents(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8301 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8302 提问下发")
    @PostMapping("info/question")
    public T0001 sendQuestion(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8302 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8303 信息点播菜单设置")
    @PutMapping("info/menus")
    public T0001 setMenus(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8303 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8304 信息服务")
    @PostMapping("info/news")
    public T0001 sendNews(@Parameter(description = "终端手机号") @RequestParam String clientId, T8304 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8400 电话回拨")
    @PostMapping("phone/call_back")
    public T0001 phoneCallBack(@Parameter(description = "终端手机号") @RequestParam String clientId, T8400 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8401 设置电话本")
    @PutMapping("phone/book")
    public T0001 setPhoneBook(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8401 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8500 车辆控制")
    @PostMapping("control/vehicle")
    public T0201_0500 vehicleControl(@Parameter(description = "终端手机号") @RequestParam String clientId, T8500 request) {
        T0201_0500 response = messageManager.request(clientId, request, T0201_0500.class);
        return response;
    }

    @Operation(summary = "8600 设置圆形区域")
    @PutMapping("area/circle")
    public T0001 setAreaCircle(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8600 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8602 设置矩形区域")
    @PutMapping("area/rectangle")
    public T0001 setAreaRectangle(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8602 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8604 设置多边形区域")
    @PutMapping("area/polygon")
    public T0001 setAreaPolygon(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8604 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8606 设置路线")
    @PutMapping("area/route")
    public T0001 setAreaRoute(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8606 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8601 8603 8605 8607 删除区域")
    @DeleteMapping("area")
    public T0001 removeArea(@Parameter(description = "终端手机号") @RequestParam String clientId,
                            @Parameter(description = "区域类型: 1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                            @Parameter(description = "区域ID列表(多个以逗号','分隔)") @RequestParam String id) {
        T8601 request = new T8601(StrUtils.toInts(id, ","));
        request.setHeader(new Header(clientId, Shape.toMessageId(type)));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8608 查询区域或线路数据")
    @GetMapping("area/location")
    public T0608 findAreaLocation(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                  @Parameter(description = "查询类型: 1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                                  @Parameter(description = "区域ID列表(多个以逗号','分隔)") @RequestParam String id) {
        T8608 request = new T8608(type, StrUtils.toInts(id, ","));
        T0608 response = messageManager.request(clientId, request, T0608.class);
        return response;
    }

    @Operation(summary = "8700 行驶记录仪数据采集命令")
    @GetMapping("drive_recorder/data")
    public T0001 getDriveRecorder(@RequestParam("clientId") String clientId) {
        T0001 response = messageManager.request(new JTMessage(clientId, JT808.行驶记录仪数据采集命令), T0001.class);
        return response;
    }

    @Operation(summary = "8701 行驶记录仪参数下传命令")
    @PutMapping("drive_recorder/parameters")
    public T0001 setDriveRecorderParameters(@Parameter(description = "终端手机号") @RequestParam String clientId, T8701 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8702 上报驾驶员身份信息请求")
    @GetMapping("driver_info/upload")
    public T0702 driverInfoUpload(@RequestParam("clientId") String clientId) {
        T0702 response = messageManager.request(new JTMessage(clientId, JT808.上报驾驶员身份信息请求), T0702.class);
        return response;
    }

    @Operation(summary = "8801 摄像头立即拍摄命令")
    @PostMapping("media/video/record")
    public T0001 mediaVideoRecord(@Parameter(description = "终端手机号") @RequestParam String clientId, T8801 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8802 存储多媒体数据检索")
    @GetMapping("media/search")
    public T0802 mediaSearch(@Parameter(description = "终端手机号") @RequestParam String clientId, T8802 request) {
        T0802 response = messageManager.request(clientId, request, T0802.class);
        return response;
    }

    @Operation(summary = "8803 存储多媒体数据上传")
    @PostMapping("media/batch_upload")
    public T0001 mediaUploadBatch(@Parameter(description = "终端手机号") @RequestParam String clientId, T8803 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8804 录音开始命令")
    @PostMapping("media/audio/record")
    public T0001 mediaAudioRecord(@Parameter(description = "终端手机号") @RequestParam String clientId, T8804 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8805 单条存储多媒体数据检索上传命令")
    @PostMapping("media/upload")
    public T0001 mediaUpload(@Parameter(description = "终端手机号") @RequestParam String clientId, T8805 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8204 服务器向终端发起链路检测请求")
    @PostMapping("link_detection")
    public T0001 link_detection(@RequestParam("clientId") String clientId) {
        T0001 response = messageManager.request(new JTMessage(clientId, JT808.服务器向终端发起链路检测请求), T0001.class);
        return response;
    }

    @Operation(summary = "8108 下发终端升级包")
    @PostMapping("upgrade")
    public T0001 upgrade(@Parameter(description = "终端手机号") @RequestParam String clientId, T8108 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8900 数据下行透传")
    @PostMapping("passthrough")
    public T0001 passthrough(@Parameter(description = "终端手机号") @RequestParam String clientId, T8900 request) {
        T0001 response = messageManager.request(clientId, request, T0001.class);
        return response;
    }

    @Operation(summary = "8A00 平台RSA公钥")
    @PostMapping("rsa_swap")
    public T0A00_8A00 rsaSwap(@Parameter(description = "终端手机号") @RequestParam String clientId,
                              @Parameter(description = "e") @RequestParam int e,
                              @Parameter(description = "n(BASE64编码)") @RequestParam String n) {
        byte[] src = Base64.getDecoder().decode(n);
        if (src.length == 129) {
            byte[] dest = new byte[128];
            System.arraycopy(src, 1, dest, 0, 128);
            src = dest;
        }
        if (src.length != 128)
            throw new APIException(DefaultCodes.InvalidParameter, "e length is not 128");

        T0A00_8A00 request = new T0A00_8A00(e, src);
        T0A00_8A00 response = messageManager.request(clientId, request, T0A00_8A00.class);
        return response;
    }
}