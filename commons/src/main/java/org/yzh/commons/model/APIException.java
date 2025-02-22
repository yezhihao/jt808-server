package org.yzh.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"stackTrace", "suppressed", "localizedMessage"})
public class APIException extends RuntimeException {

    private final int code;
    private String msg;
    private String details;

    public APIException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIException(APICode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }

    public APIException(APICode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }

    public APIException(APICode code, String msg, String details) {
        this.code = code.getCode();
        this.msg = msg;
        this.details = details;
    }

    public APIException(Throwable e) {
        super(e);
        this.code = APICodes.InternalError.getCode();
    }
}