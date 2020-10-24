package org.yzh.protocol.commons.transform;

import org.yzh.framework.orm.IdStrategy;
import org.yzh.framework.orm.PrepareLoadStrategy;
import org.yzh.protocol.commons.transform.attribute.*;

/**
 * 位置附件信息注册
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class AttributeType extends PrepareLoadStrategy {

    public static final IdStrategy INSTANCE = new AttributeType();

    @Override
    protected void addSchemas(PrepareLoadStrategy schemaRegistry) {
        schemaRegistry
                .addSchema(Mileage.attributeId, Mileage.Schema.INSTANCE)
                .addSchema(Oil.attributeId, Oil.Schema.INSTANCE)
                .addSchema(Speed.attributeId, Speed.Schema.INSTANCE)
                .addSchema(AlarmEventId.attributeId, AlarmEventId.Schema.INSTANCE)
                .addSchema(TirePressure.attributeId, TirePressure.Schema.INSTANCE)
                .addSchema(CarriageTemperature.attributeId, CarriageTemperature.Schema.INSTANCE)
                .addSchema(OverSpeedAlarm.attributeId, OverSpeedAlarm.Schema.INSTANCE)
                .addSchema(InOutAreaAlarm.attributeId, InOutAreaAlarm.Schema.INSTANCE)
                .addSchema(RouteDriveTimeAlarm.attributeId, RouteDriveTimeAlarm.Schema.INSTANCE)
                .addSchema(Signal.attributeId, Signal.Schema.INSTANCE)
                .addSchema(IoState.attributeId, IoState.Schema.INSTANCE)
                .addSchema(AnalogQuantity.attributeId, AnalogQuantity.Schema.INSTANCE)
                .addSchema(SignalStrength.attributeId, SignalStrength.Schema.INSTANCE)
                .addSchema(GnssCount.attributeId, GnssCount.Schema.INSTANCE)
                .addSchema(AlarmADAS.attributeId, AlarmADAS.class)
                .addSchema(AlarmBSD.attributeId, AlarmBSD.class)
                .addSchema(AlarmDSM.attributeId, AlarmDSM.class)
                .addSchema(AlarmTPMS.attributeId, AlarmTPMS.class);
    }
}