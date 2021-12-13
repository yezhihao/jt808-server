package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

import java.time.LocalTime;

import static io.github.yezhihao.protostar.util.DateTool.BCD;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class TimeRange {

    private LocalTime startTime;
    private LocalTime endTime;

    public TimeRange() {
    }

    public TimeRange(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(32);
        sb.append('{');
        sb.append("startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<TimeRange> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public TimeRange readFrom(ByteBuf input) {
            TimeRange message = new TimeRange();
            message.startTime = BCD.readTime2(input);
            message.endTime = BCD.readTime2(input);
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, TimeRange message) {
            BCD.writeTime2(output, message.startTime);
            BCD.writeTime2(output, message.endTime);
        }
    }
}