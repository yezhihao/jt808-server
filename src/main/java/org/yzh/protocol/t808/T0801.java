package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.多媒体数据上传)
public class T0801 extends JTMessage {

    private int id;
    private int type;
    private int format;
    private int event;
    private int channelId;
    private T0200 position;
    private byte[] packet;

    @Field(index = 0, type = DataType.DWORD, desc = "多媒体数据ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "多媒体类型 0：图像；1：音频；2：视频；")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 5, type = DataType.BYTE, desc = "多媒体格式编码 0：JPEG；1：TIF；2：MP3；3：WAV；4：WMV；")
    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "事件项编码")
    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @Field(index = 7, type = DataType.BYTE, desc = "通道ID")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
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