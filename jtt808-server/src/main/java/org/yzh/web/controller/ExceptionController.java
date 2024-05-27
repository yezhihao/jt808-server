package org.yzh.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.yzh.commons.model.APICodes;
import org.yzh.commons.model.APIException;
import org.yzh.commons.model.APIResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    private static final Pattern compile = Pattern.compile("'\\w*'");

    @ExceptionHandler(Exception.class)
    public APIResult<?> onException(Exception e) {
        log.error("系统异常", e);
        return new APIResult<>(e);
    }

    @ExceptionHandler(AsyncRequestNotUsableException.class)
    public void onAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
        log.warn("异步请求已断开", e);
    }

    @ExceptionHandler(APIException.class)
    public APIResult<?> onAPIException(APIException e) {
        return new APIResult<>(e);
    }

    @ExceptionHandler(SQLException.class)
    public APIResult<?> onSQLException(SQLException e) {
        String message = e.getMessage();
        if (message.endsWith("have a default value"))
            return new APIResult<>(APICodes.MissingParameter, e);
        log.warn("系统异常:", e);
        return new APIResult<>(e);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public APIResult<?> onDuplicateKeyException(DuplicateKeyException e) {
        Matcher matcher = compile.matcher(e.getCause().getMessage());
        List<String> values = new ArrayList<>(4);
        while (matcher.find())
            values.add(matcher.group());

        int size = values.size();
        int len = size < 2 ? size : (size / 2);

        StringBuilder sb = new StringBuilder(20);
        sb.append("已存在的号码:");

        for (int i = 0; i < len; i++)
            sb.append(values.get(i)).append(',');
        return new APIResult<>(APICodes.InvalidParameter, sb.substring(0, sb.length() - 1));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public APIResult<?> onIllegalArgumentException(IllegalArgumentException e) {
        log.warn("系统异常:", e);
        return new APIResult<>(APICodes.InvalidParameter, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIResult<?> onHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("系统异常:", e);
        return new APIResult<>(APICodes.TypeMismatch, e);
    }

    @ExceptionHandler(BindException.class)
    public APIResult<?> onBindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors)
            sb.append(fieldError.getField()).append(fieldError.getDefaultMessage());
        return new APIResult<>(APICodes.MissingParameter, sb.toString());
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public APIResult<?> onHttpMediaTypeException(HttpMediaTypeException e) {
        log.warn("系统异常:", e);
        return new APIResult<>(APICodes.NotSupportedType, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public APIResult<?> onHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new APIResult<>(APICodes.NotImplemented);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public APIResult<?> onMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new APIResult<>(APICodes.MissingParameter, ":" + e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public APIResult<?> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new APIResult<>(APICodes.TypeMismatch, ":" + e.getName() + "=" + e.getValue(), e.getMessage());
    }
}