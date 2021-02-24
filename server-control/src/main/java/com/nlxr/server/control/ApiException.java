package com.nlxr.server.control;

/**
 * @Author labu
 * @Date 2020/10/26
 * @Description
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String apiCode;
    private String apiMessage;

    public ApiException(String apiCode, String apiMessage) {
        this.build(apiCode, apiMessage);
    }

    public ApiException(ApiCode apiCode, Object... params) {
        this.build(apiCode, params);
    }

    public ApiException() {
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException build(String apiCode, String apiMessage) {
        this.apiCode = apiCode;
        this.apiMessage = apiMessage;
        return this;
    }

    public ApiException build(ApiCode apiCode, Object... params) {
        return this.build(apiCode.getApiCode(), apiCode.getApiMessage(params));
    }

    public String getApiCode() {
        return this.apiCode;
    }

    public String getApiMessage() {
        return this.apiMessage;
    }
}

