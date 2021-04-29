package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮胎气压监测系统
 */
public class AlarmTPMS {

    public static final int id = 0x66;

    public static int id() {
        return id;
    }

    private long serialNo;
    private int state;
    private int speed;
    private int altitude;
    private int latitude;
    private int longitude;
    private LocalDateTime dateTime;
    private int status;
    private AlarmId alarmId;
    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.DWORD, desc = "报警ID")
    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "标志状态")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Field(index = 5, type = DataType.BYTE, desc = "车速")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 6, type = DataType.WORD, desc = "高程")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Field(index = 8, type = DataType.DWORD, desc = "纬度")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Field(index = 12, type = DataType.DWORD, desc = "经度")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Field(index = 16, type = DataType.BCD8421, length = 6, desc = "日期时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 22, type = DataType.WORD, desc = "车辆状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 24, type = DataType.OBJ, length = 16, desc = "报警标识号")
    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Field(index = 39, type = DataType.BYTE, desc = "数据项个数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 40, type = DataType.LIST, desc = "区域列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        if (items != null) {
            this.items = items;
            this.total = items.size();
        }
    }

    public static class Item {
        private int position;
        private int type;
        private int pressure;
        private int temperature;
        private int batteryLevel;

        public Item() {
        }

        public Item(int position, int type, int pressure, int temperature, int batteryLevel) {
            this.position = position;
            this.type = type;
            this.pressure = pressure;
            this.temperature = temperature;
            this.batteryLevel = batteryLevel;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "胎压报警位置")
        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Field(index = 2, type = DataType.DWORD, desc = "报警类型")
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Field(index = 4, type = DataType.DWORD, desc = "胎压")
        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        @Field(index = 6, type = DataType.DWORD, desc = "温度")
        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        @Field(index = 8, type = DataType.DWORD, desc = "电池电量")
        public int getBatteryLevel() {
            return batteryLevel;
        }

        public void setBatteryLevel(int batteryLevel) {
            this.batteryLevel = batteryLevel;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Item{");
            sb.append("position=").append(position);
            sb.append(", type=").append(type);
            sb.append(", pressure=").append(pressure);
            sb.append(", temperature=").append(temperature);
            sb.append(", batteryLevel=").append(batteryLevel);
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlarmTPMS{");
        sb.append("serialNo=").append(serialNo);
        sb.append(", state=").append(state);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", status=").append(status);
        sb.append(", alarmId=").append(alarmId);
        sb.append(", total=").append(total);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}