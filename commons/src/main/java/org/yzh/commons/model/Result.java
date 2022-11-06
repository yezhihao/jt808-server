package org.yzh.commons.model;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class Result<T> {

    private int state;
    private T value;

    private Result() {
    }

    public static <T> Result<T> of(int state) {
        Result<T> result = new Result<>();
        result.state = state;
        return result;
    }

    public static <T> Result<T> of(T value) {
        Result<T> result = new Result<>();
        result.value = value;
        return result;
    }

    public static <T> Result<T> of(T value, int state) {
        Result<T> result = new Result<>();
        result.value = value;
        result.state = state;
        return result;
    }

    public boolean isSuccess() {
        return state == 0;
    }

    public int state() {
        return state;
    }

    public T value() {
        return value;
    }
}