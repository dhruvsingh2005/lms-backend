package com.arishi.lms_backend.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.exception.customException.CourseAlreadyEndedException;
import com.arishi.lms_backend.exception.customException.InvalidAssignmentDateException;
import com.arishi.lms_backend.exception.customException.InvalidPdfException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(JwtException.class)
	public ApiResponse handleJwtException(JwtException jwtException) {
		return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), List.of("Unauthorized"), null);
	}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ApiResponse handleAccessDeniedException(AccessDeniedException accessDeniedException) {
		return new ApiResponse(HttpStatus.FORBIDDEN.value(), List.of(accessDeniedException.getMessage()), null);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidPdfException.class)
	public ApiResponse handleInvalidPdfException(InvalidPdfException invalidPdfException) {
		return new ApiResponse(HttpStatus.BAD_REQUEST.value(), List.of(invalidPdfException.getMessage()), null);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(EntityExistsException.class)
	public ApiResponse handleEntityExistsException(EntityExistsException entityExistsException) {
		return new ApiResponse(HttpStatus.CONFLICT.value(), List.of(entityExistsException.getMessage()), null);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ApiResponse handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
		return new ApiResponse(HttpStatus.NOT_FOUND.value(), List.of(entityNotFoundException.getMessage()), null);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CourseAlreadyEndedException.class)
	public ApiResponse handleCourseAlreadyEndedException(CourseAlreadyEndedException courseAlreadyEndedException) {
		return new ApiResponse(HttpStatus.BAD_REQUEST.value(), List.of(courseAlreadyEndedException.getMessage()), null);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidAssignmentDateException.class)
	public ApiResponse handleCourseAlreadyEndedException(InvalidAssignmentDateException invalidAssignmentDateException) {
		return new ApiResponse(HttpStatus.BAD_REQUEST.value(), List.of(invalidAssignmentDateException.getMessage()), null);
	}

}
