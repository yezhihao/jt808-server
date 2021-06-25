package org.yzh.protocol.commons.transform.attribute;

import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

public interface Alarm {

    long getSerialNo();

    /** 该字段仅适用于有开始和结束标志类型的报警或事件,报警类型或事件类型无开始和结束标志,则该位不可用,填入0x00即可 */
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