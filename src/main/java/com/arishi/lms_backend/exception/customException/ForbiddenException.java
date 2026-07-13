package com.arishi.lms_backend.exception.customException;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
        super(message);
    }
}