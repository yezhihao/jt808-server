package org.yzh.commons.model;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Result<T> {

    private int state;
    private T value;

    private Result() {
    }

    public static <T> Result<T> of(int state) {
        Result result = new Result<T>();
        result.state = state;
        return result;
    }

    public static <T> Result of(T value) {
        Result result = new Result<T>();
        result.value = value;
        return result;
    }

    public static <T> Result of(T value, int state) {
        Result result = new Result<T>();
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

    public T get() {
        return value;
    }
}