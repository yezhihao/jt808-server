package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.驾驶员身份信息采集上报)
public class T0702 extends JTMessage {

    private int status;
    private String dateTime;
    private int cardStatus;
    private String name;
    private String licenseNo;
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

    @Field(index = 8, type = DataType.STRING, lengthSize = 1, desc = "驾驶员姓名")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(index = 9, type = DataType.STRING, length = 20, desc = "从业资格证编码")
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Field(index = 29, type = DataType.STRING, lengthSize = 1, desc = "从业资格证发证机构名称")
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Field(index = 30, type = DataType.BCD8421, length = 4, desc = "证件有效期")
    public String getLicenseValidPeriod() {
        return licenseValidPeriod;
    }

    public void setLicenseValidPeriod(String licenseValidPeriod) {
        this.licenseValidPeriod = licenseValidPeriod;
    }

    @Field(index = 34, type = DataType.STRING, length = 4, desc = "驾驶员身份证号", version = 1)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}