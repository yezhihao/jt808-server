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
@Message(JT808.查询终端属性应答)
public class T0107 extends JTMessage {

    @Field(length = 2, desc = "终端类型")
    private int deviceType;
    @Field(length = 5, desc = "制造商ID,终端制造商编码")
    private String makerId;
    /** 由制造商自行定义,位数不足时,后补"0x00" */
    @Field(length = 20, desc = "终端型号", version = {-1, 0})
    @Field(length = 30, desc = "终端型号", version = 1)
    private String deviceModel;
    /** 由大写字母和数字组成,此终端ID由制造商自行定义,位数不足时,后补"0x00" */
    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID", version = 1)
    private String deviceId;
    @Field(length = 10, charset = "HEX", desc = "终端SIM卡ICCID")
    private String iccid;
    @Field(lengthUnit = 1, desc = "硬件版本号")
    private String hardwareVersion;
    @Field(lengthUnit = 1, desc = "固件版本号")
    private String firmwareVersion;
    @Field(length = 1, desc = "GNSS模块属性")
    private int gnssAttribute;
    @Field(length = 1, desc = "通信模块属性")
    private int networkAttribute;

}