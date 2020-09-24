package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0100;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.mapper.DeviceMapper;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.DeviceService;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class.getSimpleName());

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public String register(T0100 request) {
        String deviceId = request.getDeviceId();
        DeviceDO device = deviceMapper.get(deviceId);
//        if (device == null)//TODO 根据自身业务选择是否校验设备ID
//            return null;

        LocalDateTime now = LocalDateTime.now();

        DeviceDO record = new DeviceDO();
        record.setDeviceId(deviceId);
        record.setMobileNo(request.getHeader().getMobileNo());
        record.setPlateNo(request.getPlateNo());
        record.setBind(true);
        record.setDeviceModel(request.getDeviceModel());
        record.setMakerId(request.getMakerId());
        record.setCityId(request.getCityId());
        record.setProvinceId(request.getProvinceId());
        record.setCreator("device");
        record.setUpdater("device");
        record.setCreateTime(now);
        record.setUpdateTime(now);
        record.setDeviceTime(now);
        record.setRegisterTime(now);
        if (device == null || device.getInstallTime() == null)
            record.setInstallTime(now);

        int row = deviceMapper.update(record);
        if (row == 0)
            deviceMapper.insert(record);

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setIssuedAt((int) (System.currentTimeMillis() / 1000));
        deviceInfo.setValidAt(60 * 60 * 24 * 7);
        deviceInfo.setPlateColor((byte) request.getPlateColor());
        deviceInfo.setPlateNo(request.getPlateNo());
        deviceInfo.setDeviceId(deviceId);

        byte[] bytes = toBytes(deviceInfo);
        bytes = EncryptUtils.encrypt(bytes);
        String token = Base64.getEncoder().encodeToString(bytes);
        return token;
    }

    @Override
    public boolean authentication(String token) {
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(token);
            bytes = EncryptUtils.decrypt(bytes);
        } catch (Exception e) {
            log.warn("鉴权失败：错误的token，{}", e.getMessage());
            return false;
        }

        DeviceInfo deviceInfo = formBytes(bytes);
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        int expiresAt = deviceInfo.getIssuedAt() + deviceInfo.getValidAt();
        return currentTime < expiresAt;
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