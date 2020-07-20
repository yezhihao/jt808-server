package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(MessageId.多媒体数据上传)
public class T0801 extends AbstractBody {

    private Integer id;
    private Integer type;
    private Integer format;
    private Integer event;
    private Integer channelId;
    private T0200 position;
    private byte[] packet;

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

    @Property(index = 8, type = DataType.OBJ, length = 28, desc = "位置信息")
    public T0200 getPosition() {
        return position;
    }

    public void setPosition(T0200 position) {
        this.position = position;
    }

    @Property(index = 36, type = DataType.BYTES, desc = "多媒体数据包")
    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }
}