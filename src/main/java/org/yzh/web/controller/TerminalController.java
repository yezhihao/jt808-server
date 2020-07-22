package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yzh.web.endpoint.JT808Endpoint;
import org.yzh.web.jt808.common.JT808;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Message;
import org.yzh.web.jt808.dto.basics.TerminalParameter;

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
    public T0001 updateParameters(@PathVariable("terminalId") String terminalId, @RequestBody List<TerminalParameter> parameters) {
        T8103 body = new T8103();
        body.setParameters(parameters);
        Message message = new Message(JT808.设置终端参数, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "查询终端参数/查询指定终端参数")
    @RequestMapping(value = "{terminalId}/parameters", method = RequestMethod.GET)
    @ResponseBody
    public T0104 findParameters(@PathVariable("terminalId") String terminalId,
                                @ApiParam("参数ID列表，为空则查询全部") @RequestParam(required = false) byte[] idList) {
        T8106 body = new T8106();
        Message message;
        if (idList != null) {
            body.setIds(idList);
            message = new Message(JT808.查询指定终端参数, terminalId, body);

        } else {
            message = new Message(JT808.查询终端参数, terminalId, body);
        }

        T0104 response = (T0104) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "查询终端属性")
    @RequestMapping(value = "{terminalId}/attributes", method = RequestMethod.GET)
    @ResponseBody
    public T0107 findAttributes(@PathVariable("terminalId") String terminalId) {
        T8106 body = new T8106();
        Message message = new Message(JT808.查询终端属性, terminalId, body);
        T0107 response = (T0107) endpoint.send(message, false);
        return response;
    }

    @ApiOperation(value = "终端控制")
    @RequestMapping(value = "{terminalId}/control/terminal", method = RequestMethod.POST)
    @ResponseBody
    public T0001 terminalControl(@PathVariable("terminalId") String terminalId, @RequestBody T8105 body) {
        Message message = new Message(JT808.终端控制, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "下发终端升级包")
    @RequestMapping(value = "{terminalId}/upgrade", method = RequestMethod.POST)
    @ResponseBody
    public T0001 vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody T8108 body) {
        Message message = new Message(JT808.下发终端升级包, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "位置信息查询")
    @RequestMapping(value = "{terminalId}/position", method = RequestMethod.GET)
    @ResponseBody
    public T0201_0500 position(@PathVariable("terminalId") String terminalId) {
        Message message = new Message(JT808.位置信息查询, terminalId, null);
        T0201_0500 response = (T0201_0500) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "临时位置跟踪控制")
    @RequestMapping(value = "{terminalId}/track", method = RequestMethod.POST)
    @ResponseBody
    public T0001 track(@PathVariable("terminalId") String terminalId, @RequestBody T8202 body) {
        Message message = new Message(JT808.临时位置跟踪控制, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "文本信息下发")
    @RequestMapping(value = "{terminalId}/text", method = RequestMethod.POST)
    @ResponseBody
    public T0001 sendText(@PathVariable("terminalId") String terminalId, @RequestBody T8300 body) {
        Message message = new Message(JT808.文本信息下发, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "事件设置")
    @RequestMapping(value = "{terminalId}/events", method = RequestMethod.POST)
    @ResponseBody
    public T0001 eventSetting(@PathVariable("terminalId") String terminalId, @RequestBody T8301 body) {
        Message message = new Message(JT808.事件设置, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "提问下发")
    @RequestMapping(value = "{terminalId}/question", method = RequestMethod.POST)
    @ResponseBody
    public T0001 sendQuestion(@PathVariable("terminalId") String terminalId, @RequestBody T8302 body) {
        Message message = new Message(JT808.提问下发, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "信息点播菜单设置")
    @RequestMapping(value = "{terminalId}/information/menu", method = RequestMethod.POST)
    @ResponseBody
    public T0001 infoMenu(@PathVariable("terminalId") String terminalId, @RequestBody T8303 body) {
        Message message = new Message(JT808.信息点播菜单设置, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "信息服务")
    @RequestMapping(value = "{terminalId}/information/push", method = RequestMethod.POST)
    @ResponseBody
    public T0001 messageSubSetting(@PathVariable("terminalId") String terminalId, @RequestBody T8304 body) {
        Message message = new Message(JT808.信息服务, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "电话回拨")
    @RequestMapping(value = "{terminalId}/call_phone", method = RequestMethod.POST)
    @ResponseBody
    public T0001 callPhone(@PathVariable("terminalId") String terminalId, @RequestBody T8400 body) {
        Message message = new Message(JT808.电话回拨, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置电话本")
    @RequestMapping(value = "{terminalId}/phone_book", method = RequestMethod.POST)
    @ResponseBody
    public T0001 phoneBook(@PathVariable("terminalId") String terminalId, @RequestBody T8401 body) {
        Message message = new Message(JT808.设置电话本, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "车辆控制")
    @RequestMapping(value = "{terminalId}/control/vehicle", method = RequestMethod.POST)
    @ResponseBody
    public T0201_0500 vehicleControl(@PathVariable("terminalId") String terminalId, @RequestBody T8500 body) {
        Message message = new Message(JT808.车辆控制, terminalId, body);
        T0201_0500 response = (T0201_0500) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "删除区域")
    @RequestMapping(value = "{terminalId}/map_fence/remove/{type}", method = RequestMethod.POST)
    @ResponseBody
    public T0001 removeMapFence(@PathVariable("terminalId") String terminalId,
                                @ApiParam("区域类型:1.圆形 2.矩形 3.多边形") @PathVariable("type") int type,
                                @ApiParam("区域ID列表") @RequestBody Integer[] idList) {
        Message message;
        T8601 body = new T8601();
        switch (type) {
            case 1:
                message = new Message(JT808.删除圆形区域, terminalId, body);
                break;
            case 2:
                message = new Message(JT808.删除矩形区域, terminalId, body);
                break;
            case 3:
                message = new Message(JT808.删除多边形区域, terminalId, body);
                break;
            default:
                return null;
        }
        for (int id : idList)
            body.addItem(id);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置圆形区域")
    @RequestMapping(value = "{terminalId}/map_fence_round", method = RequestMethod.POST)
    @ResponseBody
    public T0001 addMapFenceRound(@PathVariable("terminalId") String terminalId, @RequestBody T8600 body) {
        Message message = new Message(JT808.设置圆形区域, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置矩形区域")
    @RequestMapping(value = "{terminalId}/map_fence_rectangle", method = RequestMethod.POST)
    @ResponseBody
    public T0001 addMapFenceRectangle(@PathVariable("terminalId") String terminalId, @RequestBody T8602 body) {
        Message message = new Message(JT808.设置矩形区域, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置多边形区域")
    @RequestMapping(value = "{terminalId}/map_fence_polygon", method = RequestMethod.POST)
    @ResponseBody
    public T0001 addMapFencePolygon(@PathVariable("terminalId") String terminalId, @RequestBody T8604 body) {
        Message message = new Message(JT808.设置多边形区域, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "设置路线")
    @RequestMapping(value = "{terminalId}/route", method = RequestMethod.POST)
    @ResponseBody
    public T0001 addRoute(@PathVariable("terminalId") String terminalId, @RequestBody T8606 body) {
        Message message = new Message(JT808.设置路线, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "删除路线")
    @RequestMapping(value = "{terminalId}/route/remove", method = RequestMethod.POST)
    @ResponseBody
    public T0001 removeRoute(@PathVariable("terminalId") String terminalId, @ApiParam("区域ID列表") @RequestBody int[] idList) {
        T8606 body = new T8606();
        for (int id : idList)
            body.addPoint(id);
        Message message = new Message(JT808.删除路线, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "上报驾驶员身份信息请求")
    @RequestMapping(value = "{terminalId}/driver_identity", method = RequestMethod.POST)
    @ResponseBody
    public T0001 findDriverIdentityInfo(@PathVariable("terminalId") String terminalId) {
        T8106 body = new T8106();
        Message message = new Message(JT808.上报驾驶员身份信息请求, terminalId, body);
        T0001 response = (T0001) endpoint.send(message, false);
        return response;
    }

    @ApiOperation(value = "摄像头立即拍摄命令")
    @RequestMapping(value = "{terminalId}/camera_shot", method = RequestMethod.POST)
    @ResponseBody
    public T0805 cameraShot(@PathVariable("terminalId") String terminalId, @RequestBody T8804 body) {
        Message message = new Message(JT808.摄像头立即拍摄命令, terminalId, body);
        T0805 response = (T0805) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据检索")
    @RequestMapping(value = "{terminalId}/mediadata_query", method = RequestMethod.POST)
    @ResponseBody
    public T0802 mediaDataQuery(@PathVariable("terminalId") String terminalId, @RequestBody T8802 body) {
        Message message = new Message(JT808.存储多媒体数据检索, terminalId, body);
        T0802 response = (T0802) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "存储多媒体数据上传命令")
    @RequestMapping(value = "{terminalId}/mediadata_report", method = RequestMethod.POST)
    @ResponseBody
    public T0001 mediaDataReportRequest(@PathVariable("terminalId") String terminalId, @RequestBody T8803 body) {
        Message message = new Message(JT808.存储多媒体数据上传命令, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "录音开始命令")
    @RequestMapping(value = "{terminalId}/sound_record", method = RequestMethod.POST)
    @ResponseBody
    public T0001 soundRecord(@PathVariable("terminalId") String terminalId, @RequestBody T8804 body) {
        Message message = new Message(JT808.录音开始命令, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "单条存储多媒体数据检索上传命令")
    @RequestMapping(value = "{terminalId}/mediadata_command", method = RequestMethod.POST)
    @ResponseBody
    public T0001 mediaDataCommand(@PathVariable("terminalId") String terminalId, @RequestBody T8805 body) {
        Message message = new Message(JT808.单条存储多媒体数据检索上传命令, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "数据下行透传")
    @RequestMapping(value = "{terminalId}/passthrough", method = RequestMethod.POST)
    @ResponseBody
    public T0001 passthrough(@PathVariable("terminalId") String terminalId, @RequestBody T8900_0900 body) {
        Message message = new Message(JT808.数据下行透传, terminalId, body);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }

    @ApiOperation(value = "平台RSA公钥")
    @RequestMapping(value = "{terminalId}/rsa_swap", method = RequestMethod.POST)
    @ResponseBody
    public T0A00_8A00 rsaSwap(@PathVariable("terminalId") String terminalId, @RequestBody T0A00_8A00 body) {
        Message message = new Message(JT808.平台RSA公钥, terminalId, body);
        T0A00_8A00 response = (T0A00_8A00) endpoint.send(message, false);
        return response;
    }

    @ApiOperation(value = "行驶记录仪数据采集命令")
    @RequestMapping(value = "{terminalId}/data_record", method = RequestMethod.GET)
    @ResponseBody
    public T0001 getDataRecord(@PathVariable("terminalId") String terminalId) {
        Message message = new Message(JT808.行驶记录仪数据采集命令, terminalId, null);
        T0001 response = (T0001) endpoint.send(message);
        return response;
    }
}