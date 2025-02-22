package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.终端升级结果通知)
public class T0108 extends JTMessage {

    /** 终端 */
    public static final int Terminal = 0;
    /** 道路运输证IC卡 读卡器 */
    public static final int CardReader = 12;
    /** 北斗卫星定位模块 */
    public static final int Beidou = 52;

    @Field(length = 1, desc = "升级类型：0.终端 12.道路运输证IC卡读卡器 52.北斗卫星定位模块")
    private int type;
    @Field(length = 1, desc = "升级结果：0.成功 1.失败 2.取消")
    private int result;

}