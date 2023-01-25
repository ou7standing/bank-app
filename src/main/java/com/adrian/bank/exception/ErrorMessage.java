package com.adrian.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorMessage {
    private int statusCode;
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}