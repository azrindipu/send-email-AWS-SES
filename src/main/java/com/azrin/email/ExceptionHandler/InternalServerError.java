package com.azrin.email.ExceptionHandler;

public class InternalServerError extends RuntimeException {
    public InternalServerError(String message){
        super(message);
    }
}
