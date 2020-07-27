package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.多媒体数据上传)
public class T0801 extends AbstractMessage<Header> {

    private Integer id;
    private Integer type;
    private Integer format;
    private Integer event;
    private Integer channelId;
    private T0200 position;
    private byte[] packet;

    @Field(index = 0, type = DataType.DWORD, desc = "多媒体数据ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "多媒体类型 0：图像；1：音频；2：视频；")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 5, type = DataType.BYTE, desc = "多媒体格式编码 0：JPEG；1：TIF；2：MP3；3：WAV；4：WMV；")
    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "事件项编码")
    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    @Field(index = 7, type = DataType.BYTE, desc = "通道ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Field(index = 8, type = DataType.OBJ, length = 28, desc = "位置信息")
    public T0200 getPosition() {
        return position;
    }

    public void setPosition(T0200 position) {
        this.position = position;
    }

    @Field(index = 36, type = DataType.BYTES, desc = "多媒体数据包")
    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }
}