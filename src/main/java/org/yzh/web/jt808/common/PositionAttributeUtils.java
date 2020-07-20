package org.yzh.web.jt808.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.web.jt808.dto.basics.BytesAttribute;
import org.yzh.web.jt808.dto.position.Attribute;
import org.yzh.web.jt808.dto.position.attribute.*;

import java.util.*;
import java.util.function.Function;

public abstract class PositionAttributeUtils {

    private static final Logger log = LoggerFactory.getLogger(PositionAttributeUtils.class.getSimpleName());

    private static final Map<Integer, Function<byte[], Attribute>> FUNCTIONS = new HashMap<>(15);

    static {
        FUNCTIONS.put(new Mileage().getAttributeId(), bytes -> new Mileage().formBytes(bytes));
        FUNCTIONS.put(new Oil().getAttributeId(), bytes -> new Oil().formBytes(bytes));
        FUNCTIONS.put(new Speed().getAttributeId(), bytes -> new Speed().formBytes(bytes));
        FUNCTIONS.put(new AlarmEventId().getAttributeId(), bytes -> new AlarmEventId().formBytes(bytes));
        FUNCTIONS.put(new TirePressure().getAttributeId(), bytes -> new TirePressure().formBytes(bytes));
        FUNCTIONS.put(new CarriageTemperature().getAttributeId(), bytes -> new CarriageTemperature().formBytes(bytes));
        FUNCTIONS.put(new OverSpeedAlarm().getAttributeId(), bytes -> new OverSpeedAlarm().formBytes(bytes));
        FUNCTIONS.put(new InOutAreaAlarm().getAttributeId(), bytes -> new InOutAreaAlarm().formBytes(bytes));
        FUNCTIONS.put(new RouteDriveTimeAlarm().getAttributeId(), bytes -> new RouteDriveTimeAlarm().formBytes(bytes));
        FUNCTIONS.put(new Signal().getAttributeId(), bytes -> new Signal().formBytes(bytes));
        FUNCTIONS.put(new IoState().getAttributeId(), bytes -> new IoState().formBytes(bytes));
        FUNCTIONS.put(new AnalogQuantity().getAttributeId(), bytes -> new AnalogQuantity().formBytes(bytes));
        FUNCTIONS.put(new SignalStrength().getAttributeId(), bytes -> new SignalStrength().formBytes(bytes));
        FUNCTIONS.put(new GnssCount().getAttributeId(), bytes -> new GnssCount().formBytes(bytes));
    }

    public static Map<Integer, Attribute> transform(List<BytesAttribute> bytesAttributes) {
        Map<Integer, Attribute> result = new TreeMap<>();
        for (BytesAttribute bytesAttribute : bytesAttributes) {
            Integer id = bytesAttribute.getId();
            Function<byte[], Attribute> function = FUNCTIONS.get(id);
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