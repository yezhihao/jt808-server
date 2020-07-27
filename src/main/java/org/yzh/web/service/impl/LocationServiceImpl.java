package org.yzh.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.t808.T0200;
import org.yzh.web.mapper.LocationMapper;
import org.yzh.web.model.entity.Location;
import org.yzh.web.service.LocationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public void batchInsert(List<T0200> list) {
        int size = list.size();
        List<Location> locations = new ArrayList<>(size);
        LocalDateTime now = LocalDateTime.now();
        for (T0200 t : list) {
            Header header = t.getHeader();

            Location location = new Location();
            locations.add(location);

            location.setDeviceId(header.getTerminalId());
            location.setPlateNo("TODO");
            location.setWarningMark(t.getWarningMark());
            location.setStatus(t.getStatus());
            location.setLatitude(t.getLatitude());
            location.setLongitude(t.getLongitude());
            location.setAltitude(t.getAltitude());
            location.setSpeed(t.getSpeed());
            location.setDirection(t.getDirection());
            LocalDateTime deviceTime = LocalDateTime.parse(t.getDateTime(), DateUtils.yyMMddHHmmss);
            LocalDate deviceDate = deviceTime.toLocalDate();
            location.setDeviceTime(deviceTime);
            location.setDeviceDate(deviceDate);
            location.setMapFenceId(0);
            location.setCreateTime(now);
        }

        locationMapper.batchInsert(locations);
    }
}