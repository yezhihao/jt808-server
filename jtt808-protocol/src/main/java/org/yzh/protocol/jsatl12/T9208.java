package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JSATL12;

import java.time.LocalDateTime;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JSATL12.报警附件上传指令)
public class T9208 extends JTMessage {

    private static final byte[] RESERVES = new byte[16];

    @Field(lengthUnit = 1, desc = "服务器IP地址")
    private String ip;
    @Field(length = 2, desc = "TCP端口")
    private int tcpPort;
    @Field(length = 2, desc = "UDP端口")
    private int udpPort;

    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID(粤标)", version = 1)
    private String deviceId;
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
    @Field(length = 16, desc = "预留")
    private byte[] reserves = RESERVES;

}