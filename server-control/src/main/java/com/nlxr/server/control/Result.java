package com.nlxr.server.control;

import java.util.UUID;

/**
 * @Author labu
 * @Date 2020/10/26
 * @Description
 */
public class Result<T> {
    private String requestId;
    private boolean success;
    private T data;
    private String apiCode;
    private String apiMessage;
    private long serverTime;
    private long processTime;

    public static <T> Result<T> success(T data) {
        return new Result(data);
    }

    public static <T> Result<T> success(String requestId, T data) {
        Result<T> result = new Result(requestId, true);
        result.setData(data);
        return result;
    }

    public static Result<Void> failure() {
        return new Result(false);
    }

    public static Result<Void> failure(String requestId) {
        return new Result(requestId, false);
    }

    public Result() {
        this(true);
    }

    public Result(T data) {
        this(true);
        this.data = data;
    }

    public Result(boolean success) {
        this(UUID.randomUUID().toString(), success);
    }

    public Result(String requestId, boolean success) {
        this.serverTime = System.currentTimeMillis();
        this.requestId = requestId;
        this.success = success;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public Result<T> setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getApiCode() {
        return this.apiCode;
    }

    public Result<T> setApiCode(String apiCode) {
        this.apiCode = apiCode;
        return this;
    }

    public String getApiMessage() {
        return this.apiMessage;
    }

    public Result<T> setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
        return this;
    }

    public Result<T> setApiCodeMessage(String apiCode, String apiMessage) {
        return this.setApiCode(apiCode).setApiMessage(apiMessage);
    }

    public long getServerTime() {
        return this.serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public long getProcessTime() {
        return this.processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}
