package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.RawMessage;
import org.yzh.framework.session.MessageManager;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.TerminalParameter;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Shape;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.StrUtils;

import java.util.List;

@Api(description = "terminal api")
@RestController
@RequestMapping("terminal")
public class TerminalController {
    private MessageManager messageManager = MessageManager.getInstance();

    @ApiOperation("设置终端参数")
    @PostMapping("{clientId}/parameters")
    public T0001 updateParameters(@PathVariable("clientId") String clientId, @RequestBody List<TerminalParameter> parameters) {
        T8103 request = new T8103(clientId);
        request.setItems(parameters);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("查询终端参数/查询指定终端参数")
    @GetMapping("{clientId}/parameters")
    public T0104 findParameters(@PathVariable("clientId") String clientId,
                                @ApiParam("参数ID列表，为空则查询全部") @RequestParam(required = false) byte... id) {
        AbstractMessage request;
        if (id != null) {
            request = new T8106(clientId, id);
        } else {
            request = new RawMessage(new Header(clientId, JT808.查询终端参数));
        }
        T0104 response = messageManager.request(request, T0104.class);
        return response;
    }

    @ApiOperation("终端控制")
    @PostMapping("{clientId}/control/terminal")
    public T0001 terminalControl(@PathVariable("clientId") String clientId,
                                 @ApiParam("命令字") @RequestParam int command, @ApiParam("命令参数") @RequestParam String parameter) {
        T8105 request = new T8105(clientId, command, parameter);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("查询终端属性")
    @GetMapping("{clientId}/attributes")
    public T0107 findAttributes(@PathVariable("clientId") String clientId) {
        T0107 response = messageManager.request(new RawMessage(new Header(clientId, JT808.查询终端属性)), T0107.class);
        return response;
    }

    @ApiOperation("下发终端升级包")
    @PostMapping("{clientId}/upgrade")
    public T0001 upgrade(@PathVariable("clientId") String clientId, @RequestBody T8108 message) {
        message.setHeader(new Header(clientId, JT808.下发终端升级包));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("位置信息查询")
    @GetMapping("{clientId}/position")
    public T0201_0500 position(@PathVariable("clientId") String clientId) {
        T0201_0500 response = messageManager.request(new RawMessage(new Header(clientId, JT808.位置信息查询)), T0201_0500.class);
        return response;
    }

    @ApiOperation("临时位置跟踪控制")
    @PostMapping("{clientId}/track")
    public T0001 track(@PathVariable("clientId") String clientId,
                       @ApiParam("时间间隔（秒）") @RequestParam int interval,
                       @ApiParam("有效期（秒）") @RequestParam int validityPeriod) {
        T8202 request = new T8202(new Header(clientId, JT808.临时位置跟踪控制), interval, validityPeriod);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("人工确认报警消息")
    @PostMapping("{clientId}/alarm_ack")
    public T0001 人工确认报警消息(@PathVariable("clientId") String clientId,
                          @ApiParam("消息流水号") @RequestParam int serialNo, @ApiParam("报警类型") @RequestParam int type) {
        T8203 request = new T8203(clientId, serialNo, type);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("服务器向终端发起链路检测请求")
    @PostMapping("{clientId}/check_link")
    public T0001 checkLink(@PathVariable("clientId") String clientId) {
        RawMessage request = new RawMessage(new Header(clientId, JT808.服务器向终端发起链路检测请求));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("文本信息下发")
    @PostMapping("{clientId}/text")
    public T0001 sendText(@PathVariable("clientId") String clientId,
                          @ApiParam("标志") @RequestParam int[] sign, @ApiParam("文本信息") @RequestParam String content) {
        T8300 request = new T8300(clientId);
        request.setSign(sign);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("事件设置")
    @PostMapping("{clientId}/events")
    public T0001 eventSetting(@PathVariable("clientId") String clientId, @RequestBody T8301 message) {
        message.setHeader(new Header(clientId, JT808.事件设置));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("提问下发")
    @PostMapping("{clientId}/question")
    public T0001 sendQuestion(@PathVariable("clientId") String clientId, @RequestBody T8302 message) {
        message.setHeader(new Header(clientId, JT808.提问下发));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("信息点播菜单设置")
    @PostMapping("{clientId}/information/menu")
    public T0001 infoMenu(@PathVariable("clientId") String clientId, @RequestBody T8303 message) {
        message.setHeader(new Header(clientId, JT808.信息点播菜单设置));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("信息服务")
    @PostMapping("{clientId}/information/push")
    public T0001 messageSubSetting(@PathVariable("clientId") String clientId,
                                   @ApiParam("类型") @RequestParam int type, @ApiParam("内容") @RequestParam String content) {
        T8304 request = new T8304(clientId);
        request.setType(type);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("电话回拨")
    @PostMapping("{clientId}/call_phone")
    public T0001 callPhone(@PathVariable("clientId") String clientId,
                           @ApiParam("类型（0.通话 1.监听）") @RequestParam int type, @ApiParam("电话号码") @RequestParam String mobileNo) {
        T8400 request = new T8400(clientId, type, mobileNo);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("设置电话本")
    @PostMapping("{clientId}/phone_book")
    public T0001 phoneBook(@PathVariable("clientId") String clientId, @RequestBody T8401 message) {
        message.setHeader(new Header(clientId, JT808.设置电话本));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("车辆控制")
    @PostMapping("{clientId}/control/vehicle")
    public T0201_0500 vehicleControl(@PathVariable("clientId") String clientId, @ApiParam("控制标志") @RequestParam int... sign) {
        T8500 request = new T8500(clientId);
        request.setSign(sign);
        T0201_0500 response = messageManager.request(request, T0201_0500.class);
        return response;
    }

    @ApiOperation("删除区域")
    @DeleteMapping("{clientId}/map_fence")
    public T0001 removeMapFence(@PathVariable("clientId") String clientId,
                                @ApiParam("区域类型:1.圆形 2.矩形 3.多边形 4.路线") @RequestParam int type,
                                @ApiParam("区域ID列表(多个以逗号,分割)") @RequestParam String id) {
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

    @ApiOperation("设置圆形区域")
    @PostMapping("{clientId}/map_fence_round")
    public T0001 addMapFenceRound(@PathVariable("clientId") String clientId, @RequestBody T8600 message) {
        message.setHeader(new Header(clientId, JT808.设置圆形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置矩形区域")
    @PostMapping("{clientId}/map_fence_rectangle")
    public T0001 addMapFenceRectangle(@PathVariable("clientId") String clientId, @RequestBody T8602 message) {
        message.setHeader(new Header(clientId, JT808.设置矩形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置多边形区域")
    @PostMapping("{clientId}/map_fence_polygon")
    public T0001 addMapFencePolygon(@PathVariable("clientId") String clientId, @RequestBody T8604 message) {
        message.setHeader(new Header(clientId, JT808.设置多边形区域));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置路线")
    @PostMapping("{clientId}/route")
    public T0001 addRoute(@PathVariable("clientId") String clientId, @RequestBody T8606 message) {
        message.setHeader(new Header(clientId, JT808.设置路线));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("查询区域或线路数据")
    @GetMapping("{clientId}/location/route")
    public T0608 locationRoute(@PathVariable("clientId") String clientId, @ApiParam("区域ID列表(多个以逗号,分割)") @RequestParam String id) {
        T8608 request = new T8608(clientId);
        int[] ids = StrUtils.toInts(id, ",");
        for (int i : ids)
            request.addItem(i);
        T0608 response = messageManager.request(request, T0608.class);
        return response;
    }

    @ApiOperation("行驶记录仪数据采集命令")
    @GetMapping("{clientId}/data_record")
    public T0001 getDataRecord(@PathVariable("clientId") String clientId) {
        RawMessage request = new RawMessage(new Header(clientId, JT808.行驶记录仪数据采集命令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("行驶记录仪参数下传命令")
    @GetMapping("{clientId}/recorder")
    public T0001 recorder(@PathVariable("clientId") String clientId, @RequestBody T8701 request) {
        request.setHeader(new Header(clientId, JT808.行驶记录仪参数下传命令));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("上报驾驶员身份信息请求")
    @GetMapping("{clientId}/driver_identity")
    public T0702 findDriverIdentityInfo(@PathVariable("clientId") String clientId) {
        RawMessage request = new RawMessage(new Header(clientId, JT808.上报驾驶员身份信息请求));
        T0702 response = messageManager.request(request, T0702.class);
        return response;
    }

    @ApiOperation("摄像头立即拍摄命令")
    @PostMapping("{clientId}/camera_shot")
    public T0805 cameraShot(@PathVariable("clientId") String clientId, T8801 request) {
        request.setHeader(new Header(clientId, JT808.摄像头立即拍摄命令));
        T0805 response = messageManager.request(request, T0805.class);
        return response;
    }

    @ApiOperation("存储多媒体数据检索")
    @GetMapping("{clientId}/mediadata_query")
    public T0802 mediaDataQuery(@PathVariable("clientId") String clientId,
                                @ApiParam("多媒体类型:0.图像；1.音频；2.视频；") @RequestParam Integer type,
                                @ApiParam("通道ID") @RequestParam Integer channelId,
                                @ApiParam("事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留") @RequestParam Integer event,
                                @ApiParam("开始时间（yyMMddHHmmss）") @RequestParam String startTime,
                                @ApiParam("结束时间（yyMMddHHmmss）") @RequestParam String endTime) {
        T8802 request = new T8802(clientId);
        request.setType(type);
        request.setChannelId(channelId);
        request.setEvent(event);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        T0802 response = messageManager.request(request, T0802.class);
        return response;
    }

    @ApiOperation("存储多媒体数据上传")
    @PostMapping("{clientId}/mediadata_report")
    public T0001 mediaDataReportRequest(@PathVariable("clientId") String clientId,
                                        @ApiParam("多媒体类型:0.图像；1.音频；2.视频；") @RequestParam Integer type,
                                        @ApiParam("通道ID") @RequestParam Integer channelId,
                                        @ApiParam("事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留") @RequestParam Integer event,
                                        @ApiParam("开始时间（yyMMddHHmmss）") @RequestParam String startTime,
                                        @ApiParam("结束时间（yyMMddHHmmss）") @RequestParam String endTime,
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

    @ApiOperation("录音开始命令")
    @PostMapping("{clientId}/sound_record")
    public T0001 soundRecord(@PathVariable("clientId") String clientId,
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

    @ApiOperation("单条存储多媒体数据检索上传命令")
    @PostMapping("{clientId}/mediadata_command")
    public T0001 mediaDataCommand(@PathVariable("clientId") String clientId,
                                  @ApiParam("多媒体ID") @RequestParam Integer id,
                                  @ApiParam("删除标志:0.保留；1.删除；") @RequestParam int delete) {
        T8805 request = new T8805(clientId, id, delete);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("数据下行透传")
    @PostMapping("{clientId}/passthrough")
    public T0001 passthrough(@PathVariable("clientId") String clientId, @RequestBody T8900_0900 request) {
        request.setHeader(new Header(clientId, JT808.数据下行透传));
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("平台RSA公钥")
    @PostMapping("{clientId}/rsa_swap")
    public T0A00_8A00 rsaSwap(@PathVariable("clientId") String clientId, @RequestBody T0A00_8A00 request) {
        request.setHeader(new Header(clientId, JT808.平台RSA公钥));
        T0A00_8A00 response = messageManager.request(request, T0A00_8A00.class);
        return response;
    }
}