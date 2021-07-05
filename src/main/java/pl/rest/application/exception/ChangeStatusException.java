package pl.rest.application.exception;

public class ChangeStatusException extends RuntimeException{

    public ChangeStatusException(String errorMessage) {
        super(errorMessage);
    }
}
