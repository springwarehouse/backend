package com.example.system.core.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用出参对象
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * 结果集
     */
    private T data;

    /**
     * 返回状态（必返）
     * 返回结果 200 成功
     * 返回结果 -1  失败
     */
    private Integer code;

    /**
     * 接口信息
     */
    private String msg;

    public Result(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success(T data) {
        CommonResultStatus success = CommonResultStatus.SUCCESS;
        return new Result<T>(data, success.intValue(), success.message());
    }

    public static Result<Object> error(String message) {
        return new Result<Object>(null, CommonResultStatus.SYS_EXCEPTION.intValue(), message);
    }

    public static Result<Object> error(int code, String message) {
        return new Result<Object>(null, code, message);
    }

    public static <T> Result<T> error(CommonResultStatus status) {
        return new Result<>(null, status.intValue(), status.message());
    }
}
