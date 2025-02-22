package org.yzh.web.service;

import io.netty.buffer.ByteBuf;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.yzh.commons.util.DateUtils;
import org.yzh.commons.util.Exceptions;
import org.yzh.commons.util.IOUtils;
import org.yzh.protocol.jsatl12.DataPacket;
import org.yzh.protocol.jsatl12.T1210;
import org.yzh.protocol.jsatl12.T1211;
import org.yzh.protocol.t808.T0200;
import org.yzh.protocol.t808.T0801;
import org.yzh.web.config.JTProperties;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.enums.SessionKey;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Slf4j
@Service
public class FileService {

    @Resource
    private JTProperties jtProperties;

    private static final Comparator<long[]> comparator = Comparator.comparingLong((long[] a) -> a[0]).thenComparingLong(a -> a[1]);

    private String getDir(T1210 alarmId) {
        StringBuilder sb = new StringBuilder(80);
        sb.append(jtProperties.getT9208().getPath()).append('/');
        sb.append(alarmId.getClientId()).append('_');
        DateUtils.yyMMddHHmmss.formatTo(alarmId.getDateTime(), sb);
        sb.append('_').append(alarmId.getSerialNo()).append('/');
        return sb.toString();
    }

    /** 创建报警目录及 附件列表日志 */
    public void createDir(T1210 alarmId) {
        String dirPath = getDir(alarmId);
        new File(dirPath).mkdirs();

        List<T1210.Item> items = alarmId.getItems();
        StringBuilder fileList = new StringBuilder(items.size() * 50);
        fileList.append(dirPath).append('\n');

        for (T1210.Item item : items)
            fileList.append(item.getName()).append('\t').append(item.getSize()).append('\n');

        log.warn("文件列表: {}", fileList);
        Exceptions.ignore(() -> FileCopyUtils.copy(fileList.toString().getBytes(StandardCharsets.UTF_8), new File(dirPath, "fs.txt")));
    }

    /** 将数据块写入到报警文件，并记录日志 */
    public void writeFile(T1210 alarmId, DataPacket fileData) {
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

    public void writeFileSingle(T1210 alarmId, DataPacket fileData) {
        String dir = getDir(alarmId);
        String name = dir + fileData.getName().trim();

        int offset = fileData.getOffset();

        RandomAccessFile file = null;
        ByteBuf data = fileData.getData();
        try {
            file = new RandomAccessFile(name, "rw");
            data.readBytes(file.getChannel(), offset, data.readableBytes());
        } catch (IOException e) {
            log.error("写入报警文件", e);
        } finally {
            IOUtils.close(file);
        }
    }

    /** 根据日志检查文件完整性，并返回缺少的数据块信息 */
    public int[] checkFile(T1210 alarmId, T1211 fileInfo) {
        String dir = getDir(alarmId);
        File logFile = new File(dir + fileInfo.getName() + ".log");

        byte[] bytes;
        FileInputStream in = null;
        try {
            in = new FileInputStream(logFile);
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (FileNotFoundException e) {
            return null;
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
            File dest = new File(dir + fileInfo.getName());
            if (file.renameTo(dest)) {
                logFile.delete();
            }
            return null;
        }
        return result.stream().filter(Objects::nonNull).mapToInt(i -> i).toArray();
    }

    /** 多媒体数据上传 */
    public boolean saveMediaFile(T0801 message) {
        DeviceDO device = message.getSession().getAttribute(SessionKey.Device);
        T0200 location = message.getLocation();

        StringBuilder filename = new StringBuilder(32);
        filename.append(type(message.getType())).append('_');
        DateUtils.yyMMddHHmmss.formatTo(location.getDeviceTime(), filename);
        filename.append('_');
        filename.append(message.getChannelId()).append('_');
        filename.append(message.getEvent()).append('_');
        filename.append(message.getId()).append('.');
        filename.append(suffix(message.getFormat()));

        String deviceId;
        if (device == null)
            deviceId = message.getClientId();
        else
            deviceId = device.getDeviceId();

        File dir = new File(jtProperties.getT0801().getPath() + '/' + deviceId);
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
        return switch (type) {
            case 0 -> "image";
            case 1 -> "audio";
            case 2 -> "video";
            default -> "unknown";
        };
    }

    private static String suffix(int format) {
        return switch (format) {
            case 0 -> "jpg";
            case 1 -> "tif";
            case 2 -> "mp3";
            case 3 -> "wav";
            case 4 -> "wmv";
            default -> "bin";
        };
    }
}