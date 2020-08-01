package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.终端注册应答)
public class T8100 extends AbstractMessage<Header> {

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

    private int serialNo;
    private int resultCode;
    private String token;

    public T8100() {
    }

    public T8100(int serialNo, String mobileNo) {
        super(new Header(JT808.终端注册应答, serialNo, mobileNo));
    }

    /** 对应的终端注册消息的流水号 */
    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    /** 0-4 */
    @Field(index = 2, type = DataType.BYTE, desc = "结果")
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    /** 成功后才有该字段 */
    @Field(index = 3, type = DataType.STRING, desc = "鉴权码")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}