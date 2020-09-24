package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.mapper.DeviceMapper;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.DeviceService;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class.getSimpleName());

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public DeviceInfo register(T0100 request) {
        String deviceId = request.getDeviceId();
        DeviceDO deviceDO = deviceMapper.get(deviceId);
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
        if (deviceDO == null || deviceDO.getInstallTime() == null)
            record.setInstallTime(now);

        int row = deviceMapper.update(record);
        if (row == 0)
            deviceMapper.insert(record);

        DeviceInfo device = new DeviceInfo();
        device.setIssuedAt((int) (System.currentTimeMillis() / 1000));
        device.setValidAt(60 * 60 * 24 * 7);
        device.setPlateColor((byte) request.getPlateColor());
        device.setPlateNo(request.getPlateNo());
        device.setDeviceId(deviceId);
        return device;
    }

    @Override
    public DeviceInfo authentication(T0102 request) {
        String token = request.getToken();
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(token);
            bytes = EncryptUtils.decrypt(bytes);
            return DeviceInfo.formBytes(bytes);
        } catch (Exception e) {
            log.warn("鉴权失败：错误的token，{}", e.getMessage());
            return null;
        }
    }
}