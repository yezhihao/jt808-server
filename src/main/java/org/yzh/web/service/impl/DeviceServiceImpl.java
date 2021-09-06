package org.yzh.web.service.impl;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.commons.IOUtils;
import org.yzh.web.mapper.DeviceMapper;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.DeviceService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class.getSimpleName());

    @Autowired
    private DeviceMapper deviceMapper;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionManager sessionManager;

    @Override
    public DeviceInfo register(T0100 request) {
        String deviceId = request.getDeviceId();
        DeviceDO deviceDO = deviceMapper.get(deviceId);
//        if (device == null)//TODO 根据自身业务选择是否校验设备ID
//            return null;

        LocalDateTime now = LocalDateTime.now();

        DeviceDO record = new DeviceDO();
        record.setDeviceId(deviceId);
        record.setMobileNo(request.getClientId());
        record.setPlateNo(request.getPlateNo());
        record.setBind(true);
        record.setDeviceModel(request.getDeviceModel());
        record.setMakerId(request.getMakerId());
        record.setCityId(request.getCityId());
        record.setProvinceId(request.getProvinceId());
        record.setProtocolVersion(request.getProtocolVersion());
        record.setRegisterTime(now);
        if (deviceDO == null || deviceDO.getInstallTime() == null)
            record.setInstallTime(now);

        int row = deviceMapper.update(record);
        if (row == 0) {
            record.setCreator("device");
            record.setCreateTime(now);
            row = deviceMapper.insert(record);
            if (row == 0)
                return null;
        }

        DeviceInfo device = new DeviceInfo();
        device.setIssuedAt(LocalDate.now());
        device.setDeviceId(deviceId);
        device.setClientId(request.getClientId());
        device.setProtocolVersion(request.getProtocolVersion());

        device.setReserved((byte) 0);
        device.setPlateColor((byte) request.getPlateColor());
        device.setPlateNo(request.getPlateNo());
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
            device.setClientId(request.getClientId());
            device.setProtocolVersion(request.getProtocolVersion());

            DeviceDO record = deviceMapper.get(device.getDeviceId());
            if (record != null) {
                device.setPlateNo(record.getPlateNo());

                record = new DeviceDO(device.getDeviceId());
                record.setImei(request.getImei());
                record.setSoftwareVersion(request.getSoftwareVersion());
                deviceMapper.update(record);
            }
            return device;
        } catch (Exception e) {
            log.warn("鉴权失败：错误的token[{}]，{}", token, e.getMessage());
            //TODO 鉴权失败，使用测试车辆，仅供测试！
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId("12345678901");
            deviceInfo.setPlateNo("测A12345");
            return deviceInfo;
        }
    }

    private static final String SQL_HEAD = "insert into device_status (device_time,device_id,mobile_no,plate_no,warn_bit,status_bit,longitude,latitude,altitude,speed,direction,update_time) values ";
    private static final String SQL_TAIL = "on duplicate key update device_time=values(device_time),mobile_no=values(mobile_no),plate_no=values(plate_no),warn_bit=values(warn_bit),status_bit=values(status_bit),longitude=values(longitude),latitude=values(latitude),altitude=values(altitude),speed=values(speed),direction=values(direction),update_time=values(update_time)";

    @Scheduled(fixedDelay = 1000)
    public void updateDeviceStatus() {
        Collection<Session> all = sessionManager.all();
        if (all.isEmpty())
            return;

        String now = DateUtils.DATE_TIME_FORMATTER.format(LocalDateTime.now());

        StringBuilder builder = new StringBuilder(1024);
        builder.append(SQL_HEAD);

        for (Session session : all) {
            T0200 request = SessionKey.getSnapshot(session);
            if (request == null || request.updated())
                continue;

            builder.append('(');
            builder.append('\'').append(DateUtils.DATE_TIME_FORMATTER.format(request.getDeviceTime())).append('\'').append(',');
            builder.append('\'').append(request.getDeviceId()).append('\'').append(',');
            builder.append('\'').append(request.getClientId()).append('\'').append(',');
            builder.append('\'').append(request.getPlateNo()).append('\'').append(',');
            builder.append(request.getWarnBit()).append(',');
            builder.append(request.getStatusBit()).append(',');
            builder.append(request.getLongitude()).append(',');
            builder.append(request.getLatitude()).append(',');
            builder.append(request.getAltitude()).append(',');
            builder.append(request.getSpeed()).append(',');
            builder.append(request.getDirection()).append(',');
            builder.append('\'').append(now).append('\'');
            builder.append(')');
            builder.append(',');
        }
        if (builder.length() == SQL_HEAD.length())
            return;
        builder.setCharAt(builder.length() - 1, ' ');
        builder.append(SQL_TAIL);
        String sql = builder.toString();

        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            log.error("批量更新失败", e);
        } finally {
            IOUtils.close(statement, connection);
        }
    }
}