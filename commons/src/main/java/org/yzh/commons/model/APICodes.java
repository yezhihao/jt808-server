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
    NonPermission(403, "没有权限"),
    NotFound(404, "接口未找到"),
    NotImplemented(405, "不支持的请求方法"),
    NotAcceptable(406, "不支持的接收类型"),
    OperationFailed(407, "操作失败"),
    UnsupportedMediaType(415, "不支持的请求类型"),
    MissingParameter(417, "缺少必要的参数"),
    InvalidParameter(422, "无效的参数"),

    OfflineClient(4000, "离线的客户端"),
    InternalError(500, "内部错误");

    final int code;
    final String message;

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