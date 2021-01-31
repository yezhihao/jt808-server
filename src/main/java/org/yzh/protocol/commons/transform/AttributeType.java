package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.IdStrategy;
import io.github.yezhihao.protostar.PrepareLoadStrategy;
import org.yzh.protocol.commons.transform.attribute.*;

/**
 * 位置附加信息注册
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class AttributeType extends PrepareLoadStrategy {

    public static final IdStrategy INSTANCE = new AttributeType();

    @Override
    protected void addSchemas(PrepareLoadStrategy schemaRegistry) {
        schemaRegistry
                .addSchema(AttributeId.Mileage, Mileage.Schema.INSTANCE)
                .addSchema(AttributeId.Oil, Oil.Schema.INSTANCE)
                .addSchema(AttributeId.Speed, Speed.Schema.INSTANCE)
                .addSchema(AttributeId.AlarmEventId, AlarmEventId.Schema.INSTANCE)
                .addSchema(AttributeId.TirePressure, TirePressure.Schema.INSTANCE)
                .addSchema(AttributeId.CarriageTemperature, CarriageTemperature.Schema.INSTANCE)
                .addSchema(AttributeId.OverSpeedAlarm, OverSpeedAlarm.Schema.INSTANCE)
                .addSchema(AttributeId.InOutAreaAlarm, InOutAreaAlarm.Schema.INSTANCE)
                .addSchema(AttributeId.RouteDriveTimeAlarm, RouteDriveTimeAlarm.Schema.INSTANCE)
                .addSchema(AttributeId.Signal, Signal.Schema.INSTANCE)
                .addSchema(AttributeId.IoState, IoState.Schema.INSTANCE)
                .addSchema(AttributeId.AnalogQuantity, AnalogQuantity.Schema.INSTANCE)
                .addSchema(AttributeId.SignalStrength, SignalStrength.Schema.INSTANCE)
                .addSchema(AttributeId.GnssCount, GnssCount.Schema.INSTANCE)
                .addSchema(AttributeId.AlarmADAS, AlarmADAS.class)
                .addSchema(AttributeId.AlarmBSD, AlarmBSD.class)
                .addSchema(AttributeId.AlarmDSM, AlarmDSM.class)
                .addSchema(AttributeId.AlarmTPMS, AlarmTPMS.class);
    }
}