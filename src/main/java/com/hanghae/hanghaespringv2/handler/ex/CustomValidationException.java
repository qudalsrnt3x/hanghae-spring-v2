package com.hanghae.hanghaespringv2.handler.ex;


import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException{

    public static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
