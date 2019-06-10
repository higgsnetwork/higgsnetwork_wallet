package com.higgs.network.wallet.common.stub;

import com.google.common.base.Strings;
import static com.higgs.network.wallet.common.stub.ResultCode.SUCCESS;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author :
 * @date :
 */
public class Result<T> {
    private int code = SUCCESS;
    private String message;
    private T data;

    /**
     * Result.
     */
    public Result() {

    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Success result.
     *
     * @return the result
     */
    public static Result success() {
        return new Result();
    }

    /**
     * Success msg result.
     *
     * @param <O>     the type parameter
     * @param message the message
     * @return the result
     */
    public static <O> Result<O> successMsg(String message) {
        return new Result<>(SUCCESS, message, null);
    }

    /**
     * Success result.
     *
     * @param <O>     the type parameter
     * @param payload the payload
     * @return the result
     */
    public static <O> Result<O> success(O payload) {
        checkArgument(payload != null, "payload should be not null");
        return new Result<>(SUCCESS, null, payload);
    }

    /**
     * Success result.
     *
     * @param <O>     the type parameter
     * @param data    the data
     * @param message the message
     * @return the result
     */
    public static <O> Result<O> success(O data, String message) {
        checkArgument(data != null, "data should not be null");
        return new Result<>(SUCCESS, message, data);
    }

    /**
     * Fail result.
     *
     * @param <O>     the type parameter
     * @param code    the code
     * @param message the message
     * @return the result
     */
    public static <O> Result<O> fail(int code, String message) {
        checkArgument(!Strings.isNullOrEmpty(message), "message should be not empty");
        checkArgument(code > 0, "code should be greater than 0");
        return new Result<>(code, message, null);
    }

    /**
     * Fail result.
     *
     * @param <O>     the type parameter
     * @param code    the code
     * @param message the message
     * @param payload the payload
     * @return the result
     */
    public static <O> Result<O> fail(int code, String message, O payload) {
        checkArgument(payload != null, "payload should be not null");
        checkArgument(!Strings.isNullOrEmpty(message), "message should be not empty");
        checkArgument(code > 0, "code should be greater than 0");
        return new Result<>(code, message, payload);
    }

    /**
     * Get code int.
     *
     * @return the int
     */
    public int getCode() {
        return code;
    }

    /**
     * Get message string.
     *
     * @return the string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get data t.
     *
     * @return the t
     */
    public T getData() {
        return data;
    }
}
