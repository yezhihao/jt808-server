package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 终端鉴权消息
 */
public class Authentication extends PackageData<Header> {

    private String authCode;

    @Property(index = 0, type = DataType.STRING, desc = "鉴权码,终端连接后上报鉴权码")
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}