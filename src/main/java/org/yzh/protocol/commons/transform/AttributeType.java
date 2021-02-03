package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.DataType;
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
                .addSchema(AttributeId.Mileage, DataType.DWORD)
                .addSchema(AttributeId.Gas, DataType.WORD)
                .addSchema(AttributeId.Speed, DataType.WORD)
                .addSchema(AttributeId.AlarmEventId, DataType.WORD)
                .addSchema(AttributeId.TirePressure, TirePressure.Schema.INSTANCE)
                .addSchema(AttributeId.CarriageTemperature, DataType.WORD)

                .addSchema(AttributeId.OverSpeedAlarm, OverSpeedAlarm.Schema.INSTANCE)
                .addSchema(AttributeId.InOutAreaAlarm, InOutAreaAlarm.Schema.INSTANCE)
                .addSchema(AttributeId.RouteDriveTimeAlarm, RouteDriveTimeAlarm.Schema.INSTANCE)

                .addSchema(AttributeId.Signal, DataType.DWORD)
                .addSchema(AttributeId.IoState, DataType.WORD)
                .addSchema(AttributeId.AnalogQuantity, DataType.DWORD)
                .addSchema(AttributeId.SignalStrength, DataType.BYTE)
                .addSchema(AttributeId.GnssCount, DataType.BYTE)

                .addSchema(AttributeId.AlarmADAS, AlarmADAS.class)
                .addSchema(AttributeId.AlarmBSD, AlarmBSD.class)
                .addSchema(AttributeId.AlarmDSM, AlarmDSM.class)
                .addSchema(AttributeId.AlarmTPMS, AlarmTPMS.class)
        ;
    }
}