package org.yzh.component.area.model.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DateTimeRange {

    private final LocalTime startTime;// 开始时间
    private final LocalTime endTime;  // 结束时间
    private final LocalDate startDate;// 开始日期
    private final LocalDate endDate;  // 结束日期
    private final int weeks;          // 生效日(按位,周一至周日)

    public DateTimeRange(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, int weeks) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weeks = weeks;
    }

    public boolean contains(LocalDateTime dateTime) {
        if (containsTime(dateTime.toLocalTime()))
            if (containsWeek(dateTime.getDayOfWeek()))
                if (containsDate(dateTime.toLocalDate()))
                    return true;
        return false;
    }

    public boolean containsTime(LocalTime time) {
        return (endTime == null || endTime.compareTo(time) > 0) &&
                (startTime == null || startTime.compareTo(time) <= 0);
    }

    public boolean containsDate(LocalDate date) {
        return (endDate == null || endDate.compareTo(date) >= 0) &&
                (startDate == null || startDate.compareTo(date) <= 0);
    }

    public boolean containsWeek(DayOfWeek dayOfWeek) {
        return (weeks & (1 << dayOfWeek.ordinal())) > 0;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getWeeks() {
        return weeks;
    }
}