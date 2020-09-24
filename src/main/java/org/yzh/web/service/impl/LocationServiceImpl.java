package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yzh.framework.session.Session;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.LocationMapper;
import org.yzh.web.model.entity.LocationDO;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
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

    private static final String sql = "insert ignore into location(device_id,mobile_no,plate_no,warning_mark,status,latitude,longitude,altitude,speed,direction,device_time,map_fence_id,create_time)values" +
            "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private void jdbcBatchInsert(List<T0200> list) {
        Date now = new Date();
        Date date;
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
                date = DateUtils.parse(request.getDateTime());
                if (date == null) continue;
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
                statement.setObject(j++, date);
                statement.setInt(j++, 0);
                statement.setObject(j, now);

                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception e) {
            log.warn("批量写入失败", e);
        }
    }

    private void mybatisBatchInsert(List<T0200> list) {
        int size = list.size();
        List<LocationDO> locations = new ArrayList<>(size);
        Date now = new Date();
        for (T0200 t : list) {
            Date date = DateUtils.parse(t.getDateTime());
            if (date == null)
                continue;

            LocationDO location = new LocationDO();
            locations.add(location);

            location.setDeviceId(t.getHeader().getClientId());
            location.setPlateNo("TODO");
            location.setWarningMark(t.getWarningMark());
            location.setStatus(t.getStatus());
            location.setLatitude(t.getLatitude());
            location.setLongitude(t.getLongitude());
            location.setAltitude(t.getAltitude());
            location.setSpeed(t.getSpeed());
            location.setDirection(t.getDirection());
            location.setDeviceTime(date);
            location.setMapFenceId(0);
            location.setCreateTime(now);
        }

        int row = locationMapper.batchInsert(locations);
        if (row <= 0)
            log.warn("主键重复,写入数据库失败{}", locations);
    }
}