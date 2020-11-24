package org.yzh.web.service.impl;

import io.github.yezhihao.netmc.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.LocationMapper;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
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
        //MySQL预编译语句不支持批量写入，改用SQL拼接方式
//        jdbcBatchInsert(list);
        jdbcSQLInsert(list);
    }

    private static final String SQL_HEAD = "insert into location (device_time,device_id,mobile_no,plate_no,warning_mark,status,latitude,longitude,altitude,speed,direction,map_fence_id,create_time) values ";
    private static final String SQL = SQL_HEAD + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public void jdbcBatchInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        Session session;
        String mobileNo, deviceId, plateNo;
        int size = list.size();
        T0200 request;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
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

    public void jdbcSQLInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        Session session;
        String mobileNo, deviceId, plateNo;
        int size = list.size();
        T0200 request;

        StringBuilder sql = new StringBuilder(list.size() * 132 + 167);
        sql.append(SQL_HEAD);

        for (int i = 0; i < size; i++) {
            request = list.get(i);

            session = request.getSession();
            mobileNo = request.getHeader().getMobileNo();
            deviceId = mobileNo;
            plateNo = "";
            DeviceInfo device = (DeviceInfo) session.getSubject();
            if (device != null) {
                deviceId = device.getDeviceId();
                plateNo = device.getPlateNo();
            }

            sql.append('(');
            sql.append('\'').append(DateUtils.DATE_TIME_FORMATTER.format(request.getDateTime())).append('\'').append(',');
            sql.append('\'').append(deviceId).append('\'').append(',');
            sql.append('\'').append(mobileNo).append('\'').append(',');
            sql.append('\'').append(plateNo).append('\'').append(',');
            sql.append(request.getWarningMark()).append(',');
            sql.append(request.getStatus()).append(',');
            sql.append(request.getLatitude()).append(',');
            sql.append(request.getLongitude()).append(',');
            sql.append(request.getAltitude()).append(',');
            sql.append(request.getSpeed()).append(',');
            sql.append(request.getDirection()).append(',');
            sql.append(0).append(',');
            sql.append('\'').append(DateUtils.DATE_TIME_FORMATTER.format(now)).append('\'');
            sql.append(')');
            sql.append(',');
        }
        sql.setCharAt(sql.length() - 1, ' ');

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        } catch (Exception e) {
            log.warn("批量写入失败", e);
        }
    }
}