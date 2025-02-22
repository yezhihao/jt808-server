package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT1078.查询资源列表)
public class T9205 extends JTMessage {

    @Field(length = 1, desc = "逻辑通道号")
    private int channelNo;
    @Field(length = 6, charset = "BCD", desc = "开始时间(YYMMDDHHMMSS,全0表示无起始时间)")
    private String startTime;
    @Field(length = 6, charset = "BCD", desc = "结束时间(YYMMDDHHMMSS,全0表示无终止时间)")
    private String endTime;
    @Field(length = 4, desc = "报警标志0~31(参考808协议文档报警标志位定义)")
    private int warnBit1;
    @Field(length = 4, desc = "报警标志32~63")
    private int warnBit2;
    @Field(length = 1, desc = "音视频资源类型：0.音视频 1.音频 2.视频 3.视频或音视频")
    private int mediaType;
    @Field(length = 1, desc = "码流类型：0.所有码流 1.主码流 2.子码流")
    private int streamType;
    @Field(length = 1, desc = "存储器类型：0.所有存储器 1.主存储器 2.灾备存储器")
    private int storageType;

}