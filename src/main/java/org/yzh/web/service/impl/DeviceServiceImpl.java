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

import java.time.LocalDate;
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
        record.setOnline(true);
        record.setBind(true);
        record.setDeviceModel(request.getDeviceModel());
        record.setMakerId(request.getMakerId());
        record.setCityId(request.getCityId());
        record.setProvinceId(request.getProvinceId());
        record.setDeviceTime(now);
        record.setRegisterTime(now);
        if (deviceDO == null || deviceDO.getInstallTime() == null)
            record.setInstallTime(now);

        Integer protocolVersion = request.getSession().getProtocolVersion();
        if (protocolVersion == null)
            protocolVersion = request.getHeader().getVersionNo();
        record.setProtocolVersion(protocolVersion);

        int row = deviceMapper.update(record);
        if (row == 0) {
            record.setCreator("device");
            record.setCreateTime(now);
            deviceMapper.insert(record);
        }

        DeviceInfo device = new DeviceInfo();
        device.setIssuedAt(LocalDate.now());
        device.setValidAt(7);
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
            DeviceInfo device = DeviceInfo.formBytes(bytes);

            LocalDate expiresAt = device.getIssuedAt().plusDays(device.getValidAt());
            if (expiresAt.isBefore(LocalDate.now())) {
                log.warn("鉴权失败：过期的token，{}", token);
                return null;
            }
            DeviceDO record = deviceMapper.get(device.getDeviceId());
            if (record != null) {
                device.setPlateNo(record.getPlateNo());

                record = new DeviceDO(device.getDeviceId(), true, LocalDateTime.now());
                record.setImei(request.getImei());
                record.setSoftwareVersion(request.getVersion());
                deviceMapper.update(record);
            }
            return device;
        } catch (Exception e) {
            log.warn("鉴权失败：错误的token，{}", e.getMessage());
            return null;
        }
    }
}