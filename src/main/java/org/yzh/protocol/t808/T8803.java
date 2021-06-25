package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.time.LocalDateTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.存储多媒体数据上传)
public class T8803 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "多媒体类型: 0.图像 1.音频 2.视频 ")
    private int type;
    @Field(index = 1, type = DataType.BYTE, desc = "通道ID")
    private int channelId;
    @Field(index = 2, type = DataType.BYTE, desc = "事件项编码: 0.平台下发指令 1.定时动作 2.抢劫报警触发 3.碰撞侧翻报警触发 其他保留")
    private int event;
    @Field(index = 3, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
    private LocalDateTime startTime;
    @Field(index = 9, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
    private LocalDateTime endTime;
    @Field(index = 15, type = DataType.BYTE, desc = "删除标志: 0.保留 1.删除 ")
    private int delete;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}