package com.azrin.email.ExceptionHandler;

public class BadRequest extends RuntimeException {
    public BadRequest(String message){
        super(message);
    }
}
