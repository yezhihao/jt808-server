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
@Message(JT808.摄像头立即拍摄命令)
public class T8801 extends JTMessage {

    private int channelId;
    private int command;
    private int time;
    private int save;
    private int resolution;
    private int quality;
    private int brightness;
    private int contrast;
    private int saturation;
    private int chroma;

    public T8801() {
    }

    /** 大于0 */
    @Field(index = 0, type = DataType.BYTE, desc = "通道ID")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /** 0表示停止拍摄 0xFFFF表示录像;其它表示拍照张数 */
    @Field(index = 1, type = DataType.WORD, desc = "拍摄命令")
    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    /** 秒，0表示按最小间隔拍照或一直录像 */
    @Field(index = 3, type = DataType.WORD, desc = "拍照间隔/录像时间")
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /** 1:保存 0:实时上传 */
    @Field(index = 5, type = DataType.BYTE, desc = "保存标志")
    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    /**
     * 0x01: 320*240；
     * 0x02: 640*480；
     * 0x03: 800*600；
     * 0x04: 1024*768；
     * 0x05: 176*144；[Qcif]；
     * 0x06: 352*288；[Cif]；
     * 0x07: 704*288；[HALF D1]；
     * 0x08: 704*576；[D1]；
     */
    @Field(index = 6, type = DataType.BYTE, desc = "分辨率")
    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    /** 1:代表质量损失最小 10:表示压缩比最大 */
    @Field(index = 7, type = DataType.BYTE, desc = "质量")
    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "亮度")
    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Field(index = 9, type = DataType.BYTE, desc = "对比度")
    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    @Field(index = 10, type = DataType.BYTE, desc = "饱和度")
    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    @Field(index = 11, type = DataType.BYTE, desc = "色度")
    public int getChroma() {
        return chroma;
    }

    public void setChroma(int chroma) {
        this.chroma = chroma;
    }
}