package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.存储多媒体数据检索应答)
public class T0802 extends JTMessage implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(totalUnit = 2, desc = "检索项")
    private List<Item> items;

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Item {
        @Field(length = 4, desc = "多媒体数据ID")
        private int id;
        @Field(length = 1, desc = "多媒体类型：0.图像 1.音频 2.视频")
        private int type;
        @Field(length = 1, desc = "通道ID")
        private int channelId;
        @Field(length = 1, desc = "事件项编码")
        private int event;
        @Field(length = 28, desc = "位置信息")
        private T0200 location;
    }
}