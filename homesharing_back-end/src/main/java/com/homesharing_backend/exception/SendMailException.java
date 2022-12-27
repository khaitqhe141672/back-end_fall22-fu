package com.homesharing_backend.exception;

public class SendMailException extends RuntimeException {
    public SendMailException(String message){
        super(message);
    }
}
