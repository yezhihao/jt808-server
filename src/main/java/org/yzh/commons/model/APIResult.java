package org.yzh.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.beans.ImmutableBean;
import org.yzh.commons.util.StrUtils;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class APIResult<T> {

    public static final APIResult SUCCESS = (APIResult) ImmutableBean.create(new APIResult());

    protected int code;
    protected String msg;
    protected String detailMsg;

    protected T data;

    public APIResult() {
        this.code = ResultCodes.Success.code;
        this.msg = ResultCodes.Success.message;
    }

    public APIResult(Exception e) {
        this.code = ResultCodes.UnknownError.code;
        this.msg = e.getMessage();
        this.detailMsg = StrUtils.getStackTrace(e);
    }

    public APIResult(ResultCode code, Exception e) {
        this.code = code.getCode();
        this.msg = code.getMessage();
        this.detailMsg = StrUtils.getStackTrace(e);
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

    public APIResult(ResultCode code, String message, String detailMsg) {
        this.code = code.getCode();
        this.msg = message;
        this.detailMsg = detailMsg;
    }

    public APIResult(T t) {
        this(ResultCodes.Success, t);
    }

    public APIResult(ResultCode code, T data) {
        this(code);
        this.data = data;
    }

    public boolean isSuccess() {
        return ResultCodes.Success.code.equals(code);
    }

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("detailMsg")
    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    @JsonProperty("data")
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}