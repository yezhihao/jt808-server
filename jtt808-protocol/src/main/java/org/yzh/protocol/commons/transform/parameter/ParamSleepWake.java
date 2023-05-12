package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;

import java.time.LocalTime;

/**
 * 终端休眠唤醒模式设置数据格式
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(int timeFlag) {
        this.timeFlag = timeFlag;
    }

    public LocalTime getWakeTime1() {
        return wakeTime1;
    }

    public void setWakeTime1(LocalTime wakeTime1) {
        this.wakeTime1 = wakeTime1;
    }

    public LocalTime getSleepTime1() {
        return sleepTime1;
    }

    public void setSleepTime1(LocalTime sleepTime1) {
        this.sleepTime1 = sleepTime1;
    }

    public LocalTime getWakeTime2() {
        return wakeTime2;
    }

    public void setWakeTime2(LocalTime wakeTime2) {
        this.wakeTime2 = wakeTime2;
    }

    public LocalTime getSleepTime2() {
        return sleepTime2;
    }

    public void setSleepTime2(LocalTime sleepTime2) {
        this.sleepTime2 = sleepTime2;
    }

    public LocalTime getWakeTime3() {
        return wakeTime3;
    }

    public void setWakeTime3(LocalTime wakeTime3) {
        this.wakeTime3 = wakeTime3;
    }

    public LocalTime getSleepTime3() {
        return sleepTime3;
    }

    public void setSleepTime3(LocalTime sleepTime3) {
        this.sleepTime3 = sleepTime3;
    }

    public LocalTime getWakeTime4() {
        return wakeTime4;
    }

    public void setWakeTime4(LocalTime wakeTime4) {
        this.wakeTime4 = wakeTime4;
    }

    public LocalTime getSleepTime4() {
        return sleepTime4;
    }

    public void setSleepTime4(LocalTime sleepTime4) {
        this.sleepTime4 = sleepTime4;
    }

    @Override
    public String toString() {
        return "ParamSleepWake{" +
                "mode=" + mode +
                ", conditionType=" + conditionType +
                ", dayOfWeek=" + dayOfWeek +
                ", timeFlag=" + timeFlag +
                ", wakeTime1=" + wakeTime1 +
                ", sleepTime1=" + sleepTime1 +
                ", wakeTime2=" + wakeTime2 +
                ", sleepTime2=" + sleepTime2 +
                ", wakeTime3=" + wakeTime3 +
                ", sleepTime3=" + sleepTime3 +
                ", wakeTime4=" + wakeTime4 +
                ", sleepTime4=" + sleepTime4 +
                '}';
    }
}