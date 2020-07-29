package org.yzh.protocol.commons;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.commons.additional.Attribute;
import org.yzh.protocol.commons.additional.attribute.*;

import java.util.*;
import java.util.function.Function;

/**
 * 位置附加信息转换
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public abstract class PositionAttributeUtils {

    private static final Logger log = LoggerFactory.getLogger(PositionAttributeUtils.class.getSimpleName());

    private static final Map<Integer, Function<byte[], Attribute>> FUNCTIONS = new HashMap<>(15);

    static {
        FUNCTIONS.put(Mileage.attributeId, bytes -> new Mileage().formBytes(bytes));
        FUNCTIONS.put(Oil.attributeId, bytes -> new Oil().formBytes(bytes));
        FUNCTIONS.put(Speed.attributeId, bytes -> new Speed().formBytes(bytes));
        FUNCTIONS.put(AlarmEventId.attributeId, bytes -> new AlarmEventId().formBytes(bytes));
        FUNCTIONS.put(TirePressure.attributeId, bytes -> new TirePressure().formBytes(bytes));
        FUNCTIONS.put(CarriageTemperature.attributeId, bytes -> new CarriageTemperature().formBytes(bytes));
        FUNCTIONS.put(OverSpeedAlarm.attributeId, bytes -> new OverSpeedAlarm().formBytes(bytes));
        FUNCTIONS.put(InOutAreaAlarm.attributeId, bytes -> new InOutAreaAlarm().formBytes(bytes));
        FUNCTIONS.put(RouteDriveTimeAlarm.attributeId, bytes -> new RouteDriveTimeAlarm().formBytes(bytes));
        FUNCTIONS.put(Signal.attributeId, bytes -> new Signal().formBytes(bytes));
        FUNCTIONS.put(IoState.attributeId, bytes -> new IoState().formBytes(bytes));
        FUNCTIONS.put(AnalogQuantity.attributeId, bytes -> new AnalogQuantity().formBytes(bytes));
        FUNCTIONS.put(SignalStrength.attributeId, bytes -> new SignalStrength().formBytes(bytes));
        FUNCTIONS.put(GnssCount.attributeId, bytes -> new GnssCount().formBytes(bytes));
    }

    public static Map<Integer, Attribute> transform(List<BytesAttribute> bytesAttributes) {
        if (bytesAttributes == null)
            return null;
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