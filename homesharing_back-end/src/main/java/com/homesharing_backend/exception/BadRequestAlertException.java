package com.homesharing_backend.exception;

public class BadRequestAlertException extends RuntimeException {

    public BadRequestAlertException(String message){
        super(message);
    }
}