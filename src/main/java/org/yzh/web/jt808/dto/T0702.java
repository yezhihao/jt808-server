package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

@Type(MessageId.驾驶员身份信息采集上报)
public class T0702 extends AbstractBody {

    private Integer status;
    private String dateTime;
    private Integer cardStatus;
    private Integer nameLen;
    private String name;
    private String licenseNo;
    private Integer institutionLen;
    private String institution;
    private String licenseValidPeriod;

    @Property(index = 0, type = DataType.BYTE, desc = "状态")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Property(index = 1, type = DataType.BCD8421, length = 6, desc = "时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Property(index = 7, type = DataType.BYTE, desc = "IC卡读取结果")
    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Property(index = 8, type = DataType.BYTE, desc = "驾驶员姓名长度")
    public Integer getNameLen() {
        return nameLen;
    }

    public void setNameLen(Integer nameLen) {
        this.nameLen = nameLen;
    }

    @Property(index = 9, type = DataType.STRING, lengthName = "nameLen", desc = "驾驶员姓名")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Property(index = 9, indexOffsetName = "nameLen", type = DataType.STRING, length = 20, desc = "从业资格证编码")
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Property(index = 29, indexOffsetName = "nameLen", type = DataType.BYTE, desc = "发证机构名称长度")
    public Integer getInstitutionLen() {
        return institutionLen;
    }

    public void setInstitutionLen(Integer institutionLen) {
        this.institutionLen = institutionLen;
    }

    @Property(index = 30, indexOffsetName = "nameLen", type = DataType.STRING, lengthName = "institutionLen", desc = "发证机构名称")
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Property(index = 30, indexOffsetName = {"nameLen", "institutionLen"}, type = DataType.BCD8421, length = 4, desc = "证件有效期")
    public String getLicenseValidPeriod() {
        return licenseValidPeriod;
    }

    public void setLicenseValidPeriod(String licenseValidPeriod) {
        this.licenseValidPeriod = licenseValidPeriod;
    }
}