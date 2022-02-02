package com.hanghae.hanghaespringv2.handler;

import com.hanghae.hanghaespringv2.dto.CMResponseDTO;
import com.hanghae.hanghaespringv2.handler.ex.CustomValidationException;
import com.hanghae.hanghaespringv2.handler.ex.ErrorResponse;
import com.hanghae.hanghaespringv2.handler.ex.InvalidException;
import com.hanghae.hanghaespringv2.handler.ex.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({CustomValidationException.class})
    public CMResponseDTO<?> handleValidationException(CustomValidationException e) {
        return new CMResponseDTO<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorMap());
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidException(InvalidException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()),
                HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()),
                HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

}
