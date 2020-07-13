package com.whb.demo.validator.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author labu
 * @Date 2020/5/14
 * @Description
 */
@Data
public class RspDTO<T> implements Serializable {
    private boolean result;
    private T t;
    private int errorCode;
    private String errorMsg;

    public RspDTO() {
    }

    public RspDTO(int paramFailCode, String defaultMessage) {
        this.errorCode=paramFailCode;
        this.errorMsg=defaultMessage;
    }
}
