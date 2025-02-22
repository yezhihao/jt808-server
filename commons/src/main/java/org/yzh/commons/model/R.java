package org.yzh.commons.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.commons.util.Exceptions;
import org.yzh.commons.util.JsonUtils;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class R<T> {

    public static final R SUCCESS = new ImmutableR<>(R.success());
    public static final R Unauthorized = new ImmutableR<>(R.error(APICodes.Unauthorized));
    public static final R NonPermission = new ImmutableR<>(R.error(APICodes.NonPermission));

    public interface View {
    }

    @JsonView(View.class)
    @Schema(description = "响应码(成功：200；客户端错误：400-499；服务端错误：500-599)")
    protected int code;
    @JsonView(View.class)
    @Schema(description = "响应消息")
    protected String msg;
    @JsonView(View.class)
    @Schema(description = "响应消息详情")
    protected String details;
    @JsonView(View.class)
    @Schema(description = "响应数据")
    protected T data;

    public R() {
    }

    public R(int code, T data, String msg, String details) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.details = details;
    }

    public static <T> R<T> success() {
        return new R<>(APICodes.Success.code, null, null, null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(APICodes.Success.code, data, null, null);
    }

    public static <T> R<T> success(T data, String msg) {
        return new R<>(APICodes.Success.code, data, msg, null);
    }

    public static <T> R<T> successMsg(String msg) {
        return new R<>(APICodes.Success.code, null, msg, null);
    }

    public static <T> R<T> error() {
        return new R<>(APICodes.InternalError.code, null, "内部错误", null);
    }

    public static <T> R<T> error(Throwable e) {
        return new R<>(APICodes.InternalError.code, null, e.getMessage(), Exceptions.getStackTrace(e));
    }

    public static <T> R<T> error(String msg) {
        return new R<>(APICodes.InternalError.code, null, msg, null);
    }

    public static <T> R<T> error(String msg, Throwable e) {
        return new R<>(APICodes.InternalError.code, null, msg, Exceptions.getStackTrace(e));
    }

    public static <T> R<T> error(int code) {
        return new R<>(code, null, null, null);
    }

    public static <T> R<T> error(int code, String msg) {
        return new R<>(code, null, msg, null);
    }

    public static <T> R<T> error(APICode apiCode) {
        return new R<>(apiCode.getCode(), null, apiCode.getMessage(), null);
    }

    public static <T> R<T> error(APICode apiCode, String msg) {
        return new R<>(apiCode.getCode(), null, msg, null);
    }

    public static <T> R<T> error(APICode apiCode, Throwable e) {
        return new R<>(apiCode.getCode(), null, apiCode.getMessage(), Exceptions.getStackTrace(e));
    }

    public static <T> R<T> error(APICode apiCode, String msg, Throwable e) {
        return new R<>(apiCode.getCode(), null, msg, Exceptions.getStackTrace(e));
    }

    public boolean isSuccess() {
        return APICodes.Success.getCode() == code;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public static final class ImmutableR<T> extends R<T> {

        public ImmutableR(R<T> that) {
            this.code = that.code;
            this.msg = that.msg;
            this.details = that.details;
            this.data = that.data;
        }

        @Override
        public ImmutableR<T> setCode(int code) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ImmutableR<T> setMsg(String msg) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ImmutableR<T> setDetails(String details) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ImmutableR<T> setData(T data) {
            throw new UnsupportedOperationException();
        }
    }
}