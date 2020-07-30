package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.摄像头立即拍摄命令)
public class T8801 extends AbstractMessage<Header> {

    private Integer channelId;
    private Integer command;
    private Integer time;
    private Integer save;
    private Integer resolution;
    private Integer quality;
    private Integer brightness;
    private Integer contrast;
    private Integer saturation;
    private Integer chroma;

    public T8801() {
    }

    /** 大于0 */
    @Field(index = 0, type = DataType.BYTE, desc = "通道ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /** 0表示停止拍摄 0xFFFF表示录像;其它表示拍照张数 */
    @Field(index = 1, type = DataType.WORD, desc = "拍摄命令")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    /** 秒，0表示按最小间隔拍照或一直录像 */
    @Field(index = 3, type = DataType.WORD, desc = "拍照间隔/录像时间")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    /** 1:保存 0:实时上传 */
    @Field(index = 5, type = DataType.BYTE, desc = "保存标志")
    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
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
    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    /** 1:代表质量损失最小 10:表示压缩比最大 */
    @Field(index = 7, type = DataType.BYTE, desc = "质量")
    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "亮度")
    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    @Field(index = 9, type = DataType.BYTE, desc = "对比度")
    public Integer getContrast() {
        return contrast;
    }

    public void setContrast(Integer contrast) {
        this.contrast = contrast;
    }

    @Field(index = 10, type = DataType.BYTE, desc = "饱和度")
    public Integer getSaturation() {
        return saturation;
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;
    }

    @Field(index = 11, type = DataType.BYTE, desc = "色度")
    public Integer getChroma() {
        return chroma;
    }

    public void setChroma(Integer chroma) {
        this.chroma = chroma;
    }
}