package com.hanghae.hanghaespringv2.handler;

import com.hanghae.hanghaespringv2.dto.CMResponseDTO;
import com.hanghae.hanghaespringv2.handler.ex.CustomValidationException;
import com.hanghae.hanghaespringv2.handler.ex.InvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    @ExceptionHandler({CustomValidationException.class})
    public CMResponseDTO<?> handleValidationException(CustomValidationException e) {
        return new CMResponseDTO<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorMap());
    }

    @ExceptionHandler(InvalidException.class)
    public CMResponseDTO<?> handleInvalidException(InvalidException e) {
        return new CMResponseDTO<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
    }

}
