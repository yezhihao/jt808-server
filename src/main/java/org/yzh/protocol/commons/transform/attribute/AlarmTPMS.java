package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮胎气压监测系统
 */
public class AlarmTPMS implements Alarm {

    public static final int id = 0x66;

    public static int id() {
        return id;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "报警ID")
    private long serialNo;
    @Field(index = 4, type = DataType.BYTE, desc = "标志状态")
    private int state;
    @Field(index = 5, type = DataType.BYTE, desc = "车速")
    private int speed;
    @Field(index = 6, type = DataType.WORD, desc = "高程")
    private int altitude;
    @Field(index = 8, type = DataType.DWORD, desc = "纬度")
    private int latitude;
    @Field(index = 12, type = DataType.DWORD, desc = "经度")
    private int longitude;
    @Field(index = 16, type = DataType.BCD8421, length = 6, desc = "日期时间")
    private LocalDateTime dateTime;
    @Field(index = 22, type = DataType.WORD, desc = "车辆状态")
    private int status;
    @Field(index = 24, type = DataType.OBJ, length = 16, desc = "报警标识号", version = {-1, 0})
    @Field(index = 24, type = DataType.OBJ, length = 40, desc = "报警标识号(粤标)", version = 1)
    private AlarmId alarmId;
    @Field(index = 39, type = DataType.BYTE, desc = "数据项个数")
    private int total;
    @Field(index = 40, type = DataType.LIST, desc = "区域列表")
    private List<Item> items;

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
        @Field(index = 0, type = DataType.BYTE, desc = "胎压报警位置")
        private int position;
        @Field(index = 2, type = DataType.DWORD, desc = "报警类型")
        private int type;
        @Field(index = 4, type = DataType.DWORD, desc = "胎压")
        private int pressure;
        @Field(index = 6, type = DataType.DWORD, desc = "温度")
        private int temperature;
        @Field(index = 8, type = DataType.DWORD, desc = "电池电量")
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

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

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
        final StringBuilder sb = new StringBuilder(400);
        sb.append("AlarmTPMS{serialNo=").append(serialNo);
        sb.append(", state=").append(state);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", status=").append(status);
        sb.append(", alarmId=").append(alarmId);
        sb.append(", total=").append(total);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}