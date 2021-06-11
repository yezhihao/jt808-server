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
@Message(JT808.录音开始命令)
public class T8804 extends JTMessage {

    private int command;
    private int time;
    private int save;
    private int audioSamplingRate;

    @Field(index = 0, type = DataType.BYTE, desc = "录音命令: 0.停止录音 1.开始录音")
    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    @Field(index = 1, type = DataType.WORD, desc = "录音时间(秒) 0.表示一直录音")
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Field(index = 3, type = DataType.BYTE, desc = "保存标志: 0.实时上传 1.保存")
    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "音频采样率: 0: 8K 1: 11K 2: 23K 3: 32K 其他保留")
    public int getAudioSamplingRate() {
        return audioSamplingRate;
    }

    public void setAudioSamplingRate(int audioSamplingRate) {
        this.audioSamplingRate = audioSamplingRate;
    }
}