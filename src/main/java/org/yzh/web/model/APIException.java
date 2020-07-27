package org.yzh.web.model;

import org.yzh.web.commons.ResultCode;
import org.yzh.web.model.enums.DefaultCodes;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class APIException extends RuntimeException {

    private int code;
    private String message;
    private String detailMessage;

    public APIException(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public APIException(ResultCode code, String msg) {
        this.code = code.getCode();
        this.message = msg;
    }

    public APIException(ResultCode code, String message, String detailMessage) {
        this.code = code.getCode();
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public APIException(Throwable e) {
        super(e);
        this.code = DefaultCodes.UnknownError.code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"");
        sb.append("code\":").append(code);
        sb.append(",\"message\":\"").append(super.getMessage());
        sb.append("\"}");
        return sb.toString();
    }

}