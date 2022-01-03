package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * 胎压监测系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParamTPMS {

    public static final Schema<ParamTPMS> SCHEMA = new ParamTPMSSchema();

    public static final int id = 0xF366;

    public static int id() {
        return id;
    }

    @Field(desc = "轮胎规格型号(例：195/65R1591V,12个字符,默认值'900R20')")
    private String tireType;
    @Field(desc = "胎压单位：0.kg/cm2 1.bar 2.Kpa 3.PSI(默认值3)")
    private int pressureUnit = -1;
    @Field(desc = "正常胎压值(同胎压单位,默认值140)")
    private int normalValue = -1;
    @Field(desc = "胎压不平衡报警阈值(百分比0~100,达到冷态气压值,默认值20)")
    private int imbalanceThreshold = -1;
    @Field(desc = "慢漏气报警阈值(百分比0~100,达到冷态气压值,默认值5)")
    private int lowLeakThreshold = -1;
    @Field(desc = "低压报警阈值(同胎压单位,默认值110)")
    private int lowPressureThreshold = -1;
    @Field(desc = "高压报警阈值(同胎压单位,默认值189)")
    private int highPressureThreshold = -1;
    @Field(desc = "高温报警阈值(摄氏度,默认值80)")
    private int highTemperatureThreshold = -1;
    @Field(desc = "电压报警阈值(百分比0~100,默认值10)")
    private int voltageThreshold = -1;
    @Field(desc = "定时上报时间间隔(秒,取值0~3600,默认值60,0表示不上报)")
    private int reportInterval = -1;
    @Field(desc = "保留项")
    private byte[] reserved = new byte[6];

    public static int getId() {
        return id;
    }

    public String getTireType() {
        return tireType;
    }

    public void setTireType(String tireType) {
        this.tireType = tireType;
    }

    public int getPressureUnit() {
        return pressureUnit;
    }

    public void setPressureUnit(int pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public int getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(int normalValue) {
        this.normalValue = normalValue;
    }

    public int getImbalanceThreshold() {
        return imbalanceThreshold;
    }

    public void setImbalanceThreshold(int imbalanceThreshold) {
        this.imbalanceThreshold = imbalanceThreshold;
    }

    public int getLowLeakThreshold() {
        return lowLeakThreshold;
    }

    public void setLowLeakThreshold(int lowLeakThreshold) {
        this.lowLeakThreshold = lowLeakThreshold;
    }

    public int getLowPressureThreshold() {
        return lowPressureThreshold;
    }

    public void setLowPressureThreshold(int lowPressureThreshold) {
        this.lowPressureThreshold = lowPressureThreshold;
    }

    public int getHighPressureThreshold() {
        return highPressureThreshold;
    }

    public void setHighPressureThreshold(int highPressureThreshold) {
        this.highPressureThreshold = highPressureThreshold;
    }

    public int getHighTemperatureThreshold() {
        return highTemperatureThreshold;
    }

    public void setHighTemperatureThreshold(int highTemperatureThreshold) {
        this.highTemperatureThreshold = highTemperatureThreshold;
    }

    public int getVoltageThreshold() {
        return voltageThreshold;
    }

    public void setVoltageThreshold(int voltageThreshold) {
        this.voltageThreshold = voltageThreshold;
    }

    public int getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(int reportInterval) {
        this.reportInterval = reportInterval;
    }

    public byte[] getReserved() {
        return reserved;
    }

    public void setReserved(byte[] reserved) {
        this.reserved = reserved;
    }

    private static class ParamTPMSSchema implements Schema<ParamTPMS> {

        private ParamTPMSSchema() {
        }

        @Override
        public ParamTPMS readFrom(ByteBuf input) {
            ParamTPMS message = new ParamTPMS();
            message.tireType = input.readCharSequence(12, StandardCharsets.US_ASCII).toString();
            message.pressureUnit = input.readShort();
            message.normalValue = input.readShort();
            message.imbalanceThreshold = input.readShort();
            message.lowLeakThreshold = input.readShort();
            message.lowPressureThreshold = input.readShort();
            message.highPressureThreshold = input.readShort();
            message.highTemperatureThreshold = input.readShort();
            message.voltageThreshold = input.readShort();
            message.reportInterval = input.readShort();
            input.readBytes(message.reserved);
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamTPMS message) {
            byte[] bytes = message.getTireType().getBytes(StandardCharsets.US_ASCII);
            ByteBufUtils.writeFixedLength(output, 12, bytes);
            output.writeShort(message.pressureUnit);
            output.writeShort(message.normalValue);
            output.writeShort(message.imbalanceThreshold);
            output.writeShort(message.lowLeakThreshold);
            output.writeShort(message.lowPressureThreshold);
            output.writeShort(message.highPressureThreshold);
            output.writeShort(message.highTemperatureThreshold);
            output.writeShort(message.voltageThreshold);
            output.writeShort(message.reportInterval);
            ByteBufUtils.writeFixedLength(output, 6, message.getReserved());
        }
    }
}