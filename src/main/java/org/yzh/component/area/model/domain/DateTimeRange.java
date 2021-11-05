package org.yzh.component.area.model.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface DateTimeRange {

    LocalDate getStartDate();

    LocalDate getEndDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    int getWeeks();

    default boolean contains(LocalDateTime dateTime) {
        if (containsTime(dateTime.toLocalTime()))
            if (containsWeek(dateTime.getDayOfWeek()))
                if (containsDate(dateTime.toLocalDate()))
                    return true;
        return false;
    }

    default boolean containsTime(LocalTime time) {
        LocalTime start = getStartTime();
        LocalTime end = getEndTime();
        return (end == null || end.compareTo(time) > 0) &&
                (start == null || start.compareTo(time) <= 0);
    }

    default boolean containsDate(LocalDate date) {
        LocalDate start = getStartDate();
        LocalDate end = getEndDate();
        return (end == null || end.compareTo(date) >= 0) &&
                (start == null || start.compareTo(date) <= 0);
    }

    default boolean containsWeek(DayOfWeek dayOfWeek) {
        return (getWeeks() & (1 << dayOfWeek.ordinal())) > 0;
    }
}