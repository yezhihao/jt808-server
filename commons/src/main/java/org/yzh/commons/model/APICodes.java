package org.yzh.commons.model;

/**
 * 响应状态枚举类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public enum APICodes implements APICode {

    Success(200, ""),

    TypeMismatch(400, "参数格式不正确"),
    Unauthorized(401, "没有授权"),
    NotPermission(403, "没有权限"),
    OperationFailed(404, "操作失败"),

    NotImplemented(405, "不支持的请求方法"),
    NotAcceptable(406, "不支持的接收类型"),
    UnsupportedMediaType(415, "不支持的请求类型"),

    MissingParameter(417, "缺少必要的参数"),
    InvalidParameter(422, "无效的参数"),

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