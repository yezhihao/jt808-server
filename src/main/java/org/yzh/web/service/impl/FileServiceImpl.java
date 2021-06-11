package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yzh.protocol.jsatl12.*;
import org.yzh.web.commons.FileUtils;
import org.yzh.web.service.FileService;

import java.io.*;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class.getSimpleName());

    @Value("${tcp-server.alarm-file.path}")
    private String root;

    private File getDir(AlarmId alarmId) {
        StringBuilder sb = new StringBuilder(32);
        sb.append(root);
        sb.append(alarmId.getDeviceId()).append("/");
        sb.append(alarmId.getDateTime()).append("_").append(alarmId.getSerialNo()).append("/");
        File result = new File(sb.toString());
        return result;
    }

    /** 创建报警目录及 附件列表日志 */
    @Override
    public void createDir(T1210 alarmInfo) {
        AlarmId alarmId = alarmInfo.getAlarmId();
        File dir = getDir(alarmId);
        dir.mkdirs();

        List<T1210.Item> items = alarmInfo.getItems();
        StringBuilder fileList = new StringBuilder(items.size() * 50);

        for (T1210.Item item : items)
            fileList.append(item.getName()).append("\t").append(item.getSize()).append(FileUtils.Separator);

        FileUtils.write(new File(dir, "fs.txt"), fileList.toString());
    }

    /** 创建报警文件 */
    @Override
    public void createFile(AlarmId alarmId, T1211 fileInfo) {
        File dir = getDir(alarmId);

        File file = new File(dir, fileInfo.getName() + ".tmp");
        if (!file.exists()) {
            try (RandomAccessFile r = new RandomAccessFile(file, "rw")) {
                r.setLength(fileInfo.getSize());
            } catch (IOException e) {
                log.error("创建报警文件", e);
            }
        }
    }

    /** 将数据块写入到报警文件，并记录日志 */
    @Override
    public void writeFile(AlarmId alarmId, DataPacket fileData) {
        File dir = getDir(alarmId);

        String name = fileData.getName().trim();
        File logFile = new File(dir, name + ".log");
        File dataFile = new File(dir, name + ".tmp");
        if (!logFile.exists())
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                return;
            }
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                return;
            }
        try (RandomAccessFile file = new RandomAccessFile(dataFile, "rw");
             RandomAccessFile log = new RandomAccessFile(logFile, "rw")) {

            long offset = fileData.getOffset();
            long length = fileData.getLength();

            file.getChannel().write(fileData.getData(), offset);

            log.skipBytes((int) log.length());
            log.writeLong(offset);
            log.writeLong(length);
        } catch (IOException e) {
            log.error("写入报警文件", e);
        }
    }

    /** 根据日志检查文件完整性，并返回缺少的数据块信息 */
    @Override
    public List<DataInfo> checkFile(AlarmId alarmId, T1211 fileInfo) {
        File dir = getDir(alarmId);

        File logFile = new File(dir, fileInfo.getName() + ".log");
        if (!logFile.exists())
            return Collections.emptyList();
        Map<Long, Long> items = new HashMap<>();

        try (DataInputStream dis = new DataInputStream(new FileInputStream(logFile))) {
            int size = dis.available() / 16;
            for (int i = 0; i < size; i++)
                items.put(dis.readLong(), dis.readLong());

        } catch (IOException e) {
            log.error("检查文件完整性", e);
        }

        List<DataInfo> result = new ArrayList<>();

        long unit = 1024 * 64;
        long size = fileInfo.getSize();

        long r = size % unit;
        long i = 0, s = size - r;
        do {
            if (!items.containsKey(i))
                result.add(new DataInfo(i, (i + unit > size) ? r : unit));
        } while ((i += unit) <= s);

        if (result.isEmpty()) {
            File file = new File(dir, fileInfo.getName() + ".tmp");
            file.renameTo(new File(dir, fileInfo.getName()));
            logFile.delete();
        }

        return result;
    }
}