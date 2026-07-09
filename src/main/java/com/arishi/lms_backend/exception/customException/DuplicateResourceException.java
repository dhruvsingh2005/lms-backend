package com.arishi.lms_backend.exception.customException;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);

    }
}