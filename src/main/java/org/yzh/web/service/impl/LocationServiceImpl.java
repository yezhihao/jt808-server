package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.IOUtils;
import org.yzh.web.mapper.LocationMapper;
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

    private static final String SQL_HEAD = "insert ignore into location (device_time,device_id,mobile_no,plate_no,warn_bit,status_bit,longitude,latitude,altitude,speed,direction,alarm_type,create_time) values ";
    private static final String SQL = SQL_HEAD + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public void jdbcBatchInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        int size = list.size();

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL);
            for (int i = 0; i < size; i++) {
                T0200 request = list.get(i);
                int j = 1;

                statement.setObject(j++, request.getDeviceTime());
                statement.setString(j++, request.getDeviceId());
                statement.setString(j++, request.getClientId());
                statement.setString(j++, request.getPlateNo());
                statement.setInt(j++, request.getWarnBit());
                statement.setInt(j++, request.getStatusBit());
                statement.setInt(j++, request.getLongitude());
                statement.setInt(j++, request.getLatitude());
                statement.setInt(j++, request.getAltitude());
                statement.setInt(j++, request.getSpeed());
                statement.setInt(j++, request.getDirection());
                statement.setInt(j++, 0);
                statement.setObject(j, now);

                statement.addBatch();
            }
            statement.executeLargeBatch();
        } catch (Exception e) {
            log.error("批量写入失败", e);
        } finally {
            IOUtils.close(statement, connection);
        }
    }

    public void jdbcSQLInsert(List<T0200> list) {
        String now = DateUtils.DATE_TIME_FORMATTER.format(LocalDateTime.now());
        int size = list.size();

        StringBuilder builder = new StringBuilder(size * 132 + 174);
        builder.append(SQL_HEAD);

        for (int i = 0; i < size; i++) {
            T0200 request = list.get(i);

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
            builder.append('0').append(',');
            builder.append('\'').append(now).append('\'');
            builder.append(')');
            builder.append(',');
        }
        String sql = builder.substring(0, builder.length() - 1);

        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            int row = statement.executeUpdate(sql);
            if (row < size)
                log.warn("批量写入存在重复的主键或唯一键,新增:{},忽略:{}", row, size - row);
        } catch (Exception e) {
            log.error(sql);
            log.error("批量写入失败", e);
        } finally {
            IOUtils.close(statement, connection);
        }
    }
}