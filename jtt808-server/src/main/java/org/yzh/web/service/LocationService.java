package org.yzh.web.service;

import org.yzh.web.model.protocol.T0200;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;

import java.util.List;

public interface LocationService {

    List<Location> find(LocationQuery query);

    void batchInsert(List<T0200> list);
}