package org.yzh.protocol.basics;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Fs;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Header extends AbstractHeader {

    /** 消息类型 */
    protected int messageId;
    /** 消息体属性 */
    protected int properties;
    /** 协议版本号 */
    protected int versionNo;
    /** 手机号 */
    protected String mobileNo;
    /** 消息序列号 */
    protected int serialNo;
    /** 包总数 */
    protected Integer packageTotal;
    /** 包序号 */
    protected Integer packageNo;

    public Header() {
    }

    public Header(int messageId, String mobileNo) {
        this.messageId = messageId;
        this.mobileNo = mobileNo;
    }

    public Header(int messageId, int serialNo, String mobileNo) {
        this.messageId = messageId;
        this.serialNo = serialNo;
        this.mobileNo = mobileNo;
    }

    public Header(String mobileNo, int messageId) {
        this.messageId = messageId;
        this.mobileNo = mobileNo;
    }

    @Field(index = 0, type = DataType.WORD, desc = "消息ID", version = {0, 1})
    @Override
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Field(index = 2, type = DataType.WORD, desc = "消息体属性", version = {0, 1})
    public int getProperties() {
        return properties;
    }

    public void setProperties(int properties) {
        this.properties = properties;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "协议版本号", version = 1)
    @Override
    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    @Fs({@Field(index = 4, type = DataType.BCD8421, length = 6, desc = "终端手机号", version = 0),
            @Field(index = 5, type = DataType.BCD8421, length = 10, desc = "终端手机号", version = 1)})
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Fs({@Field(index = 10, type = DataType.WORD, desc = "流水号", version = 0),
            @Field(index = 15, type = DataType.WORD, desc = "流水号", version = 1)})
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Fs({@Field(index = 12, type = DataType.WORD, desc = "消息包总数", version = 0),
            @Field(index = 17, type = DataType.WORD, desc = "消息包总数", version = 1)})
    @Override
    public Integer getPackageTotal() {
        if (isSubpackage())
            return packageTotal;
        return null;
    }

    public void setPackageTotal(Integer packageTotal) {
        this.packageTotal = packageTotal;
    }

    @Fs({@Field(index = 14, type = DataType.WORD, desc = "包序号", version = 0),
            @Field(index = 19, type = DataType.WORD, desc = "包序号", version = 1)})
    @Override
    public Integer getPackageNo() {
        if (isSubpackage())
            return packageNo;
        return null;
    }

    public void setPackageNo(Integer packageNo) {
        this.packageNo = packageNo;
    }

    /** 消息头长度 */
    @Override
    public int getHeadLength() {
        if (isVersion())
            return isSubpackage() ? 21 : 17;
        return isSubpackage() ? 16 : 12;
    }

    private static final int BODY_LENGTH = 0b0000_0011_1111_1111;
    private static final int ENCRYPTION = 0b00011_100_0000_0000;
    private static final int SUBPACKAGE = 0b0010_0000_0000_0000;
    private static final int VERSION = 0b0100_0000_0000_0000;
    private static final int RESERVED = 0b1000_0000_0000_0000;

    /** 消息体长度 */
    @Override
    public int getBodyLength() {
        return this.properties & BODY_LENGTH;
    }

    public void setBodyLength(int bodyLength) {
        this.properties ^= (properties & BODY_LENGTH);
        this.properties |= bodyLength;
    }

    /** 加密方式 */
    @Override
    public int getEncryption() {
        return (properties & ENCRYPTION) >> 10;
    }

    public void setEncryption(int encryption) {
        this.properties ^= (properties & ENCRYPTION);
        this.properties |= (encryption << 10);
    }

    /** 是否分包 */
    @Override
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
    @Override
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
    public String getClientId() {
        return mobileNo;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder(96);
        b.append(JT808.get(messageId));
        b.append('[');
        b.append("messageId=").append(messageId);
        b.append(", properties=").append(properties);
        b.append(", versionNo=").append(versionNo);
        b.append(", mobileNo=").append(mobileNo);
        b.append(", serialNo=").append(serialNo);
        if (isSubpackage()) {
            b.append(", packageTotal=").append(packageTotal);
            b.append(", packageNo=").append(packageNo);
        }
        b.append(']');
        return b.toString();
    }
}