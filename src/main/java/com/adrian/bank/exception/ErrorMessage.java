package com.adrian.bank.exception;

public class ErrorMessage {
    private int statusCode;
    private String message;

    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }

}


//
//    private final String message;
//    private final Throwable throwable;
//    private final HttpStatus httpStatus;
//    private final ZonedDateTime timestamp;
//
//    public APIException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
//        this.message = message;
//        this.throwable = throwable;
//        this.httpStatus = httpStatus;
//        this.timestamp = timestamp;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public Throwable getThrowable() {
//        return throwable;
//    }
//
//    public HttpStatus getHttpStatus() {
//        return httpStatus;
//    }
//
//    public ZonedDateTime getTimestamp() {
//        return timestamp;
//    }


