package org.yzh.web.model.enums;

import org.yzh.web.commons.ResultCode;

/**
 * Created by Alan.ye on 2017/5/20.
 */
public enum DefaultCodes implements ResultCode {

    Success(200, ""),
    UnregisteredUser(402, "未注册的用户"),
    Unauthorized(403, "用户授权失败"),
    MissingParameter(400, "缺少必要的参数"),
    TypeMismatch(410, "参数格式不正确"),
    InvalidParameter(411, "无效的参数"),
    NotSupportedType(412, "不支持的请求类型"),
    NotImplemented(413, "未实现的方法"),
    OperationFailed(420, "操作失败"),
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