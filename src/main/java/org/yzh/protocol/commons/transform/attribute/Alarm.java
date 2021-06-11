package org.yzh.protocol.commons.transform.attribute;

import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

public interface Alarm {

    long getSerialNo();

    int getState();

    int getType();

    int getLevel();

    int getSpeed();

    int getAltitude();

    int getLatitude();

    int getLongitude();

    LocalDateTime getDateTime();

    int getStatus();

    AlarmId getAlarmId();
}