package org.yzh.protocol.t808;

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
@Message(JT808.CAN总线数据上传)
public class T0705 extends JTMessage {

    @Field(length = 2, desc = "数据项个数(大于0)")
    private int total;
    @Field(length = 5, charset = "BCD", desc = "CAN总线数据接收时间(HHMMSSMSMS)")
    private String dateTime;
    @Field(desc = "CAN总线数据项")
    private List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Item {
        @Field(length = 4, desc = "CAN ID")
        private int id;
        @Field(length = 8, desc = "CAN DATA")
        private byte[] data;
    }
}