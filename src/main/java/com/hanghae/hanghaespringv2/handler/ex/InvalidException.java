package com.hanghae.hanghaespringv2.handler.ex;

import lombok.Getter;

@Getter
public class InvalidException extends RuntimeException {

    private ErrorCode errorCode;

    public InvalidException(String message) {
        super(message);
        this.errorCode = ErrorCode.BAD_REQUEST;
    }
}
