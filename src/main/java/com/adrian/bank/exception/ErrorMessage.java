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

    // added later

//    private List<FieldError> fieldErrors = new ArrayList<> ();

//    public void addFieldError( String path, String message) {
//        FieldError error = new FieldError( path, message);
//        fieldErrors.add(error);
//    }
//
//    public List<FieldError> getFieldErrors() {
//        return fieldErrors;
//    }


}