package com.arishi.lms_backend.exception.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidPdfException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
}
