package org.yzh.protocol.t1078;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.平台下发远程录像回放控制)
public class T9202 extends AbstractMessage<Header> {

    private int channelNo;
    private int playbackMode;
    private int playbackSpeed;
    private String playbackTime;

    @Field(index = 0, type = DataType.BYTE, desc = "逻辑通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "回放控制（0:开始回放;\n1:暂停回放;\2:结束回放;\n3:快进回放;\4:关键帧快退回放;\n5:拖动回放;\n6:关键帧播放）")
    public int getPlaybackMode() {
        return playbackMode;
    }

    public void setPlaybackMode(int playbackMode) {
        this.playbackMode = playbackMode;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "快进或快退倍数（回放控制为3和4时，此字段内容有效，否则置0。\n0:无效;\n1:1倍;\n2:2倍;\n3:4倍;\n4:8倍;\n5:16倍）")
    public int getPlaybackSpeed() {
        return playbackSpeed;
    }

    public void setPlaybackSpeed(int playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    @Field(index = 3, type = DataType.BCD8421, length = 6, desc = "拖动回放位置（yyMMddHHmmss,回放控制为5时，此字段有效）")
    public String getPlaybackTime() {
        return playbackTime;
    }

    public void setPlaybackTime(String playbackTime) {
        this.playbackTime = playbackTime;
    }
}