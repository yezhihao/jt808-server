package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

@Type(MessageId.多媒体事件信息上传)
public class T0800 extends AbstractBody {

    private Integer id;
    private Integer type;
    private Integer format;
    private Integer event;
    private Integer channelId;

    @Property(index = 0, type = DataType.DWORD, desc = "多媒体数据ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Property(index = 4, type = DataType.BYTE, desc = "多媒体类型 0：图像；1：音频；2：视频；")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 5, type = DataType.BYTE, desc = "多媒体格式编码 0：JPEG；1：TIF；2：MP3；3：WAV；4：WMV；")
    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    @Property(index = 6, type = DataType.BYTE, desc = "事件项编码")
    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    @Property(index = 7, type = DataType.BYTE, desc = "通道ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
}