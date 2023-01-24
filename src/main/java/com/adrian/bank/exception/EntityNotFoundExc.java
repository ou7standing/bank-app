package com.adrian.bank.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundExc extends RuntimeException {


    public EntityNotFoundExc(String message) {
        super (message);
    }

}
