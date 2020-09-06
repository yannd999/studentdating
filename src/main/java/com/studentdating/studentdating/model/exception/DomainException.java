package com.studentdating.studentdating.model.exception;

public class DomainException extends RuntimeException {
    public DomainException() {}

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable error) {
        super(message, error);
    }

    public DomainException(Throwable error) {
        super(error);
    }

}
