package org.yzh.web.jt.t808;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.jt.common.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Type(JT808.存储多媒体数据上传命令)
public class T8803 extends AbstractBody {

    private Integer type;
    private Integer channelId;
    private Integer event;
    private String startTime;
    private String endTime;
    private Integer deleteSign;

    @Property(index = 0, type = DataType.BYTE, desc = "多媒体类型:0.图像；1.音频；2.视频；")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "通道ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Property(index = 2, type = DataType.BYTE, desc = "事件项编码:0.平台下发指令；1.定时动作；2.抢劫报警触发；3.碰撞侧翻报警触发；其他保留")
    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    @Property(index = 3, type = DataType.BCD8421, length = 6, desc = "起始时间YY-MM-DD-hh-mm-ss")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Property(index = 9, type = DataType.BCD8421, length = 6, desc = "结束时间YY-MM-DD-hh-mm-ss")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Property(index = 15, type = DataType.BYTE, desc = "删除标志:0.保留；1.删除；")
    public Integer getDeleteSign() {
        return deleteSign;
    }

    public void setDeleteSign(Integer deleteSign) {
        this.deleteSign = deleteSign;
    }
}