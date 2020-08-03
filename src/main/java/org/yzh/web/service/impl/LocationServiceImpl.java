package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.LocationMapper;
import org.yzh.web.model.entity.LocationDO;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class.getSimpleName());

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<Location> find(LocationQuery query) {
        List<Location> result = locationMapper.find(query);
        return result;
    }

    @Override
    public void batchInsert(List<T0200> list) {
        int size = list.size();
        List<LocationDO> locations = new ArrayList<>(size);
        Date now = new Date();
        for (T0200 t : list) {
            Date date = DateUtils.parse(t.getDateTime());
            if (date == null)
                continue;

            LocationDO location = new LocationDO();
            locations.add(location);

            location.setDeviceId(t.getHeader().getTerminalId());
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