package org.yzh.web.service;

import org.yzh.protocol.jsatl12.*;
import org.yzh.protocol.t808.T0801;

import java.util.List;

public interface FileService {

    boolean saveMediaFile(T0801 message);

    void createDir(T1210 alarmInfo);

    void createFile(AlarmId alarmId, T1211 fileInfo);

    void writeFile(AlarmId alarmId, DataPacket fileData);

    List<DataInfo> checkFile(AlarmId alarmId, T1211 fileInfo);

}