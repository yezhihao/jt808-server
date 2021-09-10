package org.yzh.protocol.basics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.yezhihao.netmc.core.model.Message;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.util.ToStringBuilder;
import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.MessageId;

import java.beans.Transient;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@JsonIgnoreProperties({"messageId", "properties", "protocolVersion", "clientId", "serialNo", "packageTotal", "packageNo", "verified", "bodyLength", "encryption", "subpackage", "version", "reserved"})
public class JTMessage implements Message {

    @Field(index = 0, type = DataType.WORD, desc = "消息ID")
    protected int messageId;
    @Field(index = 2, type = DataType.WORD, desc = "消息体属性")
    protected int properties;
    @Field(index = 4, type = DataType.BYTE, desc = "协议版本号", version = 1)
    protected int protocolVersion;
    @Field(index = 4, type = DataType.BCD8421, length = 6, desc = "终端手机号", version = {-1, 0})
    @Field(index = 5, type = DataType.BCD8421, length = 10, desc = "终端手机号", version = 1)
    protected String clientId;
    @Field(index = 10, type = DataType.WORD, desc = "流水号", version = {-1, 0})
    @Field(index = 15, type = DataType.WORD, desc = "流水号", version = 1)
    protected int serialNo;
    @Field(index = 12, type = DataType.WORD, desc = "消息包总数", version = {-1, 0})
    @Field(index = 17, type = DataType.WORD, desc = "消息包总数", version = 1)
    protected Integer packageTotal;
    @Field(index = 14, type = DataType.WORD, desc = "包序号", version = {-1, 0})
    @Field(index = 19, type = DataType.WORD, desc = "包序号", version = 1)
    protected Integer packageNo;
    /** bcc校验 */
    protected boolean verified = true;

    protected transient Session session;

    protected transient ByteBuf payload;


    public JTMessage() {
    }

    public JTMessage(int messageId) {
        this.messageId = messageId;
    }

    public JTMessage copyBy(JTMessage that) {
        this.setClientId(that.getClientId());
        this.setProtocolVersion(that.getProtocolVersion());
        this.setVersion(that.isVersion());
        return this;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getProperties() {
        return properties;
    }

    public void setProperties(int properties) {
        this.properties = properties;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getPackageTotal() {
        if (isSubpackage())
            return packageTotal;
        return null;
    }

    public void setPackageTotal(Integer packageTotal) {
        this.packageTotal = packageTotal;
    }

    public Integer getPackageNo() {
        if (isSubpackage())
            return packageNo;
        return null;
    }

    public void setPackageNo(Integer packageNo) {
        this.packageNo = packageNo;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Transient
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Transient
    public ByteBuf getPayload() {
        return payload;
    }

    public void setPayload(ByteBuf payload) {
        this.payload = payload;
    }

    @Transient
    @Override
    public String getMessageName() {
        return MessageId.get(messageId);
    }

    public int reflectMessageId() {
        io.github.yezhihao.protostar.annotation.Message messageType = this.getClass().getAnnotation(io.github.yezhihao.protostar.annotation.Message.class);
        if (messageType != null && messageType.value().length > 0)
            return messageType.value()[0];
        return 0;
    }

    public void transform() {
    }

    private static final int BODY_LENGTH = 0b0000_0011_1111_1111;
    private static final int ENCRYPTION = 0b00011_100_0000_0000;
    private static final int SUBPACKAGE = 0b0010_0000_0000_0000;
    private static final int VERSION = 0b0100_0000_0000_0000;
    private static final int RESERVED = 0b1000_0000_0000_0000;

    /** 消息体长度 */
    public int getBodyLength() {
        return this.properties & BODY_LENGTH;
    }

    public void setBodyLength(int bodyLength) {
        this.properties ^= (properties & BODY_LENGTH);
        this.properties |= bodyLength;
    }

    /** 加密方式 */
    public int getEncryption() {
        return (properties & ENCRYPTION) >> 10;
    }

    public void setEncryption(int encryption) {
        this.properties ^= (properties & ENCRYPTION);
        this.properties |= (encryption << 10);
    }

    /** 是否分包 */
    public boolean isSubpackage() {
        return (properties & SUBPACKAGE) == SUBPACKAGE;
    }

    public void setSubpackage(boolean subpackage) {
        if (subpackage)
            this.properties |= SUBPACKAGE;
        else
            this.properties ^= (properties & SUBPACKAGE);
    }

    /** 是否有版本 */
    public boolean isVersion() {
        return (properties & VERSION) == VERSION;
    }

    public void setVersion(boolean version) {
        if (version)
            this.properties |= VERSION;
        else
            this.properties ^= (properties & VERSION);
    }

    /** 保留位 */
    public boolean isReserved() {
        return (properties & RESERVED) == RESERVED;
    }

    public void setReserved(boolean reserved) {
        if (reserved)
            this.properties |= RESERVED;
        else
            this.properties ^= (properties & RESERVED);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(768);
        sb.append(MessageId.get(messageId));
        sb.append('[');
        sb.append("cid=").append(clientId);
        sb.append(",msg=").append(messageId);
        sb.append(",ver=").append(protocolVersion);
        sb.append(",ser=").append(serialNo);
        sb.append(",prop=").append(properties);
        if (isSubpackage()) {
            sb.append(",pt=").append(packageTotal);
            sb.append(",pn=").append(packageNo);
        }
        sb.append(']');
        sb.append(',');
        String result = ToStringBuilder.toString(sb, this, false, "messageId", "clientId", "protocolVersion", "serialNo", "properties", "packageTotal", "packageNo");
        return result;
    }
}