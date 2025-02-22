package org.yzh.commons.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.yzh.commons.model.APICodes;
import org.yzh.commons.model.APIException;
import org.yzh.commons.model.R;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AsyncRequestNotUsableException.class)
    public void onAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
        log.warn("异步请求已断开：{}", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R<?> onIllegalArgumentException(IllegalArgumentException e) {
        log.warn("系统异常:", e);
        return R.error(APICodes.InvalidParameter, e.getMessage());
    }

    @ExceptionHandler(APIException.class)
    public APIException onAPIException(APIException e) {
        return e;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<?> onHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("系统异常:", e);
        return R.error(APICodes.TypeMismatch, e);
    }

    @ExceptionHandler(BindException.class)
    public R<?> onBindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors)
            sb.append(fieldError.getField()).append(fieldError.getDefaultMessage());
        return R.error(APICodes.MissingParameter, sb.toString());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public R<?> onHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        return R.error(APICodes.NotAcceptable);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<?> onHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return R.error(APICodes.UnsupportedMediaType);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> onHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return R.error(APICodes.NotImplemented);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null)
            return R.error(fieldError.getDefaultMessage());
        return R.error(APICodes.TypeMismatch, e.getBindingResult().toString());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<?> onMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return R.error(APICodes.MissingParameter, ":" + e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<?> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return R.error(APICodes.TypeMismatch, ":" + e.getName() + "=" + e.getValue(), e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R<?> onNoHandlerFoundException(NoHandlerFoundException e) {
        return R.error(APICodes.NotFound);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public R<?> onNoResourceFoundException(NoResourceFoundException e) {
        return R.error(APICodes.NotFound);
    }
}