package com.adrian.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
                BAD_REQUEST.value (),
                exc.getMessage ());

        return new ResponseEntity<> (message, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgNotValid(BindException ex) {
        Map<String, Object> responseBody = new LinkedHashMap<> ();

        responseBody.put ("status", BAD_REQUEST.value ());

        List<FieldError> fieldErrors = ex.getBindingResult ().getFieldErrors ();

        List<String> listErrors = new ArrayList<> ();

        for (FieldError fieldError : fieldErrors) {
            String errorMessage = fieldError.getDefaultMessage ();
            listErrors.add (errorMessage);
        }

        Set<String> set = new LinkedHashSet<> (listErrors);
        listErrors.clear ();
        listErrors.addAll (set);
        responseBody.put ("errors", listErrors);
        return new ResponseEntity<> (responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ErrorMessage handle(IllegalArgumentException ex) {
        ErrorMessage e = new ErrorMessage (
                BAD_REQUEST.value (),
                ex.getLocalizedMessage ());
        return e;
    }

    @ExceptionHandler
    public ErrorMessage handleNumberFormatExc(java.lang.NumberFormatException ex) {
        ErrorMessage message = new ErrorMessage (
                BAD_REQUEST.value (),
                "improper number format");
        return message;
    }
}