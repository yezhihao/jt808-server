package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yzh.framework.session.Session;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.mapper.LocationMapper;
import org.yzh.web.model.entity.LocationDO;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class.getSimpleName());

    @Autowired
    private LocationMapper locationMapper;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Location> find(LocationQuery query) {
        List<Location> result = locationMapper.find(query);
        return result;
    }

    @Override
    public void batchInsert(List<T0200> list) {
        jdbcBatchInsert(list);
//        mybatisBatchInsert(list);
    }

    private static final String sql = "insert ignore into location(device_time,device_id,mobile_no,plate_no,warning_mark,status,latitude,longitude,altitude,speed,direction,map_fence_id,create_time)values" +
            "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private void jdbcBatchInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        Session session;
        String mobileNo, deviceId, plateNo;
        int size = list.size();
        T0200 request;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            //降低事务隔离级别，提高写入速度
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            for (int i = 0; i < size; i++) {
                request = list.get(i);
                int j = 1;

                session = request.getSession();
                mobileNo = request.getHeader().getMobileNo();
                deviceId = mobileNo;
                plateNo = "";
                DeviceInfo device = (DeviceInfo) session.getSubject();
                if (device != null) {
                    deviceId = device.getDeviceId();
                    plateNo = device.getPlateNo();
                }

                statement.setObject(j++, request.getDateTime());
                statement.setString(j++, deviceId);
                statement.setString(j++, mobileNo);
                statement.setString(j++, plateNo);
                statement.setInt(j++, request.getWarningMark());
                statement.setInt(j++, request.getStatus());
                statement.setInt(j++, request.getLatitude());
                statement.setInt(j++, request.getLongitude());
                statement.setInt(j++, request.getAltitude());
                statement.setInt(j++, request.getSpeed());
                statement.setInt(j++, request.getDirection());
                statement.setInt(j++, 0);
                statement.setObject(j, now);

                statement.addBatch();
            }
            statement.executeLargeBatch();
        } catch (Exception e) {
            log.warn("批量写入失败", e);
        }
    }

    private void mybatisBatchInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        Session session;
        String mobileNo, deviceId, plateNo;
        int size = list.size();
        List<LocationDO> locations = new ArrayList<>(size);
        for (T0200 request : list) {

            session = request.getSession();
            mobileNo = request.getHeader().getMobileNo();
            deviceId = mobileNo;
            plateNo = "";
            DeviceInfo device = (DeviceInfo) session.getSubject();
            if (device != null) {
                deviceId = device.getDeviceId();
                plateNo = device.getPlateNo();
            }

            LocationDO location = new LocationDO();
            locations.add(location);

            location.setDeviceTime(request.getDateTime());
            location.setDeviceId(deviceId);
            location.setMobileNo(mobileNo);
            location.setPlateNo(plateNo);
            location.setWarningMark(request.getWarningMark());
            location.setStatus(request.getStatus());
            location.setLatitude(request.getLatitude());
            location.setLongitude(request.getLongitude());
            location.setAltitude(request.getAltitude());
            location.setSpeed(request.getSpeed());
            location.setDirection(request.getDirection());
            location.setMapFenceId(0);
            location.setCreateTime(now);
        }

        int row = locationMapper.batchInsert(locations);
        if (row <= 0)
            log.warn("主键重复,写入数据库失败{}", locations);
    }
}