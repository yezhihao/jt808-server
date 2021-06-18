package org.yzh.web.endpoint;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    private Cache<String, Map<String, AlarmId>> cache = Caffeine.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    private AlarmId getAlarmId(String clientId, String filename) {
        Map<String, AlarmId> alarmIdMap = cache.getIfPresent(clientId);
        if (alarmIdMap != null)
            return alarmIdMap.get(filename);
        return null;
    }

    @Mapping(types = JSATL12.报警附件信息消息, desc = "报警附件信息消息")
    public void 报警附件信息消息(T1210 request) {
        List<T1210.Item> items = request.getItems();
        AlarmId alarmId = request.getAlarmId();
        Map<String, AlarmId> alarmIdMap = cache.get(request.getClientId(), s -> new TreeMap<>());
        for (T1210.Item item : items)
            alarmIdMap.put(item.getName(), alarmId);
        fileService.createDir(request);
    }

    @Mapping(types = JSATL12.文件信息上传, desc = "文件信息上传")
    public void 文件信息上传(T1211 request) {
        AlarmId alarmId = getAlarmId(request.getClientId(), request.getName());
        if (alarmId == null)
            throw new RuntimeException("alarmId not found");
        fileService.createFile(alarmId, request);
    }

    @Mapping(types = JSATL12.文件数据上传, desc = "文件数据上传")
    public Object 文件数据上传(DataPacket request) {
        AlarmId alarmId = getAlarmId(request.getClientId(), request.getName());
        if (alarmId != null)
            fileService.writeFile(alarmId, request);
        return null;
    }

    @Mapping(types = JSATL12.文件上传完成消息, desc = "文件上传完成消息")
    public T9212 文件上传完成消息(T1211 request) {
        Map<String, AlarmId> alarmIdMap = cache.getIfPresent(request.getClientId());
        AlarmId alarmId = alarmIdMap.get(request.getName());
        T9212 result = new T9212();
        result.setName(request.getName());
        result.setType(request.getType());

        List<DataInfo> items = fileService.checkFile(alarmId, request);
        if (items.isEmpty()) {
            alarmIdMap.remove(request.getName());
            result.setResult(0);
        } else {
            result.setResult(1);
            result.setItems(items);
            result.setTotal(items.size());
        }
        return result;
    }
}