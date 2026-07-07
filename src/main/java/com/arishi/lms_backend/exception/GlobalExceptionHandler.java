package com.arishi.lms_backend.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateResourceException.class)
    public ApiResponse handleDuplicateResourceException(
            DuplicateResourceException exception) {

        return new ApiResponse(
                HttpStatus.CONFLICT.value(),
                List.of(exception.getMessage()),
                null
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(
                    error.getField() + ": " + error.getDefaultMessage()
            );
        }

        return new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                null
        );
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(
                        HttpStatus.NOT_FOUND.value(),
                        List.of(ex.getMessage()),
                        null
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse(
                        HttpStatus.CONFLICT.value(),
                        List.of("Email or Mobile number already exists"),
                        null
                ));
    }
}
