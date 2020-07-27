package org.yzh.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cglib.beans.ImmutableBean;
import org.yzh.web.commons.ResultCode;
import org.yzh.web.model.enums.DefaultCodes;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class APIResult<T> {

    public static final APIResult SUCCESS = (APIResult) ImmutableBean.create(new APIResult());

    protected int code;

    protected String msg;

    protected String stackMsg;

    protected T data;

    public APIResult() {
        this.code = DefaultCodes.Success.code;
        this.msg = DefaultCodes.Success.message;
    }

    public APIResult(Exception e) {
        this.code = DefaultCodes.UnknownError.code;
        this.msg = e.getMessage();
        this.stackMsg = ExceptionUtils.getStackTrace(e);
    }

    public APIResult(ResultCode code, Exception e) {
        this.code = code.getCode();
        this.msg = code.getMessage();
        this.stackMsg = ExceptionUtils.getStackTrace(e);
    }

    public APIResult(APIException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }

    public APIResult(ResultCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }

    public APIResult(ResultCode code, String message) {
        this.code = code.getCode();
        this.msg = message;
    }

    public APIResult(T t) {
        this(DefaultCodes.Success, t);
    }

    public APIResult(ResultCode code, T data) {
        this(code);
        this.data = data;
    }

    public boolean isSuccess() {
        return DefaultCodes.Success.code.equals(code);
    }

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("stackMsg")
    public String getStackMsg() {
        return stackMsg;
    }

    public void setStackMsg(String stackMsg) {
        this.stackMsg = stackMsg;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("data")
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}