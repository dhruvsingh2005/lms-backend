package com.arishi.lms_backend.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.arishi.lms_backend.dto.ApiResponse;
import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(JwtException.class)
	public ApiResponse handleJwtException(JwtException jwtException) {
		return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), List.of("Unauthorized"), null);
	}
}
