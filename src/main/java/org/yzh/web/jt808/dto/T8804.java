package org.yzh.web.jt808.dto;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.jt808.common.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(JT808.录音开始命令)
public class T8804 extends AbstractBody {

    private Integer command;
    private Integer time;
    private Integer saveSign;
    private Integer audioSampleRate;

    public T8804() {
    }

    /** 0：停止录音；0x01：开始录音 */
    @Property(index = 0, type = DataType.BYTE, desc = "录音命令")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    @Property(index = 1, type = DataType.WORD, desc = "单位为秒（s），0 表示一直录音")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    /** 0：实时上传；1：保存 */
    @Property(index = 3, type = DataType.BYTE, desc = "保存标志")
    public Integer getSaveSign() {
        return saveSign;
    }

    public void setSaveSign(Integer saveSign) {
        this.saveSign = saveSign;
    }

    /**
     * 0：8K；
     * 1：11K；
     * 2：23K；
     * 3：32K；
     * 其他保留
     */
    @Property(index = 4, type = DataType.BYTE, desc = "音频采样率")
    public Integer getAudioSampleRate() {
        return audioSampleRate;
    }

    public void setAudioSampleRate(Integer audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }
}