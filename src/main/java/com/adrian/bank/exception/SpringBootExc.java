package com.adrian.bank.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpringBootExc extends RuntimeException {

    public SpringBootExc(String message) {
        super (message);
    }
}