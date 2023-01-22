package com.adrian.bank.exception;

public class SpringBootExc extends RuntimeException{
    public SpringBootExc() {
    }

    public SpringBootExc(String message) {
        super (message);
    }


}
