package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JSATL12;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JSATL12.报警附件信息消息)
public class T1210 extends JTMessage {

    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID(粤标)", version = 1)
    private String deviceId;

    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID(粤标)", version = 1)
    private String deviceId_;
    @Field(length = 6, charset = "BCD", desc = "时间(YYMMDDHHMMSS)")
    private LocalDateTime dateTime;
    @Field(length = 1, desc = "序号(同一时间点报警的序号，从0循环累加)")
    private int sequenceNo;
    @Field(length = 1, desc = "附件数量")
    private int fileTotal;
    @Field(length = 1, desc = "预留", version = {-1, 0})
    @Field(length = 2, desc = "预留(粤标)", version = 1)
    private int reserved;

    @Field(length = 32, desc = "报警编号")
    private String platformAlarmId;
    @Field(length = 1, desc = "信息类型：0.正常报警文件信息 1.补传报警文件信息")
    private int type;
    @Field(totalUnit = 1, desc = "附件信息列表")
    private List<Item> items;

    public String getDeviceId() {
        if (StringUtil.isNullOrEmpty(deviceId))
            return deviceId_;
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        this.deviceId_ = deviceId;
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Item {
        @Field(lengthUnit = 1, desc = "文件名称")
        private String name;
        @Field(length = 4, desc = "文件大小")
        private long size;

        private transient T1210 parent;
    }
}