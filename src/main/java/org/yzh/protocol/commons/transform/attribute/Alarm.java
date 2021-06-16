package org.yzh.protocol.commons.transform.attribute;

import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

public interface Alarm {

    long getSerialNo();

    int getState();

    default int getType() {
        return 0;
    }

    default int getLevel() {
        return 0;
    }

    int getSpeed();

    int getAltitude();

    int getLatitude();

    int getLongitude();

    LocalDateTime getDateTime();

    int getStatus();

    AlarmId getAlarmId();
}