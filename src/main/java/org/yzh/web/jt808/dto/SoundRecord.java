package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 摄像头立即拍摄命令
 */
public class SoundRecord extends PackageData<Header> {

    private Integer command;
    private Integer parameter;
    private Integer saveSign;
    private Integer audioSampleRate;

    public SoundRecord() {
    }

    @Property(index = 0, type = DataType.BYTE, desc = "录音命令:0.停止录音；0x01.开始录音")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    @Property(index = 1, type = DataType.WORD, desc = "单位为秒（s），0 表示一直录音")
    public Integer getParameter() {
        return parameter;
    }

    public void setParameter(Integer parameter) {
        this.parameter = parameter;
    }

    @Property(index = 3, type = DataType.BYTE, desc = "保存标志:0.实时上传；1.保存")
    public Integer getSaveSign() {
        return saveSign;
    }

    public void setSaveSign(Integer saveSign) {
        this.saveSign = saveSign;
    }

    @Property(index = 4, type = DataType.BYTE, desc = "音频采样率")
    public Integer getAudioSampleRate() {
        return audioSampleRate;
    }

    public void setAudioSampleRate(Integer audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }
}