package org.yzh.file.endpoint;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import io.github.yezhihao.netmc.session.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yzh.protocol.commons.JSATL12;
import org.yzh.protocol.jsatl12.DataPacket;
import org.yzh.protocol.jsatl12.T1210;
import org.yzh.protocol.jsatl12.T1211;
import org.yzh.protocol.jsatl12.T9212;
import org.yzh.web.service.FileService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Endpoint
@Component
@RequiredArgsConstructor
public class JSATL12Endpoint {

    private final FileService fileService;

    private final Cache<String, Map<String, T1210.Item>> cache = Caffeine.newBuilder().expireAfterAccess(20, TimeUnit.MINUTES).build();

    @Mapping(types = JSATL12.报警附件信息消息, desc = "报警附件信息消息")
    public void alarmFileInfoList(T1210 message, Session session) {
        session.register(message.getDeviceId(), message);

        List<T1210.Item> items = message.getItems();
        if (items == null) return;

        Map<String, T1210.Item> fileInfos = cache.get(message.getClientId(), s -> new HashMap<>((int) (items.size() / 0.75) + 1));

        for (T1210.Item item : items)
            fileInfos.put(item.getName(), item.setParent(message));
        fileService.createDir(message);
    }

    @Mapping(types = JSATL12.文件信息上传, desc = "文件信息上传")
    public void alarmFileInfo(T1211 message, Session session) {
        if (!session.isRegistered()) session.register(message);
    }

    @Mapping(types = JSATL12.文件数据上传, desc = "文件数据上传")
    public Object alarmFile(DataPacket dataPacket, Session session) {
        Map<String, T1210.Item> fileInfos = cache.getIfPresent(session.getClientId());
        if (fileInfos != null) {

            T1210.Item fileInfo = fileInfos.get(dataPacket.getName().trim());
            if (fileInfo != null) {

                if (dataPacket.getOffset() == 0 && dataPacket.getLength() >= fileInfo.getSize()) {
                    fileService.writeFileSingle(fileInfo.getParent(), dataPacket);
                } else {
                    fileService.writeFile(fileInfo.getParent(), dataPacket);
                }
            }
        }
        return null;
    }

    @Mapping(types = JSATL12.文件上传完成消息, desc = "文件上传完成消息")
    public T9212 alarmFileComplete(T1211 message) {
        Map<String, T1210.Item> fileInfos = cache.getIfPresent(message.getClientId());
        T1210.Item fileInfo = fileInfos.get(message.getName());
        T9212 result = new T9212();
        result.setName(message.getName());
        result.setType(message.getType());

        int[] items = fileService.checkFile(fileInfo.getParent(), message);
        if (items == null) {
            fileInfos.remove(message.getName());
            if (fileInfos.isEmpty()) {
                cache.invalidate(message.getClientId());
            }
            result.setResult(0);
        } else {
            result.setItems(items);
            result.setResult(1);
        }
        return result;
    }
}