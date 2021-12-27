package org.yzh.web.service.impl;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yzh.commons.util.IOUtils;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.jsatl12.AlarmId;
import org.yzh.protocol.jsatl12.DataPacket;
import org.yzh.protocol.jsatl12.T1210;
import org.yzh.protocol.jsatl12.T1211;
import org.yzh.protocol.t808.T0200;
import org.yzh.protocol.t808.T0801;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.FileService;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class.getSimpleName());

    private static final Comparator<long[]> comparator = Comparator.comparingLong((long[] a) -> a[0]).thenComparingLong(a -> a[1]);

    @Value("${jt-server.alarm-file.path}")
    private String alarmFileRoot;

    @Value("${jt-server.jt808.media-file.path}")
    private String mediaFileRoot;

    private String getDir(AlarmId alarmId) {
        StringBuilder sb = new StringBuilder(55);
        sb.append(alarmFileRoot).append('/');
        sb.append(alarmId.getDeviceId()).append('/');
        sb.append(alarmId.getDateTime()).append('_').append(alarmId.getSerialNo()).append('/');
        return sb.toString();
    }

    /** 创建报警目录及 附件列表日志 */
    @Override
    public void createDir(T1210 alarmInfo) {
        AlarmId alarmId = alarmInfo.getAlarmId();
        File dir = new File(getDir(alarmId));
        dir.mkdirs();

        List<T1210.Item> items = alarmInfo.getItems();
        StringBuilder fileList = new StringBuilder(items.size() * 50);

        for (T1210.Item item : items)
            fileList.append(item.getName()).append('\t').append(item.getSize()).append(IOUtils.Separator);

        IOUtils.write(new File(dir, "fs.txt"), fileList.toString());
    }

    /** 创建报警文件 */
    @Override
    public void createFile(AlarmId alarmId, T1211 fileInfo) {
        String dir = getDir(alarmId);

        File file = new File(dir + fileInfo.getName() + ".tmp");
        if (!file.exists()) {
            RandomAccessFile r = null;
            try {
                r = new RandomAccessFile(file, "rw");
                r.setLength(fileInfo.getSize());
            } catch (IOException e) {
                log.error("创建报警文件", e);
            } finally {
                IOUtils.close(r);
            }
        }
    }

    /** 将数据块写入到报警文件，并记录日志 */
    @Override
    public void writeFile(AlarmId alarmId, DataPacket fileData) {
        String dir = getDir(alarmId);
        String name = dir + fileData.getName().trim();

        int offset = fileData.getOffset();
        int length = fileData.getLength();

        byte[] buffer = ByteBuffer.allocate(8)
                .putInt(offset).putInt(length).array();

        RandomAccessFile file = null;
        FileOutputStream filelog = null;
        ByteBuf data = fileData.getData();
        try {
            file = new RandomAccessFile(name + ".tmp", "rw");
            filelog = new FileOutputStream(name + ".log", true);

            data.readBytes(file.getChannel(), offset, data.readableBytes());
            filelog.write(buffer);
        } catch (IOException e) {
            log.error("写入报警文件", e);
        } finally {
            IOUtils.close(file, filelog);
        }
    }

    /** 根据日志检查文件完整性，并返回缺少的数据块信息 */
    @Override
    public int[] checkFile(AlarmId alarmId, T1211 fileInfo) {
        String dir = getDir(alarmId);
        File logFile = new File(dir + fileInfo.getName() + ".log");

        byte[] bytes;
        FileInputStream in = null;
        try {
            in = new FileInputStream(logFile);
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (IOException e) {
            log.error("检查文件完整性", e);
            return null;
        } finally {
            IOUtils.close(in);
        }

        int size = bytes.length / 8;
        long[][] items = new long[size + 2][2];
        items[size + 1][0] = fileInfo.getSize();

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        for (int i = 1; i <= size; i++) {
            items[i][0] = buffer.getInt();
            items[i][1] = buffer.getInt();
        }

        List<Integer> result = new ArrayList<>(items.length);
        int len = items.length - 1;
        Arrays.sort(items, 1, len, comparator);

        for (int i = 0; i < len; ) {
            long a = items[i][0] + items[i][1];
            long b = items[++i][0] - a;
            if (b > 0) {
                result.add((int) a);
                result.add((int) b);
            }
        }

        if (result.isEmpty()) {
            File file = new File(dir + fileInfo.getName() + ".tmp");
            file.renameTo(new File(dir + fileInfo.getName()));
            logFile.delete();
            return null;
        }
        return StrUtils.toArray(result);
    }

    /** 多媒体数据上传 */
    @Override
    public boolean saveMediaFile(T0801 message) {
        DeviceInfo deviceInfo = SessionKey.getDeviceInfo(message.getSession());
        T0200 location = message.getLocation();

        StringBuilder filename = new StringBuilder(32);
        filename.append(type(message.getType())).append('_');
        filename.append(location.getDateTime()).append('_');
        filename.append(message.getChannelId()).append('_');
        filename.append(message.getEvent()).append('_');
        filename.append(message.getId()).append('.');
        filename.append(suffix(message.getFormat()));

        String deviceId;
        if (deviceInfo == null)
            deviceId = message.getClientId();
        else
            deviceId = deviceInfo.getDeviceId();

        File dir = new File(mediaFileRoot + "\\" + deviceId);
        dir.mkdirs();

        ByteBuf packet = message.getPacket();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(dir, filename.toString()));
            packet.readBytes(fos.getChannel(), 0, packet.readableBytes());
            return true;
        } catch (IOException e) {
            log.error("多媒体数据保存失败", e);
            return false;
        } finally {
            IOUtils.close(fos);
            packet.release();
        }
    }

    private static String type(int type) {
        switch (type) {
            case 0:
                return "image";
            case 1:
                return "audio";
            case 2:
                return "video";
            default:
                return "unknown";
        }
    }

    private static String suffix(int format) {
        switch (format) {
            case 0:
                return "jpg";
            case 1:
                return "tif";
            case 2:
                return "mp3";
            case 3:
                return "wav";
            case 4:
                return "wmv";
            default:
                return "bin";
        }
    }
}