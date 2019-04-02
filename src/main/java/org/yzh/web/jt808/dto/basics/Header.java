package org.yzh.web.jt808.dto.basics;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;

public class Header extends org.yzh.framework.message.Header {

    protected Integer type;
    protected Integer bodyPropsField;
    protected String mobileNumber;
    protected Integer flowId;

    protected Integer subPackageTotal;
    protected Integer subPackageNo;

    protected Integer bodyLength = 0;
    protected Integer encryptionType = 0b000;
    protected boolean hasSubPackage = false;
    protected Integer reservedBit = 0;

    public Header() {
    }

    public Header(Integer type, String mobileNumber) {
        this.type = type;
        this.mobileNumber = mobileNumber;
    }

    public Header(Integer type, Integer flowId, String mobileNumber) {
        this.type = type;
        this.flowId = flowId;
        this.mobileNumber = mobileNumber;
    }

    @Property(index = 0, type = DataType.WORD, desc = "消息ID")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 2, type = DataType.WORD, desc = "消息体属性")
    public Integer getBodyPropsField() {
        // [0-9] 0000,0011,1111,1111(3FF)(消息体长度)
        // [10-12] 0001,1100,0000,0000(1C00)(加密类型)
        // [13] 0010,0000,0000,0000(2000)(是否有子包)
        // [14-15] 1100,0000,0000,0000(C000)(保留位)
        if (bodyLength >= 1024)
            System.out.println("The max value of msgLen is 1023, but {} ." + bodyLength);
        int subPkg = hasSubPackage ? 1 : 0;
        int ret = (bodyLength & 0x3FF) | ((encryptionType << 10) & 0x1C00)
                | ((subPkg << 13) & 0x2000) | ((reservedBit << 14) & 0xC000);
        this.bodyPropsField = ret & 0xffff;

        return bodyPropsField;
    }

    public void setBodyPropsField(Integer bodyPropsField) {
        this.bodyPropsField = bodyPropsField;

        // [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
        this.bodyLength = bodyPropsField & 0x3ff;
        // [10-12] 0001,1100,0000,0000(1C00)(加密类型)
        this.encryptionType = (bodyPropsField & 0x1c00) >> 10;
        // [ 13_ ] 0010,0000,0000,0000(2000)(是否有子包)
        this.hasSubPackage = ((bodyPropsField & 0x2000) >> 13) == 1;
        // [14-15] 1100,0000,0000,0000(C000)(保留位)
        this.reservedBit = ((bodyPropsField & 0xc000) >> 14);
    }

    @Property(index = 4, type = DataType.BCD8421, length = 6, pad = 48, desc = "终端手机号")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Property(index = 10, type = DataType.WORD, desc = "流水号")
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    @Property(index = 12, type = DataType.WORD, desc = "消息包总数")
    public Integer getSubPackageTotal() {
        if (!isHasSubPackage())
            return null;
        return subPackageTotal;
    }

    public void setSubPackageTotal(Integer subPackageTotal) {
        this.subPackageTotal = subPackageTotal;
    }

    @Property(index = 14, type = DataType.WORD, desc = "包序号,这次发送的这个消息包是分包中的第几个消息包,从1开始")
    public Integer getSubPackageNo() {
        if (!isHasSubPackage())
            return null;
        return subPackageNo;
    }

    public void setSubPackageNo(Integer subPackageNo) {
        this.subPackageNo = subPackageNo;
    }

    @Override
    //消息体长度
    public Integer getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(Integer bodyLength) {
        this.bodyLength = bodyLength;
    }

    // 数据加密方式
    public Integer getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(Integer encryptionType) {
        this.encryptionType = encryptionType;
    }

    // 是否分包,true==>有消息包封装项
    public boolean isHasSubPackage() {
        return hasSubPackage;
    }

    public void setHasSubPackage(boolean hasSubPackage) {
        this.hasSubPackage = hasSubPackage;
    }

    // 保留位[14-15]
    public Integer getReservedBit() {
        return reservedBit;
    }

    public void setReservedBit(Integer reservedBit) {
        this.reservedBit = reservedBit;
    }

    @Override
    public Integer getHeaderLength() {
        if (isHasSubPackage())
            return 16;
        return 12;
    }

}