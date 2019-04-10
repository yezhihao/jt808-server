package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yzh.web.endpoint.JT808Endpoint;
import org.yzh.web.jt808.common.MessageId;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.*;

import java.util.ArrayList;
import java.util.List;

@Api(description = "terminal api")
@Controller
@RequestMapping("terminal")
public class TerminalController {

    @Autowired
    private JT808Endpoint endpoint;

    @ApiOperation(value = "设置终端参数")
    @RequestMapping(value = "{terminalId}/parameters", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateParameters(@PathVariable("terminalId") String terminalId, @RequestBody List<TerminalParameter> parameters) {
        ParameterSetting body = new ParameterSetting();
        body.setParameters(parameters);
        Message message = new Message(MessageId.设置终端参数, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "查询终端参数/查询指定终端参数")
    @RequestMapping(value = "{terminalId}/parameters", method = RequestMethod.GET)
    @ResponseBody
    public ParameterSettingReply findParameters(@PathVariable("terminalId") String terminalId,
                                                @ApiParam("参数ID列表，为空则查询全部") @RequestParam(required = false) int[] idList) {
        ParameterSetting body = new ParameterSetting();
        Message message;
        if (idList != null) {
            List<TerminalParameter> list = new ArrayList(idList.length);
            for (int id : idList)
                list.add(new TerminalParameter(id));
            body.setParameters(list);
            message = new Message(MessageId.查询指定终端参数, terminalId, body);

        } else {
            message = new Message(MessageId.查询终端参数, terminalId, body);
        }

        ParameterSettingReply response = (ParameterSettingReply) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "查询终端属性")
    @RequestMapping(value = "{terminalId}/attributes", method = RequestMethod.GET)
    @ResponseBody
    public TerminalAttributeReply findAttributes(@PathVariable("terminalId") String terminalId) {
        ParameterSetting body = new ParameterSetting();
        Message message = new Message(MessageId.查询终端属性, terminalId, body);
        TerminalAttributeReply response = (TerminalAttributeReply) endpoint.send(message, false);
        return response;
    }

    @ApiOperation(value = "终端控制")
    @RequestMapping(value = "{terminalId}/control/terminal", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult terminalControl(@PathVariable("terminalId") String terminalId, @RequestBody TerminalControl body) {
        Message message = new Message(MessageId.终端控制, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "下发终端升级包")
    @RequestMapping(value = "{terminalId}/upgrade", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody TerminalUpgradePack body) {
        Message message = new Message(MessageId.下发终端升级包, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "位置信息查询")
    @RequestMapping(value = "{terminalId}/position", method = RequestMethod.GET)
    @ResponseBody
    public PositionReply position(@PathVariable("terminalId") String terminalId) {
        Message message = new Message(MessageId.位置信息查询, terminalId, null);
        PositionReply response = (PositionReply) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "临时位置跟踪控制")
    @RequestMapping(value = "{terminalId}/track", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult track(@PathVariable("terminalId") String terminalId, @RequestBody TemporaryMonitor body) {
        Message message = new Message(MessageId.临时位置跟踪控制, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "文本信息下发")
    @RequestMapping(value = "{terminalId}/text", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult sendText(@PathVariable("terminalId") String terminalId, @RequestBody TextMessage body) {
        Message message = new Message(MessageId.文本信息下发, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "事件设置")
    @RequestMapping(value = "{terminalId}/events", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult eventSetting(@PathVariable("terminalId") String terminalId, @RequestBody EventSetting body) {
        Message message = new Message(MessageId.事件设置, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "提问下发")
    @RequestMapping(value = "{terminalId}/question", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult sendQuestion(@PathVariable("terminalId") String terminalId, @RequestBody QuestionMessage body) {
        Message message = new Message(MessageId.提问下发, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "信息点播菜单设置")
    @RequestMapping(value = "{terminalId}/information/menu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult infoMenu(@PathVariable("terminalId") String terminalId, @RequestBody MessageSubSetting body) {
        Message message = new Message(MessageId.信息点播菜单设置, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "信息服务")
    @RequestMapping(value = "{terminalId}/information/push", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult messageSubSetting(@PathVariable("terminalId") String terminalId, @RequestBody Information body) {
        Message message = new Message(MessageId.信息服务, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "电话回拨")
    @RequestMapping(value = "{terminalId}/call_phone", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult callPhone(@PathVariable("terminalId") String terminalId, @RequestBody CallPhone body) {
        Message message = new Message(MessageId.电话回拨, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置电话本")
    @RequestMapping(value = "{terminalId}/phone_book", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult phoneBook(@PathVariable("terminalId") String terminalId, @RequestBody PhoneBook body) {
        Message message = new Message(MessageId.设置电话本, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "车辆控制")
    @RequestMapping(value = "{terminalId}/control/vehicle", method = RequestMethod.POST)
    @ResponseBody
    public PositionReply vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody VehicleControl body) {
        Message message = new Message(MessageId.车辆控制, terminalId, body);
        PositionReply response = (PositionReply) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "删除区域")
    @RequestMapping(value = "{terminalId}/map_fence/remove/{type}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult removeMapFence(@PathVariable("terminalId") String terminalId,
                                       @ApiParam("区域类型:1.圆形 2.矩形 3.多边形") @PathVariable("type") int type,
                                       @ApiParam("区域ID列表") @RequestBody Integer[] idList) {
        Message message;
        MapFenceSetting body = new MapFenceSetting();
        switch (type) {
            case 1:
                message = new Message(MessageId.删除圆形区域, terminalId, body);
                break;
            case 2:
                message = new Message(MessageId.删除矩形区域, terminalId, body);
                break;
            case 3:
                message = new Message(MessageId.删除多边形区域, terminalId, body);
                break;
            default:
                return null;
        }
        for (int id : idList)
            body.addMapFence(id);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置圆形区域")
    @RequestMapping(value = "{terminalId}/map_fence_round", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addMapFenceRound(@PathVariable("terminalId") String terminalId, @RequestBody MapFenceSetting<MapFenceRound> body) {
        Message message = new Message(MessageId.设置圆形区域, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置矩形区域")
    @RequestMapping(value = "{terminalId}/map_fence_rectangle", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addMapFenceRectangle(@PathVariable("terminalId") String terminalId, @RequestBody MapFenceSetting<MapFenceRectangle> body) {
        Message message = new Message(MessageId.设置矩形区域, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置多边形区域")
    @RequestMapping(value = "{terminalId}/map_fence_polygon", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addMapFencePolygon(@PathVariable("terminalId") String terminalId, @RequestBody MapFenceSetting<MapFencePolygon> body) {
        Message message = new Message(MessageId.设置多边形区域, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置路线")
    @RequestMapping(value = "{terminalId}/route", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addRoute(@PathVariable("terminalId") String terminalId, @RequestBody RouteSetting body) {
        Message message = new Message(MessageId.设置路线, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "删除路线")
    @RequestMapping(value = "{terminalId}/route/remove", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult removeRoute(@PathVariable("terminalId") String terminalId, @ApiParam("区域ID列表") @RequestBody int[] idList) {
        RouteSetting body = new RouteSetting();
        for (int id : idList)
            body.addPoint(id);
        Message message = new Message(MessageId.删除路线, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "上报驾驶员身份信息请求")
    @RequestMapping(value = "{terminalId}/driver_identity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult findDriverIdentityInfo(@PathVariable("terminalId") String terminalId) {
        ParameterSetting body = new ParameterSetting();
        Message message = new Message(MessageId.上报驾驶员身份信息请求, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message, false);
        return response;
    }

    @ApiOperation(value = "摄像头立即拍摄命令")
    @RequestMapping(value = "{terminalId}/camera_shot", method = RequestMethod.POST)
    @ResponseBody
    public CameraShotReply cameraShot(@PathVariable("terminalId") String terminalId, @RequestBody CameraShot body) {
        Message message = new Message(MessageId.摄像头立即拍摄命令, terminalId, body);
        CameraShotReply response = (CameraShotReply) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据检索")
    @RequestMapping(value = "{terminalId}/mediadata_query", method = RequestMethod.POST)
    @ResponseBody
    public MediaDataQueryReply mediaDataQuery(@PathVariable("terminalId") String terminalId, @RequestBody MediaDataQuery body) {
        Message message = new Message(MessageId.存储多媒体数据检索, terminalId, body);
        MediaDataQueryReply response = (MediaDataQueryReply) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据上传命令")
    @RequestMapping(value = "{terminalId}/mediadata_report", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult mediaDataReportRequest(@PathVariable("terminalId") String terminalId, @RequestBody MediaDataReportRequest body) {
        Message message = new Message(MessageId.存储多媒体数据上传命令, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "录音开始命令")
    @RequestMapping(value = "{terminalId}/sound_record", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult soundRecord(@PathVariable("terminalId") String terminalId, @RequestBody SoundRecord body) {
        Message message = new Message(MessageId.录音开始命令, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "单条存储多媒体数据检索上传命令")
    @RequestMapping(value = "{terminalId}/mediadata_command", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult mediaDataCommand(@PathVariable("terminalId") String terminalId, @RequestBody MediaDataCommand body) {
        Message message = new Message(MessageId.单条存储多媒体数据检索上传命令, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "数据下行透传")
    @RequestMapping(value = "{terminalId}/passthrough", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult passthrough(@PathVariable("terminalId") String terminalId, @RequestBody PassthroughPack body) {
        Message message = new Message(MessageId.数据下行透传, terminalId, body);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "平台RSA公钥")
    @RequestMapping(value = "{terminalId}/rsa_swap", method = RequestMethod.POST)
    @ResponseBody
    public RSAPack rsaSwap(@PathVariable("terminalId") String terminalId, @RequestBody RSAPack body) {
        Message message = new Message(MessageId.平台RSA公钥, terminalId, body);
        RSAPack response = (RSAPack) endpoint.send(message, false);
        return response;
    }

    @ApiOperation(value = "行驶记录仪数据采集命令")
    @RequestMapping(value = "{terminalId}/data_record", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getDataRecord(@PathVariable("terminalId") String terminalId) {
        Message message = new Message(MessageId.行驶记录仪数据采集命令, terminalId, null);
        CommonResult response = (CommonResult) endpoint.send(message);
        return response;
    }
}