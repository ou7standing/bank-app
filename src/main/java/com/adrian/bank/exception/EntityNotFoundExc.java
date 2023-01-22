package com.adrian.bank.exception;

public class EntityNotFoundExc extends RuntimeException {


    public EntityNotFoundExc(String message) {
        super (message);
    }

    public EntityNotFoundExc() {
    }
}
