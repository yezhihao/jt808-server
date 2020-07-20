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
@Type(MessageId.摄像头立即拍摄命令)
public class T8801 extends AbstractBody {

    private Integer channelId;
    private Integer command;
    private Integer time;
    private Integer saveSign;
    private Integer resolution;
    private Integer quality;
    private Integer brightness;
    private Integer contrast;
    private Integer saturation;
    private Integer chroma;

    public T8801() {
    }

    /** 大于0 */
    @Property(index = 0, type = DataType.BYTE, desc = "通道ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /** 0表示停止拍摄 0xFFFF表示录像;其它表示拍照张数 */
    @Property(index = 1, type = DataType.WORD, desc = "拍摄命令")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    /** 秒，0表示按最小间隔拍照或一直录像 */
    @Property(index = 3, type = DataType.WORD, desc = "拍照间隔/录像时间")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    /** 1:保存 0:实时上传 */
    @Property(index = 5, type = DataType.BYTE, desc = "保存标志")
    public Integer getSaveSign() {
        return saveSign;
    }

    public void setSaveSign(Integer saveSign) {
        this.saveSign = saveSign;
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
    @Property(index = 6, type = DataType.BYTE, desc = "分辨率")
    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    /** 1:代表质量损失最小 10:表示压缩比最大 */
    @Property(index = 7, type = DataType.BYTE, desc = "质量")
    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    @Property(index = 8, type = DataType.BYTE, desc = "亮度")
    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    @Property(index = 9, type = DataType.BYTE, desc = "对比度")
    public Integer getContrast() {
        return contrast;
    }

    public void setContrast(Integer contrast) {
        this.contrast = contrast;
    }

    @Property(index = 10, type = DataType.BYTE, desc = "饱和度")
    public Integer getSaturation() {
        return saturation;
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;
    }

    @Property(index = 11, type = DataType.BYTE, desc = "色度")
    public Integer getChroma() {
        return chroma;
    }

    public void setChroma(Integer chroma) {
        this.chroma = chroma;
    }
}