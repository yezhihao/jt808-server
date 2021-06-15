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
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.StrUtils;
import org.yzh.web.endpoint.MessageManager;
import org.yzh.web.model.APIException;
import org.yzh.web.model.enums.DefaultCodes;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("terminal")
public class JT808Controller {

    @Autowired
    private MessageManager messageManager;

    @Operation(summary = "8103 设置终端参数")
    @PutMapping("settings")
    public T0001 putSettings(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody Parameters parameters) {
        Map<Integer, Object> map = parameters.toMap();
        T8103 request = new T8103(map);
        request.setHeader(new Header(clientId));

        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8104 8106 查询终端参数")
    @GetMapping("settings")
    public T0104 getSettings(@Parameter(description = "终端手机号") @RequestParam String clientId,
                             @Parameter(description = "参数ID列表(为空查询全部,多个以逗号','分隔)") @RequestParam(required = false) String id) {
        JTMessage request;
        if (StringUtils.isBlank(id)) {
            request = new JTMessage(clientId, JT808.查询终端参数);
        } else {
            request = new T8106(StrUtils.toInts(id, ","));
            request.setHeader(new Header(clientId));
        }
        T0104 response = messageManager.request(request, T0104.class);
        return response;
    }

    @Operation(summary = "8105 终端控制")
    @PostMapping("control")
    public T0001 terminalControl(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                 @Parameter(description = "命令字") @RequestParam int command, @Parameter(description = "命令参数") @RequestParam String parameter) {
        T8105 request = new T8105(command, parameter);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "0107 查询终端属性")
    @GetMapping("attributes")
    public T0107 findAttributes(@Parameter(description = "终端手机号") @RequestParam String clientId) {
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
    @PostMapping("track")
    public T0001 track(@Parameter(description = "终端手机号") @RequestParam String clientId,
                       @Parameter(description = "时间间隔(秒)") @RequestParam int interval,
                       @Parameter(description = "有效期(秒)") @RequestParam int validityPeriod) {
        T8202 request = new T8202(interval, validityPeriod);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8203 人工确认报警消息")
    @PostMapping("alarm_ack")
    public T0001 alarmAck(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "消息流水号") @RequestParam int serialNo, @Parameter(description = "报警类型") @RequestParam int type) {
        T8203 request = new T8203(serialNo, type);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8300 文本信息下发")
    @PostMapping("text")
    public T0001 sendText(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "标志") @RequestParam int[] sign,
                          @Parameter(description = "类型: 1.通知 2.服务") @RequestParam int type,
                          @Parameter(description = "文本信息") @RequestParam String content) {
        T8300 request = new T8300(type, content, sign);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8301 事件设置")
    @PutMapping("events")
    public T0001 eventSetting(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8301 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8302 提问下发")
    @PostMapping("question")
    public T0001 sendQuestion(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8302 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8303 信息点播菜单设置")
    @PutMapping("news")
    public T0001 setNews(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8303 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8304 信息服务")
    @PostMapping("news")
    public T0001 postNews(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "类型") @RequestParam int type, @Parameter(description = "内容") @RequestParam String content) {
        T8304 request = new T8304(type, content);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8400 电话回拨")
    @PostMapping("call_phone")
    public T0001 callPhone(@Parameter(description = "终端手机号") @RequestParam String clientId,
                           @Parameter(description = "类型: 0.通话 1.监听") @RequestParam int type, @Parameter(description = "电话号码") @RequestParam String mobileNo) {
        T8400 request = new T8400(type, mobileNo);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8401 设置电话本")
    @PutMapping("phone_book")
    public T0001 phoneBook(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8401 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8500 车辆控制")
    @PostMapping("control/vehicle")
    public T0201_0500 vehicleControl(@Parameter(description = "终端手机号") @RequestParam String clientId, @Parameter(description = "控制标志") @RequestParam int... sign) {
        T8500 request = new T8500(sign);
        request.setHeader(new Header(clientId));
        T0201_0500 response = messageManager.request(request, T0201_0500.class);
        return response;
    }

    @Operation(summary = "8601 8603 8605 8607 删除区域")
    @DeleteMapping("area")
    public T0001 removeArea(@Parameter(description = "终端手机号") @RequestParam String clientId,
                            @Parameter(description = "区域类型:1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                            @Parameter(description = "区域ID列表(多个以逗号','分隔)") @RequestParam String id) {
        T8601 request = new T8601(StrUtils.toInts(id, ","));
        request.setHeader(new Header(clientId, Shape.toMessageId(type)));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8600 设置圆形区域")
    @PutMapping("area/round")
    public T0001 addAreaRound(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8600 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8602 设置矩形区域")
    @PutMapping("area/rectangle")
    public T0001 addAreaRectangle(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8602 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8604 设置多边形区域")
    @PutMapping("area/polygon")
    public T0001 addAreaPolygon(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8604 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8606 设置路线")
    @PutMapping("area/route")
    public T0001 addRoute(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8606 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8608 查询区域或线路数据")
    @GetMapping("location/area")
    public T0608 locationRoute(@Parameter(description = "终端手机号") @RequestParam String clientId,
                               @Parameter(description = "区域ID列表(多个以逗号','分隔)") @RequestParam String id) {
        T8608 request = new T8608(StrUtils.toInts(id, ","));
        request.setHeader(new Header(clientId));
        T0608 response = messageManager.request(request, T0608.class);
        return response;
    }

    @Operation(summary = "8700 行驶记录仪数据采集命令")
    @GetMapping("drive_recorder/report")
    public T0001 getDataRecord(@RequestParam("clientId") String clientId) {
        T0001 response = messageManager.request(new JTMessage(clientId, JT808.行驶记录仪数据采集命令), T0001.class);
        return response;
    }

    @Operation(summary = "8701 行驶记录仪参数下传命令")
    @PutMapping("drive_recorder/settings")
    public T0001 recorder(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8701 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8702 上报驾驶员身份信息请求")
    @GetMapping("driver_identity/report")
    public T0702 findDriverIdentityInfo(@RequestParam("clientId") String clientId) {
        T0702 response = messageManager.request(new JTMessage(clientId, JT808.上报驾驶员身份信息请求), T0702.class);
        return response;
    }

    @Operation(summary = "8801 摄像头立即拍摄命令")
    @PostMapping("camera/snapshot")
    public T0805 cameraShot(@Parameter(description = "终端手机号") @RequestParam String clientId, T8801 request) {
        request.setHeader(new Header(clientId));
        T0805 response = messageManager.request(request, T0805.class);
        return response;
    }

    @Operation(summary = "8802 存储多媒体数据检索")
    @GetMapping("media_data/search")
    public T0802 mediaDataQuery(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                @Parameter(description = "多媒体类型: 0.图像 1.音频 2.视频 ") @RequestParam Integer type,
                                @Parameter(description = "通道ID") @RequestParam Integer channelId,
                                @Parameter(description = "事件项编码: 0.平台下发指令 1.定时动作 2.抢劫报警触发 3.碰撞侧翻报警触发 其他保留") @RequestParam Integer event,
                                @Parameter(description = "开始时间") @RequestParam LocalDateTime startTime,
                                @Parameter(description = "结束时间") @RequestParam LocalDateTime endTime) {
        T8802 request = new T8802();
        request.setHeader(new Header(clientId));
        request.setType(type);
        request.setChannelId(channelId);
        request.setEvent(event);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        T0802 response = messageManager.request(request, T0802.class);
        return response;
    }

    @Operation(summary = "8803 存储多媒体数据上传")
    @GetMapping("media_data/report")
    public T0001 mediaDataReportRequest(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                        @Parameter(description = "多媒体类型: 0.图像 1.音频 2.视频 ") @RequestParam Integer type,
                                        @Parameter(description = "通道ID") @RequestParam Integer channelId,
                                        @Parameter(description = "事件项编码: 0.平台下发指令 1.定时动作 2.抢劫报警触发 3.碰撞侧翻报警触发 其他保留") @RequestParam Integer event,
                                        @Parameter(description = "开始时间") @RequestParam LocalDateTime startTime,
                                        @Parameter(description = "结束时间") @RequestParam LocalDateTime endTime,
                                        @Parameter(description = "删除标志: 0.保留 1.删除 ") @RequestParam int delete) {
        T8803 request = new T8803();
        request.setHeader(new Header(clientId));
        request.setType(type);
        request.setChannelId(channelId);
        request.setEvent(event);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setDelete(delete);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8804 录音开始命令")
    @PostMapping("sound_record")
    public T0001 soundRecord(@Parameter(description = "终端手机号") @RequestParam String clientId,
                             @Parameter(description = "录音命令: 0.停止录音 1.开始录音") @RequestParam Integer command,
                             @Parameter(description = "录音时间(秒) 0.表示一直录音") @RequestParam Integer time,
                             @Parameter(description = "保存标志: 0.实时上传 1.保存") @RequestParam Integer saveSign,
                             @Parameter(description = "音频采样率: 0: 8K 1: 11K 2: 23K 3: 32K 其他保留") @RequestParam Integer audioSamplingRate) {
        T8804 request = new T8804();
        request.setHeader(new Header(clientId));
        request.setCommand(command);
        request.setTime(time);
        request.setSave(saveSign);
        request.setAudioSamplingRate(audioSamplingRate);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8805 单条存储多媒体数据检索上传命令")
    @PostMapping("media_data/command")
    public T0001 mediaDataCommand(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                  @Parameter(description = "多媒体ID") @RequestParam long id,
                                  @Parameter(description = "删除标志: 0.保留 1.删除") @RequestParam int delete) {
        T8805 request = new T8805((int) id, delete);
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8204 服务器向终端发起链路检测请求")
    @PostMapping("check_link")
    public T0001 checkLink(@RequestParam("clientId") String clientId) {
        T0001 response = messageManager.request(new JTMessage(clientId, JT808.服务器向终端发起链路检测请求), T0001.class);
        return response;
    }

    @Operation(summary = "8108 下发终端升级包")
    @PostMapping("upgrade")
    public T0001 upgrade(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8108 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "8900 数据下行透传")
    @PostMapping("passthrough")
    public T0001 passthrough(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8900 request) {
        request.setHeader(new Header(clientId));
        T0001 response = messageManager.request(request, T0001.class);
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
        request.setHeader(new Header(clientId));
        T0A00_8A00 response = messageManager.request(request, T0A00_8A00.class);
        return response;
    }

    @Value("${tcp-server.jt808.alarm-file.host}")
    private String host;
    @Value("${tcp-server.jt808.alarm-file.port}")
    private int port;

    @Operation(summary = "9208 报警附件上传指令/测试使用")
    @GetMapping("alarm_file/report")
    public T0001 alarmFileReport(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        T9208 request = new T9208();
        request.setHeader(new Header(clientId));
        request.setIp(host);
        request.setTcpPort(port);
        request.setUdpPort(0);

        request.setAlarmId(new AlarmId("test", DateUtils.yyMMddHHmmss.format(new Date()), 0, 1, 0));
        request.setAlarmNo(UUID.randomUUID().toString().replaceAll("-", ""));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }
}