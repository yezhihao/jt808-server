package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.摄像头立即拍摄命令)
public class T8801 extends JTMessage {

    @Field(length = 1, desc = "通道ID(大于0)")
    private int channelId;
    @Field(length = 2, desc = "拍摄命令：0表示停止拍摄；65535表示录像；其它表示拍照张数")
    private int command;
    @Field(length = 2, desc = "拍照间隔/录像时间(秒) 0表示按最小间隔拍照或一直录像")
    private int time;
    @Field(length = 1, desc = "保存标志：1.保存 0.实时上传")
    private int save;
    @Field(length = 1, desc = "分辨率：" +
            " 1.320*240" +
            " 2.640*480" +
            " 3.800*600" +
            " 4.1024*768" +
            " 5.176*144 [QCIF]" +
            " 6.352*288 [CIF]" +
            " 7.704*288 [HALF D1]" +
            " 8.704*576 [D1]")
    private int resolution;
    @Field(length = 1, desc = "图像/视频质量(1~10)：1.代表质量损失最小 10.表示压缩比最大")
    private int quality;
    @Field(length = 1, desc = "亮度(0~255)")
    private int brightness;
    @Field(length = 1, desc = "对比度(0~127)")
    private int contrast;
    @Field(length = 1, desc = "饱和度(0~127)")
    private int saturation;
    @Field(length = 1, desc = "色度(0~255)")
    private int chroma;

    public T8801() {
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getChroma() {
        return chroma;
    }

    public void setChroma(int chroma) {
        this.chroma = chroma;
    }
}