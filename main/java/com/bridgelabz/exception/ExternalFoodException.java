package com.bridgelabz.exception;

public class ExternalFoodException extends RuntimeException {

    public ExternalFoodException(String message) {
        super(message);
    }

    public ExternalFoodException(String message, Throwable cause) {
        super(message, cause);
    }
}