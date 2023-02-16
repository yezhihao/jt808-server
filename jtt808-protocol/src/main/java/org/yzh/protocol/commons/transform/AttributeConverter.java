package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.PrepareLoadStrategy;
import io.github.yezhihao.protostar.ProtostarUtil;
import io.github.yezhihao.protostar.schema.MapSchema;
import io.github.yezhihao.protostar.schema.NumberSchema;
import org.yzh.protocol.commons.transform.attribute.*;

/**
 * 位置附加信息转换器(苏标)
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class AttributeConverter extends MapSchema<Number, Object> {

    public AttributeConverter() {
        super(NumberSchema.BYTE_INT, 1);
    }

    @Override
    protected void addSchemas(PrepareLoadStrategy<Number> schemaRegistry) {
        schemaRegistry
                .addSchema(AttributeKey.Mileage, NumberSchema.DWORD_LONG)
                .addSchema(AttributeKey.Fuel, NumberSchema.WORD_INT)
                .addSchema(AttributeKey.Speed, NumberSchema.WORD_INT)
                .addSchema(AttributeKey.AlarmEventId, NumberSchema.WORD_INT)
                .addSchema(AttributeKey.TirePressure, TirePressure.SCHEMA)
                .addSchema(AttributeKey.CarriageTemperature, NumberSchema.WORD_SHORT)

                .addSchema(AttributeKey.OverSpeedAlarm, OverSpeedAlarm.SCHEMA)
                .addSchema(AttributeKey.InOutAreaAlarm, InOutAreaAlarm.SCHEMA)
                .addSchema(AttributeKey.RouteDriveTimeAlarm, RouteDriveTimeAlarm.SCHEMA)

                .addSchema(AttributeKey.Signal, NumberSchema.DWORD_INT)
                .addSchema(AttributeKey.IoState, NumberSchema.WORD_INT)
                .addSchema(AttributeKey.AnalogQuantity, NumberSchema.DWORD_INT)
                .addSchema(AttributeKey.SignalStrength, NumberSchema.BYTE_INT)
                .addSchema(AttributeKey.GnssCount, NumberSchema.BYTE_INT)


                .addSchema(AttributeKey.AlarmADAS, ProtostarUtil.getRuntimeSchema(AlarmADAS.class, 0))
                .addSchema(AttributeKey.AlarmBSD, ProtostarUtil.getRuntimeSchema(AlarmBSD.class, 0))
                .addSchema(AttributeKey.AlarmDSM, ProtostarUtil.getRuntimeSchema(AlarmDSM.class, 0))
                .addSchema(AttributeKey.AlarmTPMS, ProtostarUtil.getRuntimeSchema(AlarmTPMS.class, 0))
        ;
    }
}