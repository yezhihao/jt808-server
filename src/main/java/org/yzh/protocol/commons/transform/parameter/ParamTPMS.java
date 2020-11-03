package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * 胎压监测系统参数
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamTPMS {

    public static final int id = 0xF366;

    public static int id() {
        return id;
    }

    private String tireType;
    private int pressureUnit;
    private int normalValue;
    private int imbalanceThreshold;
    private int lowLeakThreshold;
    private int lowPressureThreshold;
    private int highPressureThreshold;
    private int highTemperatureThreshold;
    private int voltageThreshold;
    private int reportInterval;
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamTPMS> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
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