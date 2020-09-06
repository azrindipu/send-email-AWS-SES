package com.azrin.email.ExceptionHandler;

import java.util.List;

public class ControllerLevelException extends RuntimeException {

    private List<String> errors;
    private String message;

    public ControllerLevelException(String message, List<String> errors){
        this.message = message;
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
