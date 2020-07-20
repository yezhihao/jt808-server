package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type({MessageId.数据上行透传, MessageId.数据下行透传})
public class T8900_0900 extends AbstractBody {

    /** GNSS模块详细定位数据 */
    public static final int GNSSLocation = 0x00;
    /** 道路运输证IC卡信息上传消息为64Byte，下传消息为24Byte。道路运输证IC卡认证透传超时时间为30s。超时后，不重发。 */
    public static final int ICCardInfo = 0x0B;
    /** 串口1透传消息 */
    public static final int SerialPortOne = 0x41;
    /** 串口2透传消息 */
    public static final int SerialPortTow = 0x42;
    /** 用户自定义透传 0xF0 - 0xFF */
    public static final int Custom = 0xF0 - 0xFF;

    private Integer type;
    private byte[] content;

    public T8900_0900() {
    }

    public T8900_0900(Integer type, byte[] content) {
        this.type = type;
        this.content = content;
    }

    @Property(index = 0, type = DataType.BYTE, desc = "透传消息类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTES, desc = "透传消息内容")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}