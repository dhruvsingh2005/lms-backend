package com.arishi.lms_backend.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.exception.customException.CourseAlreadyEndedException;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.InvalidAssignmentDateException;
import com.arishi.lms_backend.exception.customException.InvalidPdfException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.server.ResponseStatusException;

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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateResourceException.class)
    public ApiResponse handleDuplicateResourceException(DuplicateResourceException exception) {
        return new ApiResponse(HttpStatus.CONFLICT.value(), List.of(exception.getMessage()), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), errors, null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()), null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(HttpStatus.CONFLICT.value(), List.of("Email or Mobile number already exists"), null));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), List.of("Invalid email or password"), null));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ApiResponse(ex.getStatusCode().value(), List.of(ex.getReason()), null));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), List.of("Authentication required. Please login."), null));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingRequestParameter(
            MissingServletRequestParameterException ex) {

        return ResponseEntity.badRequest().body(
                new ApiResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        List.of(ex.getParameterName() + " is required"),
                        null
                )
        );
    }
}
