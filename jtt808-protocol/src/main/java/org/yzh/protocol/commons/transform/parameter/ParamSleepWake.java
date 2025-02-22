package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalTime;

/**
 * 终端休眠唤醒模式设置数据格式
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamSleepWake {

    public static final Integer key = 0x007C;

    @Field(length = 1, desc = "休眠唤醒模式：[0]条件唤醒 [1]定时唤醒 [2]手动唤醒")
    private int mode;
    @Field(length = 1, desc = "唤醒条件类型：[0]紧急报警 [1]碰撞侧翻报警 [2]车辆开门,休眠唤醒模式中[0]为1时此字段有效,否则置0")
    private int conditionType;
    @Field(length = 1, desc = "周定时唤醒日设置：[0]周一 [1]周二 [2]周三 [3]周四 [4]周五 [5]周六 [6]周日")
    private int dayOfWeek;
    @Field(length = 1, desc = "日定时唤醒启用标志：[0]启用时间段1 [1]启用时间段2 [2]启用时间段3 [3]启用时间段4)")
    private int timeFlag;
    @Field(length = 2, charset = "BCD", desc = "时间段1唤醒时间")
    private LocalTime wakeTime1 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段1关闭时间")
    private LocalTime sleepTime1 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段2唤醒时间")
    private LocalTime wakeTime2 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段2关闭时间")
    private LocalTime sleepTime2 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段3唤醒时间")
    private LocalTime wakeTime3 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段3关闭时间")
    private LocalTime sleepTime3 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段4唤醒时间")
    private LocalTime wakeTime4 = LocalTime.MIN;
    @Field(length = 2, charset = "BCD", desc = "时间段4关闭时间")
    private LocalTime sleepTime4 = LocalTime.MIN;

}