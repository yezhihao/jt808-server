package org.yzh.web.jt808.dto.basics;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.framework.message.AbstractMessage;

public class Message<T extends AbstractBody> extends AbstractMessage<T> {

    protected Integer type;
    protected Integer bodyProperties;
    protected String mobileNumber;
    protected Integer serialNumber;

    protected Integer subPackageTotal;
    protected Integer subPackageNumber;

    protected Integer bodyLength = 0;
    protected Integer encryptionType = 0b000;
    protected boolean subPackage = false;
    protected Integer reservedBit = 0;

    public Message() {
    }

    public Message(Integer type, String mobileNumber, T body) {
        this.type = type;
        this.mobileNumber = mobileNumber;
        this.body = body;
    }

    public Message(Integer type, Integer serialNumber, String mobileNumber) {
        this.type = type;
        this.serialNumber = serialNumber;
        this.mobileNumber = mobileNumber;
    }

    public Message(Integer type, Integer serialNumber, String mobileNumber, T body) {
        this.type = type;
        this.serialNumber = serialNumber;
        this.mobileNumber = mobileNumber;
        this.body = body;
    }

    @Property(index = 0, type = DataType.WORD, desc = "消息ID")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 2, type = DataType.WORD, desc = "消息体属性")
    public Integer getBodyProperties() {
        if (bodyLength >= 1024)
            System.out.println("The max value of msgLen is 1023, but {} ." + bodyLength);
        int subPkg = subPackage ? 1 : 0;
        int ret = (bodyLength & 0x3FF) |
                ((encryptionType << 10) & 0x1C00) |
                ((subPkg << 13) & 0x2000) |
                ((reservedBit << 14) & 0xC000);
        this.bodyProperties = ret & 0xffff;

        return bodyProperties;
    }

    /**
     * [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
     * [10-12] 0001,1100,0000,0000(1C00)(加密类型)
     * [ 13 ] 0010,0000,0000,0000(2000)(是否有子包)
     * [14-15] 1100,0000,0000,0000(C000)(保留位)
     */
    public void setBodyProperties(Integer bodyProperties) {
        this.bodyProperties = bodyProperties;

        this.bodyLength = bodyProperties & 0x3ff;
        this.encryptionType = (bodyProperties & 0x1c00) >> 10;
        this.subPackage = ((bodyProperties & 0x2000) >> 13) == 1;
        this.reservedBit = ((bodyProperties & 0xc000) >> 14);
    }

    @Property(index = 4, type = DataType.BCD8421, length = 6, pad = 48, desc = "终端手机号")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Property(index = 10, type = DataType.WORD, desc = "流水号")
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 本次发送的子包是分包中的第几个消息包,从1开始
     * 如果消息体属性中相关标识位确定消息分包处理，则该项有内容，否则无该项
     */
    @Property(index = 12, type = DataType.WORD, desc = "消息包总数")
    public Integer getSubPackageTotal() {
        return hasSubPackage() ? subPackageTotal : null;
    }

    public void setSubPackageTotal(Integer subPackageTotal) {
        this.subPackageTotal = subPackageTotal;
    }

    /**
     * 本次发送的子包是分包中的第几个消息包,从1开始
     * 如果消息体属性中相关标识位确定消息分包处理，则该项有内容，否则无该项
     */
    @Property(index = 14, type = DataType.WORD, desc = "包序号")
    public Integer getSubPackageNumber() {
        return hasSubPackage() ? subPackageNumber : null;
    }

    public void setSubPackageNumber(Integer subPackageNumber) {
        this.subPackageNumber = subPackageNumber;
    }

    @Override
    public Integer getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(Integer bodyLength) {
        this.bodyLength = bodyLength;
    }

    @Override
    public Integer getHeaderLength() {
        return hasSubPackage() ? 16 : 12;
    }

    public Integer getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(Integer encryptionType) {
        this.encryptionType = encryptionType;
    }

    @Override
    public boolean hasSubPackage() {
        return subPackage;
    }

    public void setSubPackage(boolean subPackage) {
        this.subPackage = subPackage;
    }

    public boolean isSubPackage() {
        return subPackage;
    }

    public Integer getReservedBit() {
        return reservedBit;
    }

    public void setReservedBit(Integer reservedBit) {
        this.reservedBit = reservedBit;
    }
}