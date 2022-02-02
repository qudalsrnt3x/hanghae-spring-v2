package com.hanghae.hanghaespringv2.handler.ex;

import lombok.Data;

@Data
public class ErrorResponse {

    private int status;
    private String message;
    private String code;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getErrorcode();
        this.message = message;
    }
}
