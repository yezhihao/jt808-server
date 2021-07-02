package org.yzh.web.service;

import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.jsatl12.DataPacket;
import org.yzh.protocol.jsatl12.T1210;
import org.yzh.protocol.jsatl12.T1211;
import org.yzh.protocol.t808.T0801;

public interface FileService {

    boolean saveMediaFile(T0801 message);

    void createDir(T1210 alarmInfo);

    void createFile(AlarmId alarmId, T1211 fileInfo);

    void writeFile(AlarmId alarmId, DataPacket fileData);

    int[] checkFile(AlarmId alarmId, T1211 fileInfo);

}