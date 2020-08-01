package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.t808.T0100;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.commons.JsonUtils;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.DeviceService;
import org.yzh.web.service.LocationService;

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

            String json = JsonUtils.toJson(deviceInfo);
            byte[] bytes = json.getBytes(Charsets.GBK);
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

        String jsonStr = new String(bytes, Charsets.GBK);
        DeviceInfo deviceInfo = JsonUtils.toObj(DeviceInfo.class, jsonStr);
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        int expiresAt = deviceInfo.getIssuedAt() + deviceInfo.getValidAt();
        return currentTime < expiresAt;
    }

    @Override
    public boolean exists(String deviceId) {
        //TODO
        return true;
    }
}