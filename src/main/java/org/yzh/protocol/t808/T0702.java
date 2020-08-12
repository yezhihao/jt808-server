package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.驾驶员身份信息采集上报)
public class T0702 extends AbstractMessage<Header> {

    private int status;
    private String dateTime;
    private int cardStatus;
    private int nameLen;
    private String name;
    private String licenseNo;
    private int institutionLen;
    private String institution;
    private String licenseValidPeriod;
    private String idCard;

    @Field(index = 0, type = DataType.BYTE, desc = "状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 1, type = DataType.BCD8421, length = 6, desc = "时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 7, type = DataType.BYTE, desc = "IC卡读取结果")
    public int getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "驾驶员姓名长度")
    public int getNameLen() {
        return nameLen;
    }

    public void setNameLen(int nameLen) {
        this.nameLen = nameLen;
    }

    @Field(index = 9, type = DataType.STRING, lengthName = "nameLen", desc = "驾驶员姓名")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameLen = name.getBytes(Charsets.GBK).length;
    }

    @Field(index = 9, indexOffsetName = "nameLen", type = DataType.STRING, length = 20, desc = "从业资格证编码")
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Field(index = 29, indexOffsetName = "nameLen", type = DataType.BYTE, desc = "发证机构名称长度")
    public int getInstitutionLen() {
        return institutionLen;
    }

    public void setInstitutionLen(int institutionLen) {
        this.institutionLen = institutionLen;
    }

    @Field(index = 30, indexOffsetName = "nameLen", type = DataType.STRING, lengthName = "institutionLen", desc = "从业资格证发证机构名称")
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
        this.institutionLen = institution.getBytes(Charsets.GBK).length;
    }

    @Field(index = 30, indexOffsetName = {"nameLen", "institutionLen"}, type = DataType.BCD8421, length = 4, desc = "证件有效期")
    public String getLicenseValidPeriod() {
        return licenseValidPeriod;
    }

    public void setLicenseValidPeriod(String licenseValidPeriod) {
        this.licenseValidPeriod = licenseValidPeriod;
    }

    @Field(index = 34, indexOffsetName = {"nameLen", "institutionLen"}, type = DataType.STRING, length = 4, desc = "驾驶员身份证号", version = 1)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}