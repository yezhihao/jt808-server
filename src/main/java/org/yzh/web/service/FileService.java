package org.yzh.web.service;

import org.yzh.protocol.jsatl12.*;

import java.util.List;

public interface FileService {

    void createDir(T1210 alarmInfo);

    void createFile(AlarmId alarmId, T1211 fileInfo);

    void writeFile(AlarmId alarmId, DataPacket fileData);

    List<DataInfo> checkFile(AlarmId alarmId, T1211 fileInfo);

}