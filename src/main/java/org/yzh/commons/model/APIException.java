package org.yzh.commons.model;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class APIException extends RuntimeException {

    private int code;
    private String message;
    private String detailMessage;

    public APIException(APICode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public APIException(APICode code, String msg) {
        this.code = code.getCode();
        this.message = msg;
    }

    public APIException(APICode code, String message, String detailMessage) {
        this.code = code.getCode();
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public APIException(Throwable e) {
        super(e);
        this.code = APICodes.UnknownError.getCode();
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