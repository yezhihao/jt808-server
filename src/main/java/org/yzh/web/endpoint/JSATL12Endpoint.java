package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import io.github.yezhihao.netmc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.jsatl12.*;
import org.yzh.web.service.FileService;

import java.util.List;

@Endpoint
@Component
public class JSATL12Endpoint {

    @Autowired
    private FileService fileService;

    @Mapping(types = JSATL12.报警附件信息消息, desc = "报警附件信息消息")
    public void 报警附件信息消息(T1210 request, Session session) {
        List<T1210.Item> items = request.getItems();
        AlarmId alarmId = request.getAlarmId();
        for (T1210.Item item : items)
            session.setAttribute(item.getName(), alarmId);
        fileService.createDir(request);
    }

    @Mapping(types = JSATL12.文件信息上传, desc = "文件信息上传")
    public void 文件信息上传(T1211 request, Session session) {
        AlarmId alarmId = (AlarmId) session.getAttribute(request.getName());
        if (alarmId != null) {
            fileService.createFile(alarmId, request);
        } else {
            throw new RuntimeException();
        }
    }

    @Mapping(types = JSATL12.文件数据上传, desc = "文件数据上传")
    public Object 文件数据上传(DataPacket request, Session session) {
        AlarmId alarmId = (AlarmId) session.getAttribute(request.getName());
        if (alarmId != null)
            fileService.writeFile(alarmId, request);
        return null;
    }

    @Mapping(types = JSATL12.文件上传完成消息, desc = "文件上传完成消息")
    public T9212 文件上传完成消息(T1211 request, Session session) {
        AlarmId alarmId = (AlarmId) session.getAttribute(request.getName());
        T9212 result = new T9212();
        result.setName(request.getName());
        result.setType(request.getType());

        List<DataInfo> items = fileService.checkFile(alarmId, request);
        if (items.isEmpty()) {
            session.removeAttribute(request.getName());
            result.setResult(0);
        } else {
            result.setResult(1);
            result.setItems(items);
            result.setTotal(items.size());
        }
        return result;
    }
}