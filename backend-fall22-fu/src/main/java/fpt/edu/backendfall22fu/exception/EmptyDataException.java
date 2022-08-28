package fpt.edu.backendfall22fu.exception;


public class EmptyDataException extends RuntimeException {

    public EmptyDataException(String message){
        super(message);
    }
}
