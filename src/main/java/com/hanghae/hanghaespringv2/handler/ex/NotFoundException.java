package com.hanghae.hanghaespringv2.handler.ex;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private ErrorCode errorCode;

    public NotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_FOUND;
    }
}
