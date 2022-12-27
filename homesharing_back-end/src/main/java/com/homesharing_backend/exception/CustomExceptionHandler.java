package com.homesharing_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerConflictException(ConflictException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(BadRequestAlertException.class)
    public ExceptionResponse handlerBadRequestAlertException(BadRequestAlertException ex, WebRequest req) {
        return new ExceptionResponse(101, ex.getMessage());
    }

    @ExceptionHandler(SaveDataException.class)
    public ExceptionResponse handlerSaveDataException(SaveDataException ex, WebRequest req) {
        return new ExceptionResponse(111, ex.getMessage());
    }

    @ExceptionHandler(UpdateDataException.class)
    public ExceptionResponse handlerUpdateDataException(UpdateDataException ex, WebRequest req) {
        return new ExceptionResponse(112, ex.getMessage());
    }

    @ExceptionHandler(DateException.class)
    public ExceptionResponse handlerDateException(DateException ex, WebRequest req) {
        return new ExceptionResponse(113, ex.getMessage());
    }

    @ExceptionHandler(EmptyDataException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ExceptionResponse handlerEmptyDataException(EmptyDataException ex, WebRequest req) {
        return new ExceptionResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ExceptionResponse handlerAuthException(AuthException ex, WebRequest req) {
        return new ExceptionResponse(11, ex.getMessage());
    }

    @ExceptionHandler(SendMailException.class)
    public ExceptionResponse handlerSendMailException(SendMailException ex, WebRequest req) {
        return new ExceptionResponse(000, ex.getMessage());
    }
}
