package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 摄像头立即拍摄命令
 */
public class CameraShot extends PackageData<Header> {

    private Integer channelId;
    private Integer command;
    private Integer parameter;
    private Integer saveSign;
    private Integer resolution;
    private Integer quality;
    private Integer brightness;
    private Integer contrast;
    private Integer saturation;
    private Integer chroma;

    public CameraShot() {
    }

    @Property(index = 0, type = DataType.BYTE, desc = "通道ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Property(index = 1, type = DataType.WORD, desc = "拍摄命令")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    @Property(index = 3, type = DataType.WORD, desc = "拍照间隔/录像时间")
    public Integer getParameter() {
        return parameter;
    }

    public void setParameter(Integer parameter) {
        this.parameter = parameter;
    }

    @Property(index = 5, type = DataType.BYTE, desc = "保存标志")
    public Integer getSaveSign() {
        return saveSign;
    }

    public void setSaveSign(Integer saveSign) {
        this.saveSign = saveSign;
    }

    @Property(index = 6, type = DataType.BYTE, desc = "分辨率")
    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

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