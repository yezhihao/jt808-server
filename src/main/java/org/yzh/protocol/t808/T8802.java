package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.time.LocalDateTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.存储多媒体数据检索)
public class T8802 extends JTMessage {

    private int type;
    private int channelId;
    private int event;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public T8802() {
    }

    public T8802(String mobileNo) {
        super(new Header(mobileNo, JT808.存储多媒体数据检索));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "多媒体类型:0.图像；1.音频；2.视频；")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "通道ID")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留")
    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @Field(index = 3, type = DataType.BCD8421, length = 6, desc = "起始时间YY-MM-DD-hh-mm-ss")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Field(index = 9, type = DataType.BCD8421, length = 6, desc = "结束时间YY-MM-DD-hh-mm-ss")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}