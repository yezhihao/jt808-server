package org.yzh.commons.model;

/**
 * 响应状态枚举类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public enum APICodes implements APICode {

    Success(200, ""),
    UnregisteredUser(402, "未注册的用户"),
    Unauthorized(403, "用户授权失败"),
    MissingParameter(400, "缺少必要的参数"),
    TypeMismatch(410, "参数格式不正确"),
    InvalidParameter(411, "无效的参数"),
    NotSupportedType(412, "不支持的请求类型"),
    NotImplemented(413, "未实现的方法"),
    OperationFailed(420, "操作失败"),
    OfflineClient(4000, "离线的客户端"),
    UnknownError(500, "未知错误");

    private final int code;
    private final String message;

    APICodes(int code, String message) {
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