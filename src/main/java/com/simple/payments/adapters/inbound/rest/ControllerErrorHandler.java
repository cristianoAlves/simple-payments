package com.simple.payments.adapters.inbound.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleGenericErrors(RuntimeException exception) {
        ErrorDto errorDto = new ErrorDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errorDto);
    }
}
