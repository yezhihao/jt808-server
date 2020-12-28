package org.yzh.web.controller;

import io.github.yezhihao.netmc.session.MessageManager;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Shape;
import org.yzh.protocol.commons.transform.Parameters;
import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.StrUtils;
import org.yzh.web.model.APIException;
import org.yzh.web.model.enums.DefaultCodes;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("terminal")
public class TerminalController {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Operation(summary = "获得当前所有在线设备信息")
    @GetMapping("all")
    public Collection<Session> all() {
        return sessionManager.all();
    }

    @Operation(summary = "原始消息发送")
    @PostMapping("raw")
    public String postRaw(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "16进制报文") @RequestParam String message) {
        Session session = sessionManager.get(clientId);
        if (session != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(message));
            session.writeObject(byteBuf);
            return "success";
        }
        return "fail";
    }

    @Operation(summary = "设置终端参数")
    @PutMapping("settings")
    public T0001 putSettings(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody Parameters parameters) {
        Map<Integer, Object> map = parameters.toMap();
        T8103 request = new T8103(clientId);
        request.setParameters(map);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "查询终端参数")
    @GetMapping("settings")
    public T0104 getSettings(@Parameter(description = "终端手机号") @RequestParam String clientId, @Parameter(description = "参数ID列表，为空查询全部，多个以逗号[,]分隔") @RequestParam(required = false) String id) {
        JTMessage request;
        if (id != null) {
            request = new T8106(clientId, StrUtils.toBytes(id, ","));
        } else {
            request = new JTMessage(new Header(clientId, JT808.查询终端参数));
        }
        T0104 response = messageManager.request(request, T0104.class);
        return response;
    }

    @Operation(summary = "终端控制")
    @PostMapping("control")
    public T0001 terminalControl(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                 @Parameter(description = "命令字") @RequestParam int command, @Parameter(description = "命令参数") @RequestParam String parameter) {
        T8105 request = new T8105(clientId, command, parameter);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "查询终端属性")
    @GetMapping("attributes")
    public T0107 findAttributes(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        T0107 response = messageManager.request(new JTMessage(new Header(clientId, JT808.查询终端属性)), T0107.class);
        return response;
    }

    @Operation(summary = "位置信息查询")
    @GetMapping("location")
    public T0201_0500 location(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        T0201_0500 response = messageManager.request(new JTMessage(new Header(clientId, JT808.位置信息查询)), T0201_0500.class);
        return response;
    }

    @Operation(summary = "临时位置跟踪控制")
    @PostMapping("track")
    public T0001 track(@Parameter(description = "终端手机号") @RequestParam String clientId,
                       @Parameter(description = "时间间隔（秒）") @RequestParam int interval,
                       @Parameter(description = "有效期（秒）") @RequestParam int validityPeriod) {
        T8202 request = new T8202(new Header(clientId, JT808.临时位置跟踪控制), interval, validityPeriod);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "人工确认报警消息")
    @PostMapping("alarm_ack")
    public T0001 alarmAck(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "消息流水号") @RequestParam int serialNo, @Parameter(description = "报警类型") @RequestParam int type) {
        T8203 request = new T8203(clientId, serialNo, type);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "文本信息下发")
    @PostMapping("text")
    public T0001 sendText(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "标志") @RequestParam int[] sign, @Parameter(description = "文本信息") @RequestParam String content) {
        T8300 request = new T8300(clientId);
        request.setSign(sign);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "事件设置")
    @PutMapping("events")
    public T0001 eventSetting(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8301 message) {
        message.setHeader(new Header(clientId, JT808.事件设置));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "提问下发")
    @PostMapping("question")
    public T0001 sendQuestion(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8302 message) {
        message.setHeader(new Header(clientId, JT808.提问下发));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "信息点播菜单设置")
    @PutMapping("news")
    public T0001 setNews(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8303 message) {
        message.setHeader(new Header(clientId, JT808.信息点播菜单设置));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "信息服务")
    @PostMapping("news")
    public T0001 postNews(@Parameter(description = "终端手机号") @RequestParam String clientId,
                          @Parameter(description = "类型") @RequestParam int type, @Parameter(description = "内容") @RequestParam String content) {
        T8304 request = new T8304(clientId);
        request.setType(type);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "电话回拨")
    @PostMapping("call_phone")
    public T0001 callPhone(@Parameter(description = "终端手机号") @RequestParam String clientId,
                           @Parameter(description = "类型（0.通话 1.监听）") @RequestParam int type, @Parameter(description = "电话号码") @RequestParam String mobileNo) {
        T8400 request = new T8400(clientId, type, mobileNo);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "设置电话本")
    @PutMapping("phone_book")
    public T0001 phoneBook(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8401 message) {
        message.setHeader(new Header(clientId, JT808.设置电话本));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "车辆控制")
    @PostMapping("control/vehicle")
    public T0201_0500 vehicleControl(@Parameter(description = "终端手机号") @RequestParam String clientId, @Parameter(description = "控制标志") @RequestParam int... sign) {
        T8500 request = new T8500(clientId);
        request.setSign(sign);
        T0201_0500 response = messageManager.request(request, T0201_0500.class);
        return response;
    }

    @Operation(summary = "删除区域")
    @DeleteMapping("map_fence")
    public T0001 removeMapFence(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                @Parameter(description = "区域类型:1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                                @Parameter(description = "区域ID列表(多个以逗号[,]分割)") @RequestParam String id) {
        int messageId = 0;
        if (type == Shape.Round) {
            messageId = JT808.删除圆形区域;
        } else if (type == Shape.Rectangle) {
            messageId = JT808.删除矩形区域;
        } else if (type == Shape.Polygon) {
            messageId = JT808.删除多边形区域;
        } else if (type == Shape.Route) {
            messageId = JT808.删除路线;
        }
        T8601 request = new T8601(new Header(clientId, messageId));
        int[] ids = StrUtils.toInts(id, ",");
        for (int i : ids)
            request.addItem(i);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "设置圆形区域")
    @PutMapping("map_fence/round")
    public T0001 addMapFenceRound(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8600 message) {
        message.setHeader(new Header(clientId, JT808.设置圆形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "设置矩形区域")
    @PutMapping("map_fence/rectangle")
    public T0001 addMapFenceRectangle(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8602 message) {
        message.setHeader(new Header(clientId, JT808.设置矩形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "设置多边形区域")
    @PutMapping("map_fence/polygon")
    public T0001 addMapFencePolygon(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8604 message) {
        message.setHeader(new Header(clientId, JT808.设置多边形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "设置路线")
    @PutMapping("map_fence/route")
    public T0001 addRoute(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8606 message) {
        message.setHeader(new Header(clientId, JT808.设置路线));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "查询区域或线路数据")
    @GetMapping("location/map_fence")
    public T0608 locationRoute(@Parameter(description = "终端手机号") @RequestParam String clientId, @Parameter(description = "区域ID列表(多个以逗号,分割)") @RequestParam String id) {
        T8608 request = new T8608(clientId);
        int[] ids = StrUtils.toInts(id, ",");
        for (int i : ids)
            request.addItem(i);
        T0608 response = messageManager.request(request, T0608.class);
        return response;
    }

    @Operation(summary = "行驶记录仪数据采集命令")
    @GetMapping("drive_recorder/report")
    public T0001 getDataRecord(@PathVariable("clientId") String clientId) {
        JTMessage request = new JTMessage(new Header(clientId, JT808.行驶记录仪数据采集命令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "行驶记录仪参数下传命令")
    @PutMapping("drive_recorder/settings")
    public T0001 recorder(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8701 request) {
        request.setHeader(new Header(clientId, JT808.行驶记录仪参数下传命令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "上报驾驶员身份信息请求")
    @GetMapping("driver_identity/report")
    public T0702 findDriverIdentityInfo(@PathVariable("clientId") String clientId) {
        JTMessage request = new JTMessage(new Header(clientId, JT808.上报驾驶员身份信息请求));
        T0702 response = messageManager.request(request, T0702.class);
        return response;
    }

    @Operation(summary = "摄像头立即拍摄命令")
    @PostMapping("camera/snapshot")
    public T0805 cameraShot(@Parameter(description = "终端手机号") @RequestParam String clientId, T8801 request) {
        request.setHeader(new Header(clientId, JT808.摄像头立即拍摄命令));
        T0805 response = messageManager.request(request, T0805.class);
        return response;
    }

    @Operation(summary = "存储多媒体数据检索")
    @GetMapping("media_data/search")
    public T0802 mediaDataQuery(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                @Parameter(description = "多媒体类型:0.图像；1.音频；2.视频；") @RequestParam Integer type,
                                @Parameter(description = "通道ID") @RequestParam Integer channelId,
                                @Parameter(description = "事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留") @RequestParam Integer event,
                                @Parameter(description = "开始时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime startTime,
                                @Parameter(description = "结束时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime endTime) {
        T8802 request = new T8802(clientId);
        request.setType(type);
        request.setChannelId(channelId);
        request.setEvent(event);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        T0802 response = messageManager.request(request, T0802.class);
        return response;
    }

    @Operation(summary = "存储多媒体数据上传")
    @GetMapping("media_data/report")
    public T0001 mediaDataReportRequest(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                        @Parameter(description = "多媒体类型:0.图像；1.音频；2.视频；") @RequestParam Integer type,
                                        @Parameter(description = "通道ID") @RequestParam Integer channelId,
                                        @Parameter(description = "事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留") @RequestParam Integer event,
                                        @Parameter(description = "开始时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime startTime,
                                        @Parameter(description = "结束时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime endTime,
                                        @Parameter(description = "删除标志:0.保留；1.删除；") @RequestParam int delete) {
        T8803 request = new T8803(clientId);
        request.setType(type);
        request.setChannelId(channelId);
        request.setEvent(event);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setDelete(delete);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "录音开始命令")
    @PostMapping("sound_record")
    public T0001 soundRecord(@Parameter(description = "终端手机号") @RequestParam String clientId,
                             @Parameter(description = "0：停止录音；1：开始录音") @RequestParam Integer command,
                             @Parameter(description = "单位为秒（s），0 表示一直录音") @RequestParam Integer time,
                             @Parameter(description = "0：实时上传；1：保存") @RequestParam Integer saveSign,
                             @Parameter(description = "0：8K；1：11K；2：23K；3：32K；") @RequestParam Integer audioSamplingRate) {
        T8804 request = new T8804(clientId);
        request.setCommand(command);
        request.setTime(time);
        request.setSave(saveSign);
        request.setAudioSamplingRate(audioSamplingRate);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "单条存储多媒体数据检索上传命令")
    @PostMapping("media_data/command")
    public T0001 mediaDataCommand(@Parameter(description = "终端手机号") @RequestParam String clientId,
                                  @Parameter(description = "多媒体ID") @RequestParam Integer id, @Parameter(description = "删除标志:0.保留；1.删除；") @RequestParam int delete) {
        T8805 request = new T8805(clientId, id, delete);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "服务器向终端发起链路检测请求")
    @PostMapping("check_link")
    public T0001 checkLink(@PathVariable("clientId") String clientId) {
        JTMessage request = new JTMessage(new Header(clientId, JT808.服务器向终端发起链路检测请求));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "下发终端升级包")
    @PostMapping("upgrade")
    public T0001 upgrade(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T8108 message) {
        message.setHeader(new Header(clientId, JT808.下发终端升级包));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @Operation(summary = "数据下行透传")
    @PostMapping("passthrough")
    public T0001 passthrough(@Parameter(description = "终端手机号") @RequestParam String clientId, @RequestBody T0900 request) {
        request.setHeader(new Header(clientId, JT808.数据下行透传));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @Operation(summary = "平台RSA公钥")
    @PostMapping("rsa_swap")
    public T0A00_8A00 rsaSwap(@Parameter(description = "终端手机号") @RequestParam String clientId,
                              @Parameter(description = "e") @RequestParam int e, @Parameter(description = "n（BASE64编码）") @RequestParam String n) {
        byte[] src = Base64.getDecoder().decode(n);
        if (src.length == 129) {
            byte[] dest = new byte[128];
            System.arraycopy(src, 1, dest, 0, 128);
            src = dest;
        }
        if (src.length != 128)
            throw new APIException(DefaultCodes.InvalidParameter, "e length is not 128");

        T0A00_8A00 request = new T0A00_8A00(e, src);
        request.setHeader(new Header(clientId, JT808.平台RSA公钥));
        T0A00_8A00 response = messageManager.request(request, T0A00_8A00.class);
        return response;
    }

    @Value("${tcp-server.jt808.alarm-file.host}")
    private String host;
    @Value("${tcp-server.jt808.alarm-file.port}")
    private int port;

    @Operation(summary = "报警附件上传指令/测试使用")
    @GetMapping("alarm_file/report")
    public T0001 alarmFileReport(@Parameter(description = "终端手机号") @RequestParam String clientId) {
        T9208 request = new T9208();
        request.setHeader(new Header(clientId, JSATL12.报警附件上传指令));
        request.setIp(host);
        request.setTcpPort(port);
        request.setUdpPort(0);

        request.setAlarmId(new AlarmId("test", DateUtils.yyMMddHHmmss.format(new Date()), 0, 1, 0));
        request.setAlarmNo(UUID.randomUUID().toString().replaceAll("-", ""));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }
}