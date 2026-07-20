package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.request.LoginRequest;
import com.arishi.lms_backend.dto.response.LogInUserResponse;
import com.arishi.lms_backend.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/public/v1/auth/login")
    public ResponseEntity<ApiResponse> login(@RequestParam(required = true) String role, @Valid @RequestBody LoginRequest request, HttpServletResponse response) {
    	LogInUserResponse logInUserResponse = authService.login(request, role, response);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(), List.of("Login successful"), logInUserResponse));
    }
}