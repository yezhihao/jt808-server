package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮胎气压监测系统 0x66
 */
@ToString
@Data
@Accessors(chain = true)
public class AlarmTPMS extends Alarm {

    public static final Integer key = 102;

    @Field(length = 4, desc = "报警ID")
    private long id;
    @Field(length = 1, desc = "标志状态")
    private int state;
    @Field(length = 1, desc = "车速")
    private int speed;
    @Field(length = 2, desc = "高程")
    private int altitude;
    @Field(length = 4, desc = "纬度")
    private int latitude;
    @Field(length = 4, desc = "经度")
    private int longitude;
    @Field(length = 6, charset = "BCD", desc = "日期时间")
    private LocalDateTime alarmTime;
    @Field(length = 2, desc = "车辆状态")
    private int statusBit;

    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID(粤标)", version = 1)
    private String deviceId;
    @Field(length = 6, charset = "BCD", desc = "时间(YYMMDDHHMMSS)")
    private LocalDateTime dateTime;
    @Field(length = 1, desc = "序号(同一时间点报警的序号，从0循环累加)")
    private int sequenceNo;
    @Field(length = 1, desc = "附件数量")
    private int fileTotal;
    @Field(length = 1, desc = "预留", version = {-1, 0})
    @Field(length = 2, desc = "预留(粤标)", version = 1)
    private int reserved;

    @Field(totalUnit = 1, desc = "事件信息列表")
    private List<Item> items;

    @Override
    public int getSource() {
        return 1;
    }

    @Override
    public int getCategory() {
        return key;
    }

    @Override
    public int getAlarmType() {
        return Alarm.buildType(key, 0);
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public String getExtra() {
        return null;
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Item {
        @Field(length = 1, desc = "胎压报警位置(从左前轮开始以Z字形从00依次编号,编号与是否安装TPMS无关)")
        private int position;
        @Field(length = 2, desc = "报警类型：" +
                " 0.胎压(定时上报)" +
                " 1.胎压过高报警" +
                " 2.胎压过低报警" +
                " 3.胎温过高报警" +
                " 4.传感器异常报警" +
                " 5.胎压不平衡报警" +
                " 6.慢漏气报警" +
                " 7.电池电量低报警" +
                " 8~31.预留")
        private int type;
        @Field(length = 2, desc = "胎压(Kpa)")
        private int pressure;
        @Field(length = 2, desc = "温度(℃)")
        private int temperature;
        @Field(length = 2, desc = "电池电量(%)")
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
    }
}