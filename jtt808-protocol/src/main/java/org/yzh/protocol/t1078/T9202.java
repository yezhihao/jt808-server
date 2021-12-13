package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.平台下发远程录像回放控制)
public class T9202 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 1, desc = "回放控制：0.开始回放 1.暂停回放 2.结束回放 3.快进回放 4.关键帧快退回放 5.拖动回放 6.关键帧播放")
    private int playbackMode;
    @Field(length = 1, desc = "快进或快退倍数：0.无效 1.1倍 2.2倍 3.4倍 4.8倍 5.16倍 (回放控制为3和4时,此字段内容有效,否则置0)")
    private int playbackSpeed;
    @Field(length = 6, charset = "BCD", desc = "拖动回放位置(YYMMDDHHMMSS,回放控制为5时,此字段有效)")
    private String playbackTime;

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public int getPlaybackMode() {
        return playbackMode;
    }

    public void setPlaybackMode(int playbackMode) {
        this.playbackMode = playbackMode;
    }

    public int getPlaybackSpeed() {
        return playbackSpeed;
    }

    public void setPlaybackSpeed(int playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    public String getPlaybackTime() {
        return playbackTime;
    }

    public void setPlaybackTime(String playbackTime) {
        this.playbackTime = playbackTime;
    }
}