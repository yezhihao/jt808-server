package org.yzh.web.model.enums;

import org.yzh.web.commons.ResultCode;

/**
 * Created by Alan.ye on 2017/5/20.
 */
public enum DefaultCodes implements ResultCode {

    Success(0, ""),
    TypeMismatch(400, "参数格式不正确"),
    MissingParameter(401, "缺少必要的参数"),
    NotSupportedType(402, "不支持的请求类型"),
    NotImplemented(403, "未实现的方法"),
    UnknownError(500, "未知错误");

    public final Integer code;

    public final String message;

    DefaultCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}