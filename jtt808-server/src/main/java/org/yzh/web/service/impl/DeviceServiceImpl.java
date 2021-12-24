package org.yzh.web.service.impl;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.yzh.commons.model.Result;
import org.yzh.commons.util.EncryptUtils;
import org.yzh.commons.util.IOUtils;
import org.yzh.commons.util.StrUtils;
import org.yzh.protocol.commons.DateUtils;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T8100;
import org.yzh.web.mapper.DeviceMapper;
import org.yzh.web.mapper.VehicleMapper;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.entity.VehicleDO;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.protocol.T0200;
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

    private static final Result<DeviceInfo> AlreadyRegisteredTerminal = Result.of(T8100.AlreadyRegisteredTerminal);
    private static final Result<DeviceInfo> NotFoundTerminal = Result.of(T8100.NotFoundTerminal);
    private static final Result<DeviceInfo> AlreadyRegisteredVehicle = Result.of(T8100.AlreadyRegisteredVehicle);
    private static final Result<DeviceInfo> NotFoundVehicle = Result.of(T8100.NotFoundVehicle);

    private static final boolean vehicleCheck = false;//是否校验车牌号
    private static final boolean deviceCheck = false;//是否校验设备ID

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionManager sessionManager;

    @Override
    public Result<DeviceInfo> register(T0100 request) {
        String clientId = request.getClientId();
        String deviceId = request.getDeviceId();
        String plateNo = request.getPlateNo();

        if (StrUtils.isBlank(clientId) || StrUtils.isBlank(deviceId) || StrUtils.isBlank(plateNo))
            return NotFoundTerminal;

        VehicleDO vehicle = vehicleMapper.getByPlateNo(plateNo);
        if (vehicle != null) {
            if (!(StrUtils.isBlank(vehicle.getDeviceId()) || deviceId.equals(vehicle.getDeviceId())))
                return AlreadyRegisteredVehicle;
        } else {
            if (vehicleCheck)
                return NotFoundVehicle;
            vehicle = new VehicleDO();
        }

        DeviceDO device = deviceMapper.get(deviceId);
        if (device != null) {
            if (!(device.getVehicleId() == null || device.getVehicleId().equals(vehicle.getId())))
                return AlreadyRegisteredTerminal;
        } else {
            if (deviceCheck)
                return NotFoundTerminal;
            device = new DeviceDO();
        }

        vehicle.setDeviceId(deviceId);
        vehicle.setPlateNo(plateNo);
        vehicle.setPlateColor(request.getPlateColor());
        vehicle.setCityId(request.getCityId());
        vehicle.setProvinceId(request.getProvinceId());

        if (vehicle.getId() != null) {
            vehicleMapper.update(vehicle.updatedBy("device"));
        } else {
            vehicleMapper.insert(vehicle.createdBy("device"));
        }


        device.setVehicleId(vehicle.getId());
        device.setAgencyId(vehicle.getAgencyId());
        device.setMobileNo(clientId);
        device.setDeviceModel(request.getDeviceModel());
        device.setProtocolVersion(request.getProtocolVersion());
        device.setMakerId(request.getMakerId());
        device.setRegisterTime(LocalDateTime.now());

        if (device.getDeviceId() != null) {
            deviceMapper.update(device.updatedBy("device"));
        } else {
            if (device.getInstallTime() == null)
                device.setInstallTime(LocalDateTime.now());
            deviceMapper.insert(device.createdBy("device").deviceId(deviceId));
        }


        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setIssuedAt(LocalDate.now());
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setVehicleId(vehicle.getId());
        deviceInfo.setClientId(clientId);
        deviceInfo.setProtocolVersion(request.getProtocolVersion());

        deviceInfo.setReserved((byte) 0);
        deviceInfo.setPlateColor((byte) request.getPlateColor());
        deviceInfo.setPlateNo(plateNo);
        return Result.of(deviceInfo);
    }

    @Override
    public DeviceInfo authentication(T0102 request) {
        try {
            byte[] bytes = Base64.getDecoder().decode(request.getToken());
            bytes = EncryptUtils.decrypt(bytes);
            DeviceInfo deviceInfo = DeviceInfo.formBytes(bytes);

            DeviceDO record = deviceMapper.get(deviceInfo.getDeviceId());
            if (record == null) {
                log.warn("鉴权失败：不存在的设备ID[{}]", deviceInfo.getDeviceId());
                return null;
            }

            if (request.getImei() != null && !request.getImei().equals(record.getImei())) {
                deviceMapper.update(new DeviceDO()
                        .deviceId(deviceInfo.getDeviceId())
                        .imei(request.getImei())
                        .softwareVersion(request.getSoftwareVersion()));
            }

            deviceInfo.setAgencyId(record.getAgencyId());
            deviceInfo.setVehicleId(record.getVehicleId());
            deviceInfo.setPlateNo(record.getPlateNo());
            deviceInfo.setPlateColor(record.getPlateColor());
            deviceInfo.setClientId(request.getClientId());
            deviceInfo.setProtocolVersion(request.getProtocolVersion());
            return deviceInfo;
        } catch (Exception e) {
            log.warn("鉴权失败：错误的token[{}]，{}", request.getToken(), e.getMessage());
            return null;
        }
    }

    private static final String SQL_HEAD = "insert into device_status (device_time,device_id,mobile_no,warn_bit,status_bit,longitude,latitude,altitude,speed,direction,updated_at) values ";
    private static final String SQL_TAIL = "on duplicate key update device_time=values(device_time),mobile_no=values(mobile_no),warn_bit=values(warn_bit),status_bit=values(status_bit),longitude=values(longitude),latitude=values(latitude),altitude=values(altitude),speed=values(speed),direction=values(direction),updated_at=values(updated_at)";

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