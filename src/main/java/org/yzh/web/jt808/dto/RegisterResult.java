package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.common.MessageId;
import org.yzh.web.jt808.dto.basics.Header;

@Type(MessageId.终端注册应答)
public class RegisterResult extends PackageData<Header> {

    /** 0：成功 */
    public static final int Success = 0;
    /** 1：车辆已被注册 */
    public static final int AlreadyRegisteredVehicle = 1;
    /** 2：数据库中无该车辆 */
    public static final int NotFoundVehicle = 2;
    /** 3：终端已被注册 */
    public static final int AlreadyRegisteredTerminal = 3;
    /** 4：数据库中无该终端 */
    public static final int NotFoundTerminal = 4;

    private Integer serialNumber;
    private Integer resultCode;
    private String token;

    public RegisterResult() {
    }

    public RegisterResult(Header header, Integer resultCode, String token) {
        super(header);
        this.serialNumber = header.getSerialNumber();
        this.resultCode = resultCode;
        this.token = token;
    }

    /** 对应的终端注册消息的流水号 */
    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    /** 0-4 */
    @Property(index = 2, type = DataType.BYTE, desc = "结果")
    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    /** 成功后才有该字段 */
    @Property(index = 3, type = DataType.STRING, desc = "鉴权码")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}