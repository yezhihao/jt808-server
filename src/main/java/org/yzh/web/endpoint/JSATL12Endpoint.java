package org.yzh.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.Session;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.jsatl12.*;
import org.yzh.web.service.FileService;

import java.util.List;

@Endpoint
@Component
public class JSATL12Endpoint {

    private static final String AlarmId = "AlarmId";

    @Autowired
    private FileService fileService;

    @Mapping(types = JSATL12.报警附件信息消息, desc = "报警附件信息消息")
    public void 报警附件信息消息(T1210 request, Session session) {
        session.setAttribute(AlarmId, request.getAlarmId());
        fileService.createDir(request);
    }

    @Mapping(types = JSATL12.文件信息上传, desc = "文件信息上传")
    public void 文件信息上传(T1211 request, Session session) {
        AlarmId alarmId = (AlarmId) session.getAttribute(AlarmId);
        if (alarmId != null) {
            fileService.createFile(alarmId, request);
        } else {
            throw new RuntimeException();
        }
    }

    @Mapping(types = JSATL12.文件数据上传, desc = "文件数据上传")
    public Object 文件数据上传(DataPacket message, Session session) {
        AlarmId alarmId = (AlarmId) session.getAttribute(AlarmId);
        if (alarmId != null)
            fileService.writeFile(alarmId, message);
        return null;
    }

    @Mapping(types = JSATL12.文件上传完成消息, desc = "文件上传完成消息")
    public T9212 文件上传完成消息(T1211 message, Session session) {
        AlarmId alarmId = (AlarmId) session.getAttribute(AlarmId);
        T9212 result = new T9212(session.nextSerialNo(), message.getHeader().getMobileNo());
        result.setName(message.getName());
        result.setType(message.getType());

        List<DataInfo> items = fileService.checkFile(alarmId, message);
        if (items.isEmpty()) {
            session.removeAttribute(AlarmId);
            result.setResult(0);
        } else {
            result.setResult(1);
            result.setItems(items);
            result.setTotal(items.size());
        }
        return result;
    }
}