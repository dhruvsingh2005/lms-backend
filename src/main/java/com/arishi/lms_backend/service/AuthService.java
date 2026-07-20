package com.arishi.lms_backend.service;

import com.arishi.lms_backend.dto.request.LoginRequest;
import com.arishi.lms_backend.dto.response.LogInUserResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LogInUserResponse login(LoginRequest request, String role, HttpServletResponse response);
}
