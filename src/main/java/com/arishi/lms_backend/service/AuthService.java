package com.arishi.lms_backend.service;

import com.arishi.lms_backend.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void login(LoginRequest request, String role, HttpServletResponse response);
}
