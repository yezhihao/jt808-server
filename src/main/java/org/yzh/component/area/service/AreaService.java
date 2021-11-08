package org.yzh.component.area.service;

import io.github.yezhihao.netmc.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private AreaMapper areaMapper;

    public void register(Session session) {
        DeviceInfo deviceInfo = SessionKey.getDeviceInfo(session);
        int vehicleId = deviceInfo.getVehicleId();

        VehicleArea[] areas = getVehicleAreas(vehicleId);
        AreaFilter areaFilter = new AreaFilter().updateAreas(areas);
        log.warn("车辆区域绑定,车辆[{}],区域{}", vehicleId, areas == null ? "[]" : Arrays.toString(Arrays.stream(areas).mapToInt(v -> v.getArea().getId()).toArray()));

        session.setAttribute(SessionKey.AreaFilter, areaFilter);
        vehicleAreaMap.put(vehicleId, areaFilter);
    }

    public void cancellation(Session session) {
        DeviceInfo deviceInfo = SessionKey.getDeviceInfo(session);
        AreaFilter areaFilter = vehicleAreaMap.remove(deviceInfo.getVehicleId());
        VehicleArea[] areas = areaFilter.getAreas();
        log.warn("车辆区域解绑,车辆[{}],区域{}", deviceInfo.getVehicleId(), areas == null ? "[]" : Arrays.toString(Arrays.stream(areas).mapToInt(v -> v.getArea().getId()).toArray()));
    }

    private LocalDateTime lastUpdateTime;

    @Scheduled(fixedDelay = 10000L)
    public void update() {
        final LocalDateTime temp = lastUpdateTime;
        this.lastUpdateTime = LocalDateTime.now();
        updateAreas(temp);
        updateVehicleAreas(temp);
    }

    private synchronized void updateAreas(LocalDateTime updateTime) {
        List<AreaDO> areas = areaMapper.find(new AreaQuery().updatedAt(updateTime));
        if (areas.isEmpty())
            return;

        LocalDate now = LocalDate.now();
        List<Integer> dels = new LinkedList<>();
        List<Area> adds = new LinkedList<>();

        for (AreaDO area : areas) {
            if (area.isDeleted() || area.getWeeks() == 0 || (area.getEndDate() != null && now.isAfter(area.getEndDate()))) {
                dels.add(area.getId());
            } else {
                try {
                    adds.add(Area.build(area, factory));
                } catch (Exception e) {
                    log.error("加载区域出错[{}],{}", area, e.getMessage());
                }
            }
        }

        synchronized (areaMap) {
            for (Integer id : dels) {
                areaMap.remove(id);
            }
            for (Area area : adds) {
                TArea TArea = areaMap.get(area.getId());
                if (TArea == null)
                    areaMap.put(area.getId(), TArea = new TArea());
                TArea.set(area);
            }
        }

        log.warn("区域更新\n移除区域{}\n加载区域{}\n当前区域{}",
                Arrays.toString(dels.stream().mapToInt(e -> e).toArray()),
                Arrays.toString(adds.stream().mapToInt(e -> e.getId()).toArray()),
                Arrays.toString(areaMap.keySet().stream().mapToInt(k -> k).toArray())
        );
    }

    private synchronized void updateVehicleAreas(LocalDateTime updateTime) {
        int[] vehicleIds = areaMapper.findVehicleId(updateTime);
        for (int vehicleId : vehicleIds) {

            AreaFilter areaFilter = vehicleAreaMap.get(vehicleId);
            if (areaFilter != null) {

                VehicleArea[] areas = getVehicleAreas(vehicleId);
                areaFilter.updateAreas(areas);
                log.warn("车辆区域绑定,车辆[{}],区域{}", vehicleId, areas == null ? "[]" : Arrays.toString(Arrays.stream(areas).mapToInt(v -> v.getArea().getId()).toArray()));
            }
        }
    }

    private VehicleArea[] getVehicleAreas(int vehicleId) {
        int[] areaIds = areaMapper.findAreaId(vehicleId);
        log.warn("车辆区域查询,车辆[{}],区域{}", vehicleId, Arrays.toString(areaIds));
        if (areaIds.length == 0)
            return null;

        List<VehicleArea> temp = new ArrayList<>(areaIds.length);

        for (int areaId : areaIds) {
            TArea area = areaMap.get(areaId);
            if (area != null) {
                temp.add(new VehicleArea(area));
            }
        }
        if (temp.isEmpty())
            return null;

        return temp.toArray(new VehicleArea[temp.size()]);
    }
}