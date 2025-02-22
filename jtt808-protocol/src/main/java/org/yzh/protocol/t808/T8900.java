package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import io.github.yezhihao.protostar.util.KeyValuePair;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.PassthroughConverter;
import org.yzh.protocol.commons.transform.passthrough.PeripheralStatus;
import org.yzh.protocol.commons.transform.passthrough.PeripheralSystem;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.数据下行透传)
public class T8900 extends JTMessage {

    /** GNSS模块详细定位数据 */
    public static final int GNSSLocation = 0x00;
    /** 道路运输证IC卡信息上传消息为64Byte,下传消息为24Byte,道路运输证IC卡认证透传超时时间为30s.超时后,不重发 */
    public static final int ICCardInfo = 0x0B;
    /** 串口1透传消息 */
    public static final int SerialPortOne = 0x41;
    /** 串口2透传消息 */
    public static final int SerialPortTow = 0x42;
    /** 用户自定义透传 0xF0~0xFF */
    public static final int Custom = 0xF0;

    @Field(desc = "透传消息", converter = PassthroughConverter.class)
    private KeyValuePair<Integer, Object> message;

    @Schema(description = "状态查询(外设状态信息：外设工作状态、设备报警信息)")
    private PeripheralStatus peripheralStatus;

    @Schema(description = "信息查询(外设传感器的基本信息：公司信息、产品代码、版本号、外设ID、客户代码)")
    private PeripheralSystem peripheralSystem;

    public T8900 build() {
        KeyValuePair<Integer, Object> message = new KeyValuePair<>();

        if (peripheralStatus != null) {
            message.setKey(PeripheralStatus.key);
            message.setValue(peripheralStatus);

        } else if (peripheralSystem != null) {
            message.setKey(PeripheralSystem.key);
            message.setValue(peripheralSystem);
        }
        this.message = message;
        return this;
    }
}