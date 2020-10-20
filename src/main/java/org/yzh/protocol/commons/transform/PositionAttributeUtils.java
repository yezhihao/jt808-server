package org.yzh.protocol.commons.transform;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.commons.transform.attribute.*;

import java.util.*;
import java.util.function.Function;

/**
 * 位置附加信息转换
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class PositionAttributeUtils {

    private static final Logger log = LoggerFactory.getLogger(PositionAttributeUtils.class.getSimpleName());

    private static final Map<Integer, Function<byte[], Attribute>> MAPPER;

    static {
        Map<Integer, Function<byte[], Attribute>> map = new HashMap(15);
        map.put(Mileage.attributeId,            /**/bytes -> new Mileage().formBytes(bytes));
        map.put(Oil.attributeId,                /**/bytes -> new Oil().formBytes(bytes));
        map.put(Speed.attributeId,              /**/bytes -> new Speed().formBytes(bytes));
        map.put(AlarmEventId.attributeId,       /**/bytes -> new AlarmEventId().formBytes(bytes));
        map.put(TirePressure.attributeId,       /**/bytes -> new TirePressure().formBytes(bytes));
        map.put(CarriageTemperature.attributeId,/**/bytes -> new CarriageTemperature().formBytes(bytes));
        map.put(OverSpeedAlarm.attributeId,     /**/bytes -> new OverSpeedAlarm().formBytes(bytes));
        map.put(InOutAreaAlarm.attributeId,     /**/bytes -> new InOutAreaAlarm().formBytes(bytes));
        map.put(RouteDriveTimeAlarm.attributeId,/**/bytes -> new RouteDriveTimeAlarm().formBytes(bytes));
        map.put(Signal.attributeId,             /**/bytes -> new Signal().formBytes(bytes));
        map.put(IoState.attributeId,            /**/bytes -> new IoState().formBytes(bytes));
        map.put(AnalogQuantity.attributeId,     /**/bytes -> new AnalogQuantity().formBytes(bytes));
        map.put(SignalStrength.attributeId,     /**/bytes -> new SignalStrength().formBytes(bytes));
        map.put(GnssCount.attributeId,          /**/bytes -> new GnssCount().formBytes(bytes));

        map.put(AlarmADAS.attributeId,          /**/bytes -> new AlarmADAS().formBytes(bytes));
        map.put(AlarmBSD.attributeId,          /**/bytes -> new AlarmBSD().formBytes(bytes));
        map.put(AlarmDSM.attributeId,          /**/bytes -> new AlarmDSM().formBytes(bytes));
        map.put(AlarmTPMS.attributeId,          /**/bytes -> new AlarmTPMS().formBytes(bytes));
        MAPPER = Collections.unmodifiableMap(map);
    }

    public static Map<Integer, Attribute> transform(List<BytesAttribute> bytesAttributes) {
        if (bytesAttributes == null)
            return null;
        Map<Integer, Attribute> result = new TreeMap<>();
        for (BytesAttribute bytesAttribute : bytesAttributes) {
            Integer id = bytesAttribute.getId();
            Function<byte[], Attribute> function = MAPPER.get(id);
            if (function != null) {
                try {
                    Attribute attribute = function.apply(bytesAttribute.getValue());
                    result.put(attribute.getAttributeId(), attribute);
                } catch (Exception e) {
                    log.error(bytesAttribute.toString());
                    log.error("set attributes error", e);
                }
            }
        }
        return result;
    }

    public static List<BytesAttribute> transform(Map<Integer, Attribute> attributes) {
        if (attributes == null)
            return null;
        List<BytesAttribute> result = new ArrayList<>(attributes.size());
        for (Attribute attribute : attributes.values()) {
            try {
                Integer id = attribute.getAttributeId();
                byte[] bytes = attribute.toBytes();
                result.add(new BytesAttribute(id, bytes));
            } catch (Exception e) {
                log.error(ReflectionToStringBuilder.toString(attribute, ToStringStyle.SHORT_PREFIX_STYLE));
                log.error("set bytesAttributes error", e);
            }
        }
        return result;
    }
}