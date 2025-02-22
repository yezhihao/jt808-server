package org.yzh.protocol.t1078;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@Accessors(chain = true)
@Message(JT1078.终端上传音视频资源列表)
public class T1205 extends JTMessage implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(totalUnit = 4, desc = "音视频资源列表")
    private List<Item> items;

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Item {

        @Field(length = 1, desc = "逻辑通道号")
        private int channelNo;
        @Field(length = 6, charset = "BCD", desc = "开始时间")
        private LocalDateTime startTime;
        @Field(length = 6, charset = "BCD", desc = "结束时间")
        private LocalDateTime endTime;
        @Field(length = 8, desc = "报警标志0~63")
        private long warnBit;
        @Field(length = 1, desc = "音视频资源类型")
        private int mediaType;
        @Field(length = 1, desc = "码流类型")
        private int streamType = 1;
        @Field(length = 1, desc = "存储器类型")
        private int storageType;
        @Field(length = 4, desc = "文件大小")
        private long size;
    }
}
