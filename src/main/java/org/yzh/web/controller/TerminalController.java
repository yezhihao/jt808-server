package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yzh.framework.message.PackageData;
import org.yzh.web.endpoint.JT808Endpoint;
import org.yzh.web.jt808.common.JT808MessageCode;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Header;

import java.util.ArrayList;
import java.util.List;

@Api(description = "test")
@Controller
@RequestMapping
public class TerminalController {

    @Autowired
    private JT808Endpoint endpoint;

    @ApiOperation(value = "设置终端参数")
    @RequestMapping(value = "{terminalId}/parameters", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateParameters(@PathVariable("terminalId") String terminalId, @RequestBody List<TerminalParameter> parameters) {
        ParameterSetting body = new ParameterSetting();
        body.setParameters(parameters);
        body.setHeader(new Header(JT808MessageCode.设置终端参数, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "查询终端参数/查询指定终端参数")
    @RequestMapping(value = "{terminalId}/parameters", method = RequestMethod.GET)
    @ResponseBody
    public ParameterSettingReply findParameters(@PathVariable("terminalId") String terminalId,
                                                @ApiParam("参数ID列表，为空则查询全部") @RequestParam(required = false) int[] idList) {
        ParameterSetting body = new ParameterSetting();
        if (idList != null) {
            List<TerminalParameter> list = new ArrayList(idList.length);
            for (int id : idList)
                list.add(new TerminalParameter(id));
            body.setParameters(list);
            body.setHeader(new Header(JT808MessageCode.查询指定终端参数, terminalId));

        } else {
            body.setHeader(new Header(JT808MessageCode.查询终端参数, terminalId));
        }

        ParameterSettingReply response = (ParameterSettingReply) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "查询终端属性")
    @RequestMapping(value = "{terminalId}/attributes", method = RequestMethod.GET)
    @ResponseBody
    public TerminalAttributeReply findAttributes(@PathVariable("terminalId") String terminalId) {
        PackageData body = new ParameterSetting();
        body.setHeader(new Header(JT808MessageCode.查询终端属性, terminalId));
        TerminalAttributeReply response = (TerminalAttributeReply) endpoint.send(body, false);
        return response;
    }

    @ApiOperation(value = "终端控制")
    @RequestMapping(value = "{terminalId}/control/terminal", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult terminalControl(@PathVariable("terminalId") String terminalId, @RequestBody TerminalControl body) {
        body.setHeader(new Header(JT808MessageCode.终端控制, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "下发终端升级包")
    @RequestMapping(value = "{terminalId}/upgrade", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody TerminalUpgradePack body) {
        body.setHeader(new Header(JT808MessageCode.下发终端升级包, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "位置信息查询")
    @RequestMapping(value = "{terminalId}/position", method = RequestMethod.GET)
    @ResponseBody
    public PositionReply position(@PathVariable("terminalId") String terminalId) {
        PackageData body = new PackageData();
        body.setHeader(new Header(JT808MessageCode.位置信息查询, terminalId));
        PositionReply response = (PositionReply) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "临时位置跟踪控制")
    @RequestMapping(value = "{terminalId}/track", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult track(@PathVariable("terminalId") String terminalId, @RequestBody TemporaryMonitor body) {
        body.setHeader(new Header(JT808MessageCode.临时位置跟踪控制, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "文本信息下发")
    @RequestMapping(value = "{terminalId}/text", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult sendText(@PathVariable("terminalId") String terminalId, @RequestBody TextMessage body) {
        body.setHeader(new Header(JT808MessageCode.文本信息下发, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "事件设置")
    @RequestMapping(value = "{terminalId}/events", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult eventSetting(@PathVariable("terminalId") String terminalId, @RequestBody EventSetting body) {
        body.setHeader(new Header(JT808MessageCode.事件设置, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "提问下发")
    @RequestMapping(value = "{terminalId}/question", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult sendQuestion(@PathVariable("terminalId") String terminalId, @RequestBody QuestionMessage body) {
        body.setHeader(new Header(JT808MessageCode.提问下发, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "信息点播菜单设置")
    @RequestMapping(value = "{terminalId}/information/menu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult infoMenu(@PathVariable("terminalId") String terminalId, @RequestBody MessageSubSetting body) {
        body.setHeader(new Header(JT808MessageCode.信息点播菜单设置, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "信息服务")
    @RequestMapping(value = "{terminalId}/information/push", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult messageSubSetting(@PathVariable("terminalId") String terminalId, @RequestBody Information body) {
        body.setHeader(new Header(JT808MessageCode.信息服务, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "电话回拨")
    @RequestMapping(value = "{terminalId}/callPhone", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult callPhone(@PathVariable("terminalId") String terminalId, @RequestBody CallPhone body) {
        body.setHeader(new Header(JT808MessageCode.电话回拨, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "设置电话本")
    @RequestMapping(value = "{terminalId}/phoneBook", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult phoneBook(@PathVariable("terminalId") String terminalId, @RequestBody PhoneBook body) {
        body.setHeader(new Header(JT808MessageCode.设置电话本, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "车辆控制")
    @RequestMapping(value = "{terminalId}/control/vehicle", method = RequestMethod.POST)
    @ResponseBody
    public PositionReply vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody VehicleControl body) {
        body.setHeader(new Header(JT808MessageCode.车辆控制, terminalId));
        PositionReply response = (PositionReply) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "删除区域")
    @RequestMapping(value = "{terminalId}/mapFence/remove/{type}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult removeMapFence(@PathVariable("terminalId") String terminalId,
                                       @ApiParam("区域类型:1.圆形 2.矩形 3.多边形") @PathVariable("type") int type,
                                       @ApiParam("区域ID列表") @RequestBody Integer[] idList) {
        MapFenceSetting body = new MapFenceSetting();
        switch (type) {
            case 1:
                body.setHeader(new Header(JT808MessageCode.删除圆形区域, terminalId));
                break;
            case 2:
                body.setHeader(new Header(JT808MessageCode.删除矩形区域, terminalId));
                break;
            case 3:
                body.setHeader(new Header(JT808MessageCode.删除多边形区域, terminalId));
                break;
            default:
                return null;
        }
        for (int id : idList)
            body.addMapFence(id);
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "设置圆形区域")
    @RequestMapping(value = "{terminalId}/mapFenceRound", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addMapFenceRound(@PathVariable("terminalId") String terminalId, @RequestBody MapFenceSetting<MapFenceRound> body) {
        body.setHeader(new Header(JT808MessageCode.设置圆形区域, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "设置矩形区域")
    @RequestMapping(value = "{terminalId}/mapFenceRectangle", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addMapFenceRectangle(@PathVariable("terminalId") String terminalId, @RequestBody MapFenceSetting<MapFenceRectangle> body) {
        body.setHeader(new Header(JT808MessageCode.设置矩形区域, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "设置多边形区域")
    @RequestMapping(value = "{terminalId}/mapFencePolygon", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addMapFencePolygon(@PathVariable("terminalId") String terminalId, @RequestBody MapFenceSetting<MapFencePolygon> body) {
        body.setHeader(new Header(JT808MessageCode.设置多边形区域, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "设置路线")
    @RequestMapping(value = "{terminalId}/route", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addRoute(@PathVariable("terminalId") String terminalId, @RequestBody RouteSetting body) {
        body.setHeader(new Header(JT808MessageCode.设置路线, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "删除路线")
    @RequestMapping(value = "{terminalId}/route/remove", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult removeRoute(@PathVariable("terminalId") String terminalId, @ApiParam("区域ID列表") @RequestBody int[] idList) {
        RouteSetting body = new RouteSetting();
        for (int id : idList)
            body.addPoint(id);
        body.setHeader(new Header(JT808MessageCode.删除路线, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "上报驾驶员身份信息请求")
    @RequestMapping(value = "{terminalId}/driverIdentity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult findDriverIdentityInfo(@PathVariable("terminalId") String terminalId) {
        PackageData body = new ParameterSetting();
        body.setHeader(new Header(JT808MessageCode.上报驾驶员身份信息请求, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body, false);
        return response;
    }

    @ApiOperation(value = "摄像头立即拍摄命令")
    @RequestMapping(value = "{terminalId}/cameraShot", method = RequestMethod.POST)
    @ResponseBody
    public CameraShotReply cameraShot(@PathVariable("terminalId") String terminalId, @RequestBody CameraShot body) {
        body.setHeader(new Header(JT808MessageCode.摄像头立即拍摄命令, terminalId));
        CameraShotReply response = (CameraShotReply) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据检索")
    @RequestMapping(value = "{terminalId}/mediaDataQuery", method = RequestMethod.POST)
    @ResponseBody
    public MediaDataQueryReply mediaDataQuery(@PathVariable("terminalId") String terminalId, @RequestBody MediaDataQuery body) {
        body.setHeader(new Header(JT808MessageCode.存储多媒体数据检索, terminalId));
        MediaDataQueryReply response = (MediaDataQueryReply) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据上传命令")
    @RequestMapping(value = "{terminalId}/mediaDataReportRequest", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult mediaDataReportRequest(@PathVariable("terminalId") String terminalId, @RequestBody MediaDataReportRequest body) {
        body.setHeader(new Header(JT808MessageCode.存储多媒体数据上传命令, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "录音开始命令")
    @RequestMapping(value = "{terminalId}/soundRecord", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult soundRecord(@PathVariable("terminalId") String terminalId, @RequestBody SoundRecord body) {
        body.setHeader(new Header(JT808MessageCode.录音开始命令, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "单条存储多媒体数据检索上传命令")
    @RequestMapping(value = "{terminalId}/mediaDataCommand", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult mediaDataCommand(@PathVariable("terminalId") String terminalId, @RequestBody MediaDataCommand body) {
        body.setHeader(new Header(JT808MessageCode.单条存储多媒体数据检索上传命令, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "数据下行透传")
    @RequestMapping(value = "{terminalId}/passthrough", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult passthrough(@PathVariable("terminalId") String terminalId, @RequestBody PassthroughPack body) {
        body.setHeader(new Header(JT808MessageCode.数据下行透传, terminalId));
        CommonResult response = (CommonResult) endpoint.send(body);
        return response;
    }

    @ApiOperation(value = "平台RSA公钥")
    @RequestMapping(value = "{terminalId}/rsaSwap", method = RequestMethod.POST)
    @ResponseBody
    public RSAPack rsaSwap(@PathVariable("terminalId") String terminalId, @RequestBody RSAPack body) {
        body.setHeader(new Header(JT808MessageCode.平台RSA公钥, terminalId));
        RSAPack response = (RSAPack) endpoint.send(body, false);
        return response;
    }
}