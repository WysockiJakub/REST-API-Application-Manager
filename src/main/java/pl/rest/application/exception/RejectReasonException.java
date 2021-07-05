package pl.rest.application.exception;

public class RejectReasonException extends RuntimeException {

    public RejectReasonException(String errorMessage) {
        super(errorMessage);
    }
}
