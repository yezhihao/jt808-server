package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0100;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.DeviceService;
import org.yzh.web.service.LocationService;

import java.io.*;
import java.util.Base64;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class.getSimpleName());

    @Override
    public String register(T0100 request) {
        String deviceId = request.getDeviceId();
        if (exists(deviceId)) {

            int now = (int) (System.currentTimeMillis() / 1000);

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setIssuedAt(now);
            deviceInfo.setValidAt(60 * 60 * 24 * 7);
            deviceInfo.setPlateColor((byte) request.getPlateColor());
            deviceInfo.setPlateNo(request.getPlateNo());
            deviceInfo.setDeviceId(request.getDeviceId());

            byte[] bytes = toBytes(deviceInfo);
            bytes = EncryptUtils.encrypt(bytes);
            String token = Base64.getEncoder().encodeToString(bytes);
            return token;
        }
        return null;
    }

    @Override
    public boolean authentication(String token) {
        byte[] bytes = Base64.getDecoder().decode(token);
        bytes = EncryptUtils.decrypt(bytes);
        if (bytes == null)
            return false;

        DeviceInfo deviceInfo = formBytes(bytes);
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        int expiresAt = deviceInfo.getIssuedAt() + deviceInfo.getValidAt();
        return currentTime < expiresAt;
    }

    @Override
    public boolean exists(String deviceId) {
        //TODO
        return true;
    }


    private static DeviceInfo formBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            DeviceInfo result = new DeviceInfo();
            result.setIssuedAt(dis.readInt());
            result.setValidAt(dis.readInt());
            result.setPlateColor(dis.readByte());
            result.setPlateNo(dis.readUTF());
            result.setDeviceId(dis.readUTF());

            return result;
        } catch (IOException e) {
            log.warn("写入token失败", e);
        }
        return null;
    }

    private static byte[] toBytes(DeviceInfo deviceToken) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(32);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.writeInt(deviceToken.getIssuedAt());
            dos.writeInt(deviceToken.getValidAt());
            dos.writeByte(deviceToken.getPlateColor());
            dos.writeUTF(deviceToken.getPlateNo());
            dos.writeUTF(deviceToken.getDeviceId());

            return bos.toByteArray();
        } catch (IOException e) {
            log.warn("读取token失败", e);
        }
        return null;
    }
}