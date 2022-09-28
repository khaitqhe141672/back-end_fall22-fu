package com.homesharing_backend.exception;

import lombok.Data;

public class ConflictException extends RuntimeException {
    public ConflictException(String message){
        super(message);
    }
}
