package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("{terminalId}/parameters")
    public T0001 updateParameters(@PathVariable("terminalId") String terminalId, @RequestBody List<TerminalParameter> parameters) {
        T8103 message = new T8103();
        message.setHeader(new Header(JT808.设置终端参数, terminalId));
        message.setItems(parameters);
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("查询终端参数/查询指定终端参数")
    @GetMapping("{terminalId}/parameters")
    public T0104 findParameters(@PathVariable("terminalId") String terminalId,
                                @ApiParam("参数ID列表，为空则查询全部") @RequestParam(required = false) byte[] idList) {
        T8106 body = new T8106();
        Header message;
        if (idList != null) {
            body.setIds(idList);
            message = new Header(JT808.查询指定终端参数, terminalId);
        } else {
            message = new Header(JT808.查询终端参数, terminalId);
        }
        T0104 response = messageManager.request(new RawMessage(message), T0104.class);
        return response;
    }

    @ApiOperation("终端控制")
    @PostMapping("{terminalId}/control/terminal")
    public T0001 terminalControl(@PathVariable("terminalId") String terminalId, @RequestBody T8105 message) {
        message.setHeader(new Header(JT808.终端控制, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("查询终端属性")
    @GetMapping("{terminalId}/attributes")
    public T0107 findAttributes(@PathVariable("terminalId") String terminalId) {
        T0107 response = messageManager.request(new RawMessage(new Header(JT808.查询终端属性, terminalId)), T0107.class);
        return response;
    }

    @ApiOperation("下发终端升级包")
    @PostMapping("{terminalId}/upgrade")
    public T0001 upgrade(@PathVariable("terminalId") String terminalId, @RequestBody T8108 message) {
        message.setHeader(new Header(JT808.下发终端升级包, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("位置信息查询")
    @GetMapping("{terminalId}/position")
    public T0201_0500 position(@PathVariable("terminalId") String terminalId) {
        RawMessage message = new RawMessage(new Header(JT808.位置信息查询, terminalId));
        T0201_0500 response = messageManager.request(message, T0201_0500.class);
        return response;
    }

    @ApiOperation("临时位置跟踪控制")
    @PostMapping("{terminalId}/track")
    public T0001 track(@PathVariable("terminalId") String terminalId,
                       @ApiParam("时间间隔（秒）") @RequestParam int interval,
                       @ApiParam("有效期（秒）") @RequestParam int validityPeriod) {
        T8202 request = new T8202(new Header(JT808.临时位置跟踪控制, terminalId), interval, validityPeriod);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("人工确认报警消息")
    @PostMapping("{terminalId}/alarm_ack")
    public T0001 人工确认报警消息(@PathVariable("terminalId") String terminalId, @RequestBody T8203 message) {
        message.setHeader(new Header(JT808.人工确认报警消息, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("服务器向终端发起链路检测请求")
    @PostMapping("{terminalId}/check_link")
    public T0001 checkLink(@PathVariable("terminalId") String terminalId) {
        RawMessage message = new RawMessage(new Header(JT808.服务器向终端发起链路检测请求, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("文本信息下发")
    @PostMapping("{terminalId}/text")
    public T0001 sendText(@PathVariable("terminalId") String terminalId, @RequestBody T8300 message) {
        message.setHeader(new Header(JT808.文本信息下发, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("事件设置")
    @PostMapping("{terminalId}/events")
    public T0001 eventSetting(@PathVariable("terminalId") String terminalId, @RequestBody T8301 message) {
        message.setHeader(new Header(JT808.事件设置, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("提问下发")
    @PostMapping("{terminalId}/question")
    public T0001 sendQuestion(@PathVariable("terminalId") String terminalId, @RequestBody T8302 message) {
        message.setHeader(new Header(JT808.提问下发, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("信息点播菜单设置")
    @PostMapping("{terminalId}/information/menu")
    public T0001 infoMenu(@PathVariable("terminalId") String terminalId, @RequestBody T8303 message) {
        message.setHeader(new Header(JT808.信息点播菜单设置, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("信息服务")
    @PostMapping("{terminalId}/information/push")
    public T0001 messageSubSetting(@PathVariable("terminalId") String terminalId,
                                   @ApiParam("类型") @RequestParam int type,
                                   @ApiParam("内容") @RequestParam String content) {
        T8304 request = new T8304(new Header(JT808.信息服务, terminalId), type);
        request.setContent(content);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("电话回拨")
    @PostMapping("{terminalId}/call_phone")
    public T0001 callPhone(@PathVariable("terminalId") String terminalId,
                           @ApiParam("类型（0.通话 1.监听）") @RequestParam int type,
                           @ApiParam("电话号码") @RequestParam String mobileNo) {
        T8400 message = new T8400(new Header(JT808.电话回拨, terminalId), type, mobileNo);
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置电话本")
    @PostMapping("{terminalId}/phone_book")
    public T0001 phoneBook(@PathVariable("terminalId") String terminalId, @RequestBody T8401 message) {
        message.setHeader(new Header(JT808.设置电话本, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("车辆控制")
    @PostMapping("{terminalId}/control/vehicle")
    public T0201_0500 vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody T8500 message) {
        message.setHeader(new Header(JT808.车辆控制, terminalId));
        T0201_0500 response = messageManager.request(message, T0201_0500.class);
        return response;
    }

    @ApiOperation("删除区域")
    @DeleteMapping("{terminalId}/map_fence")
    public T0001 removeMapFence(@PathVariable("terminalId") String terminalId,
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
        T8601 request = new T8601(new Header(messageId, terminalId));
        int[] ids = StrUtils.toInts(id, ",");
        for (int i : ids)
            request.addItem(i);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }

    @ApiOperation("设置圆形区域")
    @PostMapping("{terminalId}/map_fence_round")
    public T0001 addMapFenceRound(@PathVariable("terminalId") String terminalId, @RequestBody T8600 message) {
        message.setHeader(new Header(JT808.设置圆形区域, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置矩形区域")
    @PostMapping("{terminalId}/map_fence_rectangle")
    public T0001 addMapFenceRectangle(@PathVariable("terminalId") String terminalId, @RequestBody T8602 message) {
        message.setHeader(new Header(JT808.设置矩形区域, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置多边形区域")
    @PostMapping("{terminalId}/map_fence_polygon")
    public T0001 addMapFencePolygon(@PathVariable("terminalId") String terminalId, @RequestBody T8604 message) {
        message.setHeader(new Header(JT808.设置多边形区域, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("设置路线")
    @PostMapping("{terminalId}/route")
    public T0001 addRoute(@PathVariable("terminalId") String terminalId, @RequestBody T8606 message) {
        message.setHeader(new Header(JT808.设置路线, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("查询区域或线路数据")
    @PostMapping("{terminalId}/location/route")
    public T0608 locationRoute(@PathVariable("terminalId") String terminalId, @RequestBody T8608 message) {
        message.setHeader(new Header(JT808.查询区域或线路数据, terminalId));
        T0608 response = messageManager.request(message, T0608.class);
        return response;
    }

    @ApiOperation("行驶记录仪数据采集命令")
    @GetMapping("{terminalId}/data_record")
    public T0001 getDataRecord(@PathVariable("terminalId") String terminalId) {
        RawMessage message = new RawMessage(new Header(JT808.行驶记录仪数据采集命令, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("行驶记录仪参数下传命令")
    @GetMapping("{terminalId}/recorder")
    public T0001 recorder(@PathVariable("terminalId") String terminalId) {
        //TODO 2019
        RawMessage message = new RawMessage(new Header(JT808.行驶记录仪参数下传命令, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("上报驾驶员身份信息请求")
    @PostMapping("{terminalId}/driver_identity")
    public T0001 findDriverIdentityInfo(@PathVariable("terminalId") String terminalId) {
        T8106 message = new T8106();
        message.setHeader(new Header(JT808.上报驾驶员身份信息请求, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("摄像头立即拍摄命令")
    @PostMapping("{terminalId}/camera_shot")
    public T0805 cameraShot(@PathVariable("terminalId") String terminalId, @RequestBody T8804 message) {
        message.setHeader(new Header(JT808.摄像头立即拍摄命令, terminalId));
        T0805 response = messageManager.request(message, T0805.class);
        return response;
    }

    @ApiOperation("存储多媒体数据检索")
    @PostMapping("{terminalId}/mediadata_query")
    public T0802 mediaDataQuery(@PathVariable("terminalId") String terminalId, @RequestBody T8802 message) {
        message.setHeader(new Header(JT808.存储多媒体数据检索, terminalId));
        T0802 response = messageManager.request(message, T0802.class);
        return response;
    }

    @ApiOperation("存储多媒体数据上传")
    @PostMapping("{terminalId}/mediadata_report")
    public T0001 mediaDataReportRequest(@PathVariable("terminalId") String terminalId, @RequestBody T8803 message) {
        message.setHeader(new Header(JT808.存储多媒体数据上传, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("录音开始命令")
    @PostMapping("{terminalId}/sound_record")
    public T0001 soundRecord(@PathVariable("terminalId") String terminalId, @RequestBody T8804 message) {
        message.setHeader(new Header(JT808.录音开始命令, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("单条存储多媒体数据检索上传命令")
    @PostMapping("{terminalId}/mediadata_command")
    public T0001 mediaDataCommand(@PathVariable("terminalId") String terminalId, @RequestBody T8805 message) {
        message.setHeader(new Header(JT808.单条存储多媒体数据检索上传命令, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("数据下行透传")
    @PostMapping("{terminalId}/passthrough")
    public T0001 passthrough(@PathVariable("terminalId") String terminalId, @RequestBody T8900_0900 message) {
        message.setHeader(new Header(JT808.数据下行透传, terminalId));
        T0001 response = messageManager.request(message, T0001.class);
        return response;
    }

    @ApiOperation("平台RSA公钥")
    @PostMapping("{terminalId}/rsa_swap")
    public T0A00_8A00 rsaSwap(@PathVariable("terminalId") String terminalId, @RequestBody T0A00_8A00 message) {
        message.setHeader(new Header(JT808.平台RSA公钥, terminalId));
        T0A00_8A00 response = messageManager.request(message, T0A00_8A00.class);
        return response;
    }
}