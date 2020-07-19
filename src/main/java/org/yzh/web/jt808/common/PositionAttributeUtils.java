package org.yzh.web.jt808.common;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.web.jt808.dto.basics.PositionAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class PositionAttributeUtils {
    public static int Mileage = 0x01;//4 里程，DWORD，1/10km，对应车上里程表读数
    public static int Oil = 0x02;//2 油量，WORD，1/10L，对应车上油量表读数
    public static int Speed = 0x03;//2 行驶记录功能获取的速度，WORD，1/10km/h
    public static int AlarmEventId = 0x04;//2 需要人工确认报警事件的 ID，WORD，从 1 开始计数
    public static int TirePressure = 0x05;//30 胎压
    public static int CarriageTemperature = 0x06;//2 车厢温度
    //0x07-0x10  保留
    public static int OverSpeedAlarm = 0x11;//1 或 5 超速报警附加信息见表 28
    public static int InOutAreaAlarm = 0x12;//6 进出区域/路线报警附加信息见表 29
    public static int RouteDriveTimeAlarm = 0x13;//7 路段行驶时间不足/过长报警附加信息见表 30
    //0x14-0x24  保留
    public static int Signal = 0x25;//4 扩展车辆信号状态位，定义见表 31
    public static int IO = 0x2A;//2 IO 状态位，定义见表 32
    public static int AnalogQuantity = 0x2B;//4 模拟量，bit0-15，AD0；bit16-31，AD1。
    public static int SignalStrength = 0x30;//1 BYTE，无线通信网络信号强度
    public static int GnssCount = 0x31;//1 BYTE，GNSS 定位卫星数

    private static final Map<Integer, Function<byte[], Object[]>> FUNCTIONS = new HashMap<>(18);

    static {
        FUNCTIONS.put(Mileage, bytes -> new Object[]{"里程", Bit.toUInt32(bytes, 0)});
        FUNCTIONS.put(Oil, bytes -> new Object[]{"油量", Bit.toUInt16(bytes, 0)});
        FUNCTIONS.put(Speed, bytes -> new Object[]{"速度", Bit.toUInt16(bytes, 0)});
        FUNCTIONS.put(AlarmEventId, bytes -> new Object[]{"报警事件ID", Bit.toUInt16(bytes, 0)});
        FUNCTIONS.put(TirePressure, bytes -> new Object[]{"胎压", bytes});
        FUNCTIONS.put(CarriageTemperature, bytes -> new Object[]{"车厢温度", Bit.toUInt16(bytes, 0)});
        FUNCTIONS.put(OverSpeedAlarm, bytes -> new Object[]{"超速报警", new OverSpeedAlarm(bytes)});
        FUNCTIONS.put(InOutAreaAlarm, bytes -> new Object[]{"进出区域、路线报警", new InOutAreaAlarm(bytes)});
        FUNCTIONS.put(RouteDriveTimeAlarm, bytes -> new Object[]{"行驶时间报警", new RouteDriveTimeAlarm(bytes)});
        FUNCTIONS.put(Signal, bytes -> new Object[]{"车辆信号状态", Bit.toUInt32(bytes, 0)});
        FUNCTIONS.put(IO, bytes -> new Object[]{"IO状态", Bit.toUInt16(bytes, 0)});
        FUNCTIONS.put(AnalogQuantity, bytes -> new Object[]{"模拟量", 4});
        FUNCTIONS.put(SignalStrength, bytes -> new Object[]{"信号强度", bytes[0]});
        FUNCTIONS.put(GnssCount, bytes -> new Object[]{"定位星数", bytes[0]});
    }

    public static void fillValue(List<PositionAttribute> positionAttributes) {
        for (PositionAttribute attribute : positionAttributes) {
            Integer id = attribute.getId();
            Function<byte[], Object[]> function = FUNCTIONS.get(id);
            if (function != null) {
                Object[] objects = function.apply(attribute.getBytesValue());
                attribute.setName((String) objects[0]);
                attribute.setValue(objects[1]);
            }
        }
    }

    public static class OverSpeedAlarm {
        /** 位置类型 */
        private byte positionType;
        /** 区域或路段ID */
        private int areaId;

        public OverSpeedAlarm() {
        }

        public OverSpeedAlarm(byte[] bytes) {
            this.positionType = bytes[0];
            if (bytes.length > 1)
                this.areaId = Bit.toUInt32(bytes, 1);
        }

        public byte getPositionType() {
            return positionType;
        }

        public void setPositionType(byte positionType) {
            this.positionType = positionType;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }
    }

    public static class RouteDriveTimeAlarm {
        /** 路段ID */
        private int routeId;
        /** 行驶时间,单位为秒(s) */
        private int driveTime;
        /** 结果,0: 不足,1:过长 */
        private byte result;

        public RouteDriveTimeAlarm() {
        }

        public RouteDriveTimeAlarm(byte[] bytes) {
            this.routeId = Bit.toUInt32(bytes, 0);
            this.driveTime = Bit.toUInt16(bytes, 0);
            this.result = bytes[5];
        }

        public int getRouteId() {
            return routeId;
        }

        public void setRouteId(int routeId) {
            this.routeId = routeId;
        }

        public int getDriveTime() {
            return driveTime;
        }

        public void setDriveTime(int driveTime) {
            this.driveTime = driveTime;
        }

        public byte getResult() {
            return result;
        }

        public void setResult(byte result) {
            this.result = result;
        }
    }


    public static class InOutAreaAlarm {
        /** 位置类型 */
        private byte positionType;
        /** 区域或路段ID */
        private int areaId;
        /** 方向,0:进,1:出 */
        private byte direction;

        public InOutAreaAlarm() {
        }

        public InOutAreaAlarm(byte[] bytes) {
            this.positionType = bytes[0];
            this.areaId = Bit.toUInt32(bytes, 1);
            this.direction = bytes[5];
        }

        public byte getPositionType() {
            return positionType;
        }

        public void setPositionType(byte positionType) {
            this.positionType = positionType;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public byte getDirection() {
            return direction;
        }

        public void setDirection(byte direction) {
            this.direction = direction;
        }
    }
}