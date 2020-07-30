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
@Message(JT808.录音开始命令)
public class T8804 extends AbstractMessage<Header> {

    private Integer command;
    private Integer time;
    private Integer save;
    private Integer audioSampleRate;

    public T8804() {
    }

    public T8804(String mobileNo) {
        super(new Header(mobileNo, JT808.录音开始命令));
    }

    /** 0：停止录音；1：开始录音 */
    @Field(index = 0, type = DataType.BYTE, desc = "录音命令")
    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    @Field(index = 1, type = DataType.WORD, desc = "单位为秒（s），0 表示一直录音")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    /** 0：实时上传；1：保存 */
    @Field(index = 3, type = DataType.BYTE, desc = "保存标志")
    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    /**
     * 0：8K；
     * 1：11K；
     * 2：23K；
     * 3：32K；
     * 其他保留
     */
    @Field(index = 4, type = DataType.BYTE, desc = "音频采样率")
    public Integer getAudioSampleRate() {
        return audioSampleRate;
    }

    public void setAudioSampleRate(Integer audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }
}