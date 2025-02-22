package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 胎压监测系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamTPMS {

    public static final Integer key = 0xF366;

    @Field(length = 12, desc = "轮胎规格型号(例：195/65R1591V,12个字符,默认值'900R20')")
    private String tireType;
    @Field(length = 2, desc = "胎压单位：0.kg/cm2 1.bar 2.Kpa 3.PSI(默认值3)")
    private int pressureUnit = -1;
    @Field(length = 2, desc = "正常胎压值(同胎压单位,默认值140)")
    private int normalValue = -1;
    @Field(length = 2, desc = "胎压不平衡报警阈值(百分比0~100,达到冷态气压值,默认值20)")
    private int imbalanceThreshold = -1;
    @Field(length = 2, desc = "慢漏气报警阈值(百分比0~100,达到冷态气压值,默认值5)")
    private int lowLeakThreshold = -1;
    @Field(length = 2, desc = "低压报警阈值(同胎压单位,默认值110)")
    private int lowPressureThreshold = -1;
    @Field(length = 2, desc = "高压报警阈值(同胎压单位,默认值189)")
    private int highPressureThreshold = -1;
    @Field(length = 2, desc = "高温报警阈值(摄氏度,默认值80)")
    private int highTemperatureThreshold = -1;
    @Field(length = 2, desc = "电压报警阈值(百分比0~100,默认值10)")
    private int voltageThreshold = -1;
    @Field(length = 2, desc = "定时上报时间间隔(秒,取值0~3600,默认值60,0表示不上报)")
    private int reportInterval = -1;
    @Field(length = 6, desc = "保留项")
    private byte[] reserved = new byte[6];

}