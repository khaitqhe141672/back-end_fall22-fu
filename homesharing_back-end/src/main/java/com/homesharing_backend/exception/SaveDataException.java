package com.homesharing_backend.exception;

public class SaveDataException extends RuntimeException {
    public SaveDataException(String message){
        super(message);
    }
}