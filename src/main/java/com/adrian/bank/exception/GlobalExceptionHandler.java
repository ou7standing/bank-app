package com.adrian.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleAccNotFoundExc(EntityNotFoundExc exc) {

        ErrorMessage message = new ErrorMessage (
                HttpStatus.NOT_FOUND.value (),
                exc.getMessage ());

        return new ResponseEntity<> (message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleRandomException(SpringBootExc exc) {

        ErrorMessage message = new ErrorMessage (
                HttpStatus.BAD_REQUEST.value (),
                exc.getMessage ());

        return new ResponseEntity<> (message, HttpStatus.BAD_REQUEST);
    }
}