package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.终端鉴权)
public class T0102 extends JTMessage {

    /** 终端重连后上报鉴权码 */
    @Field(index = 0, type = DataType.STRING, desc = "鉴权码", version = {-1, 0})
    @Field(index = 0, type = DataType.STRING, lengthSize = 1, desc = "鉴权码", version = 1)
    private String token;
    @Field(index = 2, type = DataType.STRING, length = 15, desc = "终端IMEI", version = 1)
    private String imei;
    @Field(index = 17, type = DataType.STRING, length = 20, desc = "软件版本号", version = 1)
    private String softwareVersion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }
}