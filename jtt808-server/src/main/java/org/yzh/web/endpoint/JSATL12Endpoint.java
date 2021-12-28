package org.yzh.web.endpoint;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import io.github.yezhihao.netmc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.jsatl12.*;
import org.yzh.web.service.FileService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Endpoint
@Component
public class JSATL12Endpoint {

    @Autowired
    private FileService fileService;

    private final Cache<String, Map<String, AlarmId>> cache = Caffeine.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    private AlarmId getAlarmId(String clientId, String filename) {
        Map<String, AlarmId> alarmIdMap = cache.getIfPresent(clientId);
        if (alarmIdMap != null)
            return alarmIdMap.get(filename);
        return null;
    }

    @Mapping(types = JSATL12.报警附件信息消息, desc = "报警附件信息消息")
    public void alarmFileInfoList(T1210 message, Session session) {
        session.register(message.getDeviceId(), message);

        List<T1210.Item> items = message.getItems();
        if (items == null)
            return;

        AlarmId alarmId = message.getAlarmId();

        if (StrUtils.isBlank(alarmId.getDeviceId()))
            alarmId.setDeviceId(message.getDeviceId());

        if (StrUtils.isBlank(alarmId.getDeviceId()))
            alarmId.setDeviceId(message.getClientId());

        Map<String, AlarmId> alarmIdMap = cache.get(message.getClientId(), s -> new TreeMap<>());

        for (T1210.Item item : items)
            alarmIdMap.put(item.getName(), alarmId);
        fileService.createDir(message);
    }

    @Mapping(types = JSATL12.文件信息上传, desc = "文件信息上传")
    public void alarmFileInfo(T1211 message, Session session) {
        if (!session.isRegistered())
            session.register(message);

        AlarmId alarmId = getAlarmId(message.getClientId(), message.getName());
        if (alarmId == null)
            throw new RuntimeException("alarmId not found");
        fileService.createFile(alarmId, message);
    }

    @Mapping(types = JSATL12.文件数据上传, desc = "文件数据上传")
    public Object alarmFile(DataPacket message, Session session) {
        AlarmId alarmId = getAlarmId(session.getClientId(), message.getName().trim());
        if (alarmId != null)
            fileService.writeFile(alarmId, message);
        return null;
    }

    @Mapping(types = JSATL12.文件上传完成消息, desc = "文件上传完成消息")
    public T9212 alarmFileComplete(T1211 message) {
        Map<String, AlarmId> alarmIdMap = cache.getIfPresent(message.getClientId());
        AlarmId alarmId = alarmIdMap.get(message.getName());
        T9212 result = new T9212();
        result.setName(message.getName());
        result.setType(message.getType());

        int[] items = fileService.checkFile(alarmId, message);
        if (items == null) {
            alarmIdMap.remove(message.getName());
            result.setResult(0);
        } else {
            result.setItems(items);
            result.setResult(1);
        }
        return result;
    }
}