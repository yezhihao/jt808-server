package org.yzh.component.area.service;

import io.github.yezhihao.netmc.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.component.area.AreaFilter;
import org.yzh.component.area.GeometryFactory;
import org.yzh.component.area.mapper.AreaMapper;
import org.yzh.component.area.model.Area;
import org.yzh.component.area.model.TArea;
import org.yzh.component.area.model.VehicleArea;
import org.yzh.component.area.model.entity.AreaDO;
import org.yzh.component.area.model.vo.AreaQuery;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Service
public class AreaService {

    private static final Logger log = LoggerFactory.getLogger(AreaService.class.getSimpleName());

    private Map<Integer, AreaFilter> vehicleAreaMap = new ConcurrentHashMap<>();

    private Map<Integer, TArea> areaMap = new HashMap<>();

    private GeometryFactory factory = new GeometryFactory();

    private LocalDateTime updateTime;

    @Autowired
    private AreaMapper areaMapper;

    public void register(Session session) {
        DeviceInfo deviceInfo = SessionKey.getDeviceInfo(session);
        int vehicleId = deviceInfo.getVehicleId();

        VehicleArea[] areas = getVehicleAreas(vehicleId);
        AreaFilter areaFilter = new AreaFilter(areas);

        session.setAttribute("AreaFilter", areaFilter);
        vehicleAreaMap.put(vehicleId, areaFilter);
    }

    public void cancellation(Session session) {
        DeviceInfo deviceInfo = SessionKey.getDeviceInfo(session);
        vehicleAreaMap.remove(deviceInfo.getVehicleId());
    }

    public void updateVehicleArea() {
        int[] vehicleIds = areaMapper.findVehicleId(updateTime);
        for (int vehicleId : vehicleIds) {

            AreaFilter areaFilter = vehicleAreaMap.get(vehicleId);
            if (areaFilter != null) {

                VehicleArea[] areas = getVehicleAreas(vehicleId);
                areaFilter.updateAreas(areas);
            }
        }
    }

    private VehicleArea[] getVehicleAreas(int vehicleId) {
        int[] areaIds = areaMapper.findAreaId(vehicleId);
        if (areaIds.length == 0)
            return null;

        List<VehicleArea> temp = new ArrayList<>(areaIds.length);

        for (int areaId : areaIds) {
            TArea tArea = areaMap.get(areaId);
            if (tArea != null) {
                temp.add(new VehicleArea(vehicleId, areaId, tArea));
            }
        }
        if (temp.isEmpty())
            return null;
        return temp.toArray(new VehicleArea[temp.size()]);
    }

    public void initial() {
        this.updateTime = LocalDateTime.now();
        update(null);
    }

    public void update() {
        LocalDateTime updateTime = this.updateTime;
        this.updateTime = LocalDateTime.now();
        update(updateTime);
    }

    private void update(LocalDateTime updateTime) {
        List<AreaDO> areas = areaMapper.find(new AreaQuery().updatedAt(updateTime));
        LocalDate now = LocalDate.now();

        List<Integer> dels = new LinkedList<>();
        List<Area> adds = new LinkedList<>();

        for (AreaDO area : areas) {
            if (area.isDeleted() || !area.containsDate(now) || !area.containsWeek(now.getDayOfWeek())) {
                dels.add(area.getId());
            } else {
                try {
                    adds.add(Area.build(factory, area));
                } catch (Exception e) {
                    log.error("加载区域出错" + area, e);
                }
            }
        }

        synchronized (areaMap) {
            for (Integer id : dels) {
                areaMap.remove(id);
            }
            for (Area area : adds) {
                TArea tArea = areaMap.get(area.getId());
                if (tArea != null)
                    tArea.update(area);
                else
                    areaMap.put(area.getId(), new TArea(area));
            }
        }
    }
}