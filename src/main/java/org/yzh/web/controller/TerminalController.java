package org.yzh.web.controller;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.RawMessage;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Shape;
import org.yzh.protocol.commons.transform.Parameter;
import org.yzh.protocol.commons.transform.ParameterType;
import org.yzh.protocol.commons.transform.TerminalParameterUtils;
import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.jsatl12.T9208;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.StrUtils;
import org.yzh.web.model.APIException;
import org.yzh.web.model.enums.DefaultCodes;

import java.time.LocalDateTime;
import java.util.*;

@Api(description = "terminal api")
@RestController
@RequestMapping("terminal")
public class TerminalController {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @ApiOperation(value = "获得当前所有在线设备信息")
    @GetMapping("all")
    public Collection<Session> all() {
        return sessionManager.all();
    }

    @ApiOperation(value = "原始消息发送")
    @PostMapping("raw")
    public String postRaw(@ApiParam("终端手机号") @RequestParam String clientId,
                          @ApiParam("16进制报文") @RequestParam String message) {
        Session session = sessionManager.get(clientId);
        if (session != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(message));
            session.writeObject(byteBuf);
            return "success";
        }
        return "fail";
    }

    @ApiOperation(value = "终端参数可选项", tags = "终端管理类协议")
    @GetMapping("settings/option")
    public ParameterType[] settingsOption() {
        return ParameterType.values();
    }

    @ApiOperation(value = "设置终端参数", tags = "终端管理类协议")
    @PutMapping("settings")
    public T0001 putSettings(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody Parameter... parameters) {
        List<BytesParameter> parameterList = new ArrayList<>(parameters.length);
        for (Parameter parameter : parameters) {
            int id = parameter.getId();
            byte[] value = TerminalParameterUtils.toBytes(id, parameter.getValue());
            if (value != null)
                parameterList.add(new BytesParameter(id, value));
        }
        T8103 request = new T8103(clientId);
        request.setBytesParameters(parameterList);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "查询终端参数", tags = "终端管理类协议")
    @GetMapping("settings")
    public T0104 getSettings(@ApiParam("终端手机号") @RequestParam String clientId, @ApiParam("参数ID列表，为空查询全部，多个以逗号[,]分隔") @RequestParam(required = false) String id) {
        AbstractMessage request;
        if (id != null) {
            request = new T8106(clientId, StrUtils.toBytes(id, ","));
        } else {
            request = new RawMessage(new Header(clientId, JT808.查询终端参数));
        }
        T0104 response = messageManager.request(request, T0104.class);
        return response;
    }

    @ApiOperation(value = "终端控制", tags = "终端管理类协议")
    @PostMapping("control")
    public T0001 terminalControl(@ApiParam("终端手机号") @RequestParam String clientId,
                                 @ApiParam("命令字") @RequestParam int command, @ApiParam("命令参数") @RequestParam String parameter) {
        T8105 request = new T8105(clientId, command, parameter);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "查询终端属性", tags = "终端管理类协议")
    @GetMapping("attributes")
    public T0107 findAttributes(@ApiParam("终端手机号") @RequestParam String clientId) {
        T0107 response = messageManager.request(new RawMessage(new Header(clientId, JT808.查询终端属性)), T0107.class);
        return response;
    }

    @ApiOperation(value = "位置信息查询", tags = "位置报警类协议")
    @GetMapping("location")
    public T0201_0500 location(@ApiParam("终端手机号") @RequestParam String clientId) {
        T0201_0500 response = messageManager.request(new RawMessage(new Header(clientId, JT808.位置信息查询)), T0201_0500.class);
        return response;
    }

    @ApiOperation(value = "临时位置跟踪控制", tags = "位置报警类协议")
    @PostMapping("track")
    public T0001 track(@ApiParam("终端手机号") @RequestParam String clientId,
                       @ApiParam("时间间隔（秒）") @RequestParam int interval,
                       @ApiParam("有效期（秒）") @RequestParam int validityPeriod) {
        T8202 request = new T8202(new Header(clientId, JT808.临时位置跟踪控制), interval, validityPeriod);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "人工确认报警消息", tags = "位置报警类协议")
    @PostMapping("alarm_ack")
    public T0001 alarmAck(@ApiParam("终端手机号") @RequestParam String clientId,
                          @ApiParam("消息流水号") @RequestParam int serialNo, @ApiParam("报警类型") @RequestParam int type) {
        T8203 request = new T8203(clientId, serialNo, type);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "文本信息下发", tags = "信息类协议")
    @PostMapping("text")
    public T0001 sendText(@ApiParam("终端手机号") @RequestParam String clientId,
                          @ApiParam("标志") @RequestParam int[] sign, @ApiParam("文本信息") @RequestParam String content) {
        T8300 request = new T8300(clientId);
        request.setSign(sign);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "事件设置", tags = "信息类协议")
    @PutMapping("events")
    public T0001 eventSetting(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8301 message) {
        message.setHeader(new Header(clientId, JT808.事件设置));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "提问下发", tags = "信息类协议")
    @PostMapping("question")
    public T0001 sendQuestion(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8302 message) {
        message.setHeader(new Header(clientId, JT808.提问下发));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "信息点播菜单设置", tags = "信息类协议")
    @PutMapping("news")
    public T0001 setNews(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8303 message) {
        message.setHeader(new Header(clientId, JT808.信息点播菜单设置));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "信息服务", tags = "信息类协议")
    @PostMapping("news")
    public T0001 postNews(@ApiParam("终端手机号") @RequestParam String clientId,
                          @ApiParam("类型") @RequestParam int type, @ApiParam("内容") @RequestParam String content) {
        T8304 request = new T8304(clientId);
        request.setType(type);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "电话回拨", tags = "电话类协议")
    @PostMapping("call_phone")
    public T0001 callPhone(@ApiParam("终端手机号") @RequestParam String clientId,
                           @ApiParam("类型（0.通话 1.监听）") @RequestParam int type, @ApiParam("电话号码") @RequestParam String mobileNo) {
        T8400 request = new T8400(clientId, type, mobileNo);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "设置电话本", tags = "电话类协议")
    @PutMapping("phone_book")
    public T0001 phoneBook(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8401 message) {
        message.setHeader(new Header(clientId, JT808.设置电话本));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "车辆控制", tags = "终端管理类协议")
    @PostMapping("control/vehicle")
    public T0201_0500 vehicleControl(@ApiParam("终端手机号") @RequestParam String clientId, @ApiParam("控制标志") @RequestParam int... sign) {
        T8500 request = new T8500(clientId);
        request.setSign(sign);
        T0201_0500 response = messageManager.request(request, T0201_0500.class);
        return response;
    }

    @ApiOperation(value = "删除区域", tags = "车辆管理类协议")
    @DeleteMapping("map_fence")
    public T0001 removeMapFence(@ApiParam("终端手机号") @RequestParam String clientId,
                                @ApiParam("区域类型:1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                                @ApiParam("区域ID列表(多个以逗号[,]分割)") @RequestParam String id) {
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

    @ApiOperation(value = "设置圆形区域", tags = "车辆管理类协议")
    @PutMapping("map_fence/round")
    public T0001 addMapFenceRound(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8600 message) {
        message.setHeader(new Header(clientId, JT808.设置圆形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "设置矩形区域", tags = "车辆管理类协议")
    @PutMapping("map_fence/rectangle")
    public T0001 addMapFenceRectangle(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8602 message) {
        message.setHeader(new Header(clientId, JT808.设置矩形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "设置多边形区域", tags = "车辆管理类协议")
    @PutMapping("map_fence/polygon")
    public T0001 addMapFencePolygon(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8604 message) {
        message.setHeader(new Header(clientId, JT808.设置多边形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "设置路线", tags = "车辆管理类协议")
    @PutMapping("map_fence/route")
    public T0001 addRoute(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8606 message) {
        message.setHeader(new Header(clientId, JT808.设置路线));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "查询区域或线路数据", tags = "位置报警类协议")
    @GetMapping("location/map_fence")
    public T0608 locationRoute(@ApiParam("终端手机号") @RequestParam String clientId, @ApiParam("区域ID列表(多个以逗号,分割)") @RequestParam String id) {
        T8608 request = new T8608(clientId);
        int[] ids = StrUtils.toInts(id, ",");
        for (int i : ids)
            request.addItem(i);
        T0608 response = messageManager.request(request, T0608.class);
        return response;
    }

    @ApiOperation(value = "行驶记录仪数据采集命令", tags = "信息采集类协议")
    @GetMapping("drive_recorder/report")
    public T0001 getDataRecord(@PathVariable("clientId") String clientId) {
        RawMessage request = new RawMessage(new Header(clientId, JT808.行驶记录仪数据采集命令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "行驶记录仪参数下传命令", tags = "信息采集类协议")
    @PutMapping("drive_recorder/settings")
    public T0001 recorder(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8701 request) {
        request.setHeader(new Header(clientId, JT808.行驶记录仪参数下传命令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "上报驾驶员身份信息请求", tags = "信息采集类协议")
    @GetMapping("driver_identity/report")
    public T0702 findDriverIdentityInfo(@PathVariable("clientId") String clientId) {
        RawMessage request = new RawMessage(new Header(clientId, JT808.上报驾驶员身份信息请求));
        T0702 response = messageManager.request(request, T0702.class);
        return response;
    }

    @ApiOperation(value = "摄像头立即拍摄命令", tags = "多媒体类协议")
    @PostMapping("camera/snapshot")
    public T0805 cameraShot(@ApiParam("终端手机号") @RequestParam String clientId, T8801 request) {
        request.setHeader(new Header(clientId, JT808.摄像头立即拍摄命令));
        T0805 response = messageManager.request(request, T0805.class);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据检索", tags = "多媒体类协议")
    @GetMapping("media_data/search")
    public T0802 mediaDataQuery(@ApiParam("终端手机号") @RequestParam String clientId,
                                @ApiParam("多媒体类型:0.图像；1.音频；2.视频；") @RequestParam Integer type,
                                @ApiParam("通道ID") @RequestParam Integer channelId,
                                @ApiParam("事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留") @RequestParam Integer event,
                                @ApiParam("开始时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime startTime,
                                @ApiParam("结束时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime endTime) {
        T8802 request = new T8802(clientId);
        request.setType(type);
        request.setChannelId(channelId);
        request.setEvent(event);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        T0802 response = messageManager.request(request, T0802.class);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据上传", tags = "多媒体类协议")
    @GetMapping("media_data/report")
    public T0001 mediaDataReportRequest(@ApiParam("终端手机号") @RequestParam String clientId,
                                        @ApiParam("多媒体类型:0.图像；1.音频；2.视频；") @RequestParam Integer type,
                                        @ApiParam("通道ID") @RequestParam Integer channelId,
                                        @ApiParam("事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留") @RequestParam Integer event,
                                        @ApiParam("开始时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime startTime,
                                        @ApiParam("结束时间（yyyy-MM-dd HH:mm:ss）") @RequestParam LocalDateTime endTime,
                                        @ApiParam("删除标志:0.保留；1.删除；") @RequestParam int delete) {
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

    @ApiOperation(value = "录音开始命令", tags = "多媒体类协议")
    @PostMapping("sound_record")
    public T0001 soundRecord(@ApiParam("终端手机号") @RequestParam String clientId,
                             @ApiParam("0：停止录音；1：开始录音") @RequestParam Integer command,
                             @ApiParam("单位为秒（s），0 表示一直录音") @RequestParam Integer time,
                             @ApiParam("0：实时上传；1：保存") @RequestParam Integer saveSign,
                             @ApiParam("0：8K；1：11K；2：23K；3：32K；") @RequestParam Integer audioSampleRate) {
        T8804 request = new T8804(clientId);
        request.setCommand(command);
        request.setTime(time);
        request.setSave(saveSign);
        request.setAudioSampleRate(audioSampleRate);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "单条存储多媒体数据检索上传命令", tags = "多媒体类协议")
    @PostMapping("media_data/command")
    public T0001 mediaDataCommand(@ApiParam("终端手机号") @RequestParam String clientId,
                                  @ApiParam("多媒体ID") @RequestParam Integer id, @ApiParam("删除标志:0.保留；1.删除；") @RequestParam int delete) {
        T8805 request = new T8805(clientId, id, delete);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "服务器向终端发起链路检测请求", tags = "其他")
    @PostMapping("check_link")
    public T0001 checkLink(@PathVariable("clientId") String clientId) {
        RawMessage request = new RawMessage(new Header(clientId, JT808.服务器向终端发起链路检测请求));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "下发终端升级包", tags = "其他")
    @PostMapping("upgrade")
    public T0001 upgrade(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8108 message) {
        message.setHeader(new Header(clientId, JT808.下发终端升级包));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation(value = "数据下行透传", tags = "其他")
    @PostMapping("passthrough")
    public T0001 passthrough(@ApiParam("终端手机号") @RequestParam String clientId, @RequestBody T8900_0900 request) {
        request.setHeader(new Header(clientId, JT808.数据下行透传));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation(value = "平台RSA公钥", tags = "其他")
    @PostMapping("rsa_swap")
    public T0A00_8A00 rsaSwap(@ApiParam("终端手机号") @RequestParam String clientId,
                              @ApiParam("e") @RequestParam int e, @ApiParam("n（BASE64编码）") @RequestParam String n) {
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

    @Value("${tpc-server.jt808.alarm-file.host}")
    private String host;
    @Value("${tpc-server.jt808.alarm-file.port}")
    private int port;

    @ApiOperation(value = "报警附件上传指令/测试使用", tags = "其他")
    @GetMapping("alarm_file/report")
    public T0001 alarmFileReport(@ApiParam("终端手机号") @RequestParam String clientId) {
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