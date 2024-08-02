package com.example.ecommercemini.exception;

public class ConstraintViolationException extends RuntimeException{
    public ConstraintViolationException() {
        super("Unique constraint voilation");
    }

    public ConstraintViolationException(String message) {
        super(message);
    }
}
