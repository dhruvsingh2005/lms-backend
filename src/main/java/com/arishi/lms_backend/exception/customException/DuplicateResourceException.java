package com.arishi.lms_backend.exception.customException;

public class DuplicateResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateResourceException(String message) {
        super(message);

    }
}