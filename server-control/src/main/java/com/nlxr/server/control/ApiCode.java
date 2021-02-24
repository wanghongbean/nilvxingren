package com.nlxr.server.control;

public interface ApiCode {
    String getApiCode();

    String getApiMessage(Object... var1) throws ApiCode.ApiCodeException;

    public static class ApiCodeException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ApiCodeException(String message) {
            super(message);
        }
    }
}
