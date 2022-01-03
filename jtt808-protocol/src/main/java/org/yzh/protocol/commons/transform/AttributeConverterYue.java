package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.PrepareLoadStrategy;
import io.github.yezhihao.protostar.ProtostarUtil;
import io.github.yezhihao.protostar.schema.MapSchema;
import io.github.yezhihao.protostar.schema.NumberSchema;
import org.yzh.protocol.commons.transform.attribute.*;

/**
 * 位置附加信息转换器(粤标)
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class AttributeConverterYue extends MapSchema<Integer, Object> {

    public AttributeConverterYue() {
        super(NumberSchema.BYTE_INT, 1);
    }

    @Override
    protected void addSchemas(PrepareLoadStrategy<Integer> schemaRegistry) {
        schemaRegistry
                .addSchema(AttributeId.Mileage, NumberSchema.DWORD_LONG)
                .addSchema(AttributeId.Gas, NumberSchema.WORD_INT)
                .addSchema(AttributeId.Speed, NumberSchema.WORD_INT)
                .addSchema(AttributeId.AlarmEventId, NumberSchema.WORD_INT)
                .addSchema(AttributeId.TirePressure, TirePressure.SCHEMA)
                .addSchema(AttributeId.CarriageTemperature, NumberSchema.WORD_INT)

                .addSchema(AttributeId.OverSpeedAlarm, OverSpeedAlarm.SCHEMA)
                .addSchema(AttributeId.InOutAreaAlarm, InOutAreaAlarm.SCHEMA)
                .addSchema(AttributeId.RouteDriveTimeAlarm, RouteDriveTimeAlarm.SCHEMA)

                .addSchema(AttributeId.Signal, NumberSchema.DWORD_LONG)
                .addSchema(AttributeId.IoState, NumberSchema.WORD_INT)
                .addSchema(AttributeId.AnalogQuantity, NumberSchema.DWORD_LONG)
                .addSchema(AttributeId.SignalStrength, NumberSchema.BYTE_INT)
                .addSchema(AttributeId.GnssCount, NumberSchema.BYTE_INT)


                .addSchema(AttributeId.AlarmADAS, ProtostarUtil.getRuntimeSchema(AlarmADAS.class, 1))
                .addSchema(AttributeId.AlarmBSD, ProtostarUtil.getRuntimeSchema(AlarmBSD.class, 1))
                .addSchema(AttributeId.AlarmDSM, ProtostarUtil.getRuntimeSchema(AlarmDSM.class, 1))
                .addSchema(AttributeId.AlarmTPMS, ProtostarUtil.getRuntimeSchema(AlarmTPMS.class, 1))
        ;
    }
}