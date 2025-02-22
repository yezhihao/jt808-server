package org.yzh.protocol.basics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yezhihao.netmc.core.model.Message;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.util.ToStringBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.commons.MessageId;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class JTMessage implements Message {

    @Field(length = 2, desc = "消息ID")
    protected int messageId;
    @Field(length = 2, desc = "消息体属性")
    protected int properties;
    @Field(length = 1, desc = "协议版本号", version = 1)
    protected int protocolVersion;
    @Field(length = 6, charset = "BCD", desc = "终端手机号", version = {-1, 0})
    @Field(length = 10, charset = "BCD", desc = "终端手机号", version = 1)
    protected String clientId;
    @Field(length = 2, desc = "流水号")
    protected int serialNo;
    @Field(length = 2, desc = "消息包总数")
    protected Integer packageTotal;
    @Field(length = 2, desc = "包序号")
    protected Integer packageNo;
    /** bcc校验 */
    protected boolean verified = true;

    @JsonIgnore
    protected transient Session session;
    @JsonIgnore
    protected transient ByteBuf payload;
    @JsonIgnore
    protected transient Object extData;
    @JsonIgnore
    protected transient int vehicleId;
    @JsonIgnore
    protected transient int driverId;

    public JTMessage copyBy(JTMessage that) {
        this.setClientId(that.getClientId());
        this.setProtocolVersion(that.getProtocolVersion());
        this.setVersion(that.isVersion());
        return this;
    }

    public Integer getPackageTotal() {
        if (isSubpackage())
            return packageTotal;
        return null;
    }

    public Integer getPackageNo() {
        if (isSubpackage())
            return packageNo;
        return null;
    }

    public <T> T getExtData() {
        return (T) extData;
    }

    public int reflectMessageId() {
        if (messageId != 0)
            return messageId;
        return reflectMessageId(this.getClass());
    }

    public static int reflectMessageId(Class<?> clazz) {
        io.github.yezhihao.protostar.annotation.Message messageType = clazz.getAnnotation(io.github.yezhihao.protostar.annotation.Message.class);
        if (messageType != null && messageType.value().length > 0)
            return messageType.value()[0];
        return 0;
    }

    public boolean noBuffer() {
        return true;
    }

    private static final int BODY_LENGTH = 0b0000_0011_1111_1111;
    private static final int ENCRYPTION = 0b0001_1100_0000_0000;
    private static final int SUBPACKAGE = 0b0010_0000_0000_0000;
    private static final int VERSION = 0b0100_0000_0000_0000;
    private static final int RESERVED = 0b1000_0000_0000_0000;

    /** 消息体长度 */
    public int getBodyLength() {
        return this.properties & BODY_LENGTH;
    }

    public void setBodyLength(int bodyLength) {
        this.properties = (properties & ~BODY_LENGTH) | (bodyLength & BODY_LENGTH);
    }

    /** 加密方式 */
    public int getEncryption() {
        return (properties & ENCRYPTION) >> 10;
    }

    public void setEncryption(int encryption) {
        this.properties = (properties & ~ENCRYPTION) | (encryption & ENCRYPTION);
    }

    /** 是否分包 */
    public boolean isSubpackage() {
        return (properties & SUBPACKAGE) == SUBPACKAGE;
    }

    public void setSubpackage(boolean subpackage) {
        if (subpackage)
            this.properties |= SUBPACKAGE;
        else
            this.properties &= ~SUBPACKAGE;
    }

    /** 是否有版本 */
    public boolean isVersion() {
        return (properties & VERSION) == VERSION;
    }

    public void setVersion(boolean version) {
        if (version)
            this.properties |= VERSION;
        else
            this.properties &= ~VERSION;
    }

    /** 保留位 */
    public boolean isReserved() {
        return (properties & RESERVED) == RESERVED;
    }

    public void setReserved(boolean reserved) {
        if (reserved)
            this.properties |= RESERVED;
        else
            this.properties &= ~RESERVED;
    }

    protected StringBuilder toStringHead() {
        final StringBuilder sb = new StringBuilder(768);
        sb.append(MessageId.getName(messageId));
        sb.append('[');
        sb.append("cid=").append(clientId);
        sb.append(",msgId=").append(messageId);
        sb.append(",version=").append(protocolVersion);
        sb.append(",serialNo=").append(serialNo);
        sb.append(",props=").append(properties);
        sb.append(",verified=").append(verified);
        if (isSubpackage()) {
            sb.append(",pt=").append(packageTotal);
            sb.append(",pn=").append(packageNo);
        }
        sb.append(']');
        sb.append(',');
        return sb;
    }

    @Override
    public String toString() {
        String result = ToStringBuilder.toString(toStringHead(), this, false, "messageId", "clientId", "protocolVersion", "serialNo", "properties", "packageTotal", "packageNo");
        return result;
    }
}